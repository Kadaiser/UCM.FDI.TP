package es.ucm.fdi.tp.Practica6.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.Practica6.buffer.Connection;
import es.ucm.fdi.tp.Practica6.server.response.ChangeTurnResponse;
import es.ucm.fdi.tp.Practica6.server.response.ErrorResponse;
import es.ucm.fdi.tp.Practica6.server.response.GameOverResponse;
import es.ucm.fdi.tp.Practica6.server.response.GameStartResponse;
import es.ucm.fdi.tp.Practica6.server.response.MoveEndResponse;
import es.ucm.fdi.tp.Practica6.server.response.MoveStartResponse;
import es.ucm.fdi.tp.Practica6.server.response.Response;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {
	
	/**
	 * <b>port</b>
	 * <p>puerto usado por el servidor</p>
	 */
	private int port;
	
	/**
	 * <b>numPlayers</b>
	 * <p>numero de jugadores miminos necesario para desarrollar el juego</p>
	 */
	private int numPlayers;
	
	/**
	 * <b>numOfConnectedPlayers</b>
	 * <p>El numero actual de jugadores conectados al servidor</p>
	 */
	private int numOfConnectedPlayers;
	
	/**
	 * <b>gameFactory</b>
	 * <p>Factoria del juego que se va a instanciar</p>
	 */
	private GameFactory gameFactory;
	
	/**
	 * <b>List</b>
	 * <p>Arraylist de clientes conectados al servidor</p>
	 */
	private List<Connection> clients;
	
	/**
	 * <b>infoArea</b>
	 * <p>Campo de informe de eventos del servidor</p> 
	 */
	private TextArea infoStatusArea;
	
	/**
	 * <b>infoPlayersArea</b>
	 * <p>Campo de informe de los jugadores conectados al servidor</p> 
	 */
	private TextArea infoPlayersArea;


//---------------------------------------------ATRIBUTOS VOLATILE------------------------------------//
	/**
	 * <b>ServerSocket</b>
	 * <p>Referencia del servidor</p>
	 */
	volatile private ServerSocket server;
	/**
	 * <b>stopped</b>
	 * <p>Valor de estado "encendido/apagado" del servidor</p>
	 */
	volatile private boolean stopped;
	
	/**
	 * <b>gameOver</b>
	 * <p>Valor indicador de fin de partida</p>
	 */
	volatile private boolean gameOver;
	
	
	/**
	 * 
	 * @param game
	 * @param pieces
	 */
	public GameServer(GameFactory gameFactory, List<Piece> pieces, int port) {
		super(new Game(gameFactory.gameRules()), pieces);
		this.port = port;
		this.numPlayers = pieces.size();
		this.numOfConnectedPlayers = 0;
		this.gameFactory = gameFactory;
		this.clients = new ArrayList<Connection>();
		
		game.addObserver(this);
	}

	@Override
	public synchronized void makeMove(Player player) {
		try {
			super.makeMove(player);
		} catch (GameError e) {
		}
	}

	@Override
	public synchronized void stop() {
		try {
			super.stop();
		} catch (GameError e) {
		}
		this.gameOver = true;
	}

	@Override
	public synchronized void restart() {
		try {
			super.restart();
		} catch (GameError e) {
		}
	}

	public void start() {
		controlGUI();
		try {
			startServer();
		} catch (IOException e) {
			this.log("Error starting a client connection: " + e.getMessage());
		}
	}

	/**
	 * <b>controlGUI</b>
	 * <p>Procedimiento de inicializacion de la interfaz de servidor</p>
	 */
	private void controlGUI() {
		try{
			SwingUtilities.invokeAndWait(new Runnable(){

				@Override
				public void run() {
					constructGraphicGUI();
				}
			});
		}catch(InvocationTargetException | InterruptedException e){
			throw new GameError("Well, this is embarrassing, but something went wrong.");
		}
	}
	
	/**
	 * <b>constructGraphicGUI</b>
	 * <p>Procedimiento de construccion grafico de la interfaz de servidor</p>
	 */
	private void constructGraphicGUI(){
		JFrame window = new JFrame("Game Server");
		this.infoStatusArea = new TextArea(40,40);
		this.infoStatusArea.setEditable(false);
		this.infoStatusArea.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		window.add(new JScrollPane(infoStatusArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		JButton quitButton = new JButton("Stop Server");
		quitButton.setToolTipText("Stop and close the server");
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to close the server?", "Close server",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				
				if (n == 0) {
					try {
						stop();
					} catch (GameError _e) {
					}
					window.setVisible(false);
					window.dispose();
					System.exit(0);
				}
			}
			
		});
		//window.add(quitButton);
		window.setMaximumSize(new Dimension(400,400));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		/*
		JFrame window = new JFrame("Game Server");
		window.setLayout(new BoxLayout(window, BoxLayout.X_AXIS));
		JPanel infoStatus = createPanelLabeled("Status Server Information", Color.BLACK);
		JPanel infoPlayers = createPanelLabeled("Status Server Information", Color.BLACK);
		
		this.infoStatusArea = new TextArea(40,40);
		this.infoStatusArea.setEditable(false);
		this.infoStatusArea.setFont(new Font("Comic Sans MS", Font.BOLD, 11));	
		window.add(infoStatus.add(new JScrollPane(infoStatusArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)));
		
		this.infoPlayersArea = new TextArea(40,10);
		this.infoPlayersArea.setEditable(false);
		this.infoPlayersArea.setFont(new Font("Comic Sans MS", Font.BOLD, 11));	
		window.add(infoPlayers.add(new JScrollPane(infoPlayersArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)));
		
		JButton quitButton = new JButton("Stop Server");
		quitButton.setToolTipText("Stop and close the server");
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to close the server?", "Close server",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				
				if (n == 0) {
					try {
						stop();
					} catch (GameError _e) {
					}
					window.setVisible(false);
					window.dispose();
					System.exit(0);
				}
			}
			
		});
		window.add(quitButton);
		window.setMaximumSize(new Dimension(400,400));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		*/
	}
	
	/**
	 * <b>gameMessagesPanel</b>
	 * <p> Creation procedure accumulator panel components</p>
	 * <p> Procedimiento de creación de un panel etiquetado y centrado</p> 
	 * @param label String whit the label for the titled border
	 * @param color background configuration
	 * @return a instanced titled JPanel 
	 */
	protected JPanel createPanelLabeled(String label, Color color) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		panel.setBorder(BorderFactory.createTitledBorder(label));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		return panel;
	}
	
	
	/**
	 * <b>log</b>
	 * <p> Component process for the info game server </p>
	 * <p> Procedimiento de insercion de mensajes en el area de texto</p> 
	 * @param message to show in the tex area
	 */
	private void log(String message){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				try {
				infoStatusArea.append(message + System.getProperty("line.separator"));
				} catch (NullPointerException e) {
					infoStatusArea.setText("null");
				}
			}			
		});
	}
	
	
	/**
	 * <b>startServer</b>
	 * <p>Procedimiento de inicializacion de servidor</p>
	 * @throws IOException caused by {@link stopped} value in false
	 */
	private void startServer() throws IOException {
		this.server = new ServerSocket(port);
		this.stopped = false;
		
		while(!this.stopped){
			try{
				/*
				 * El bucle del servidor: esperar a que un cliente conecete y pasar el socket correspondiente a handle Request para resonder a la peticion
				 */
				
				//accept a connection into a socket s
				Socket s = new Socket();
				
				//log a corresponding message
				this.log("something");
				//call handleRequest(s) to handle the request
				this.handleRequest(s);
			}catch(IOException | ClassNotFoundException e ){
				if (!this.stopped)
					this.log("Error while waiting for a connection: " + e.getMessage());
			}
		}
	}

/**
 * 
 * @param s
 * @throws IOException
 * @throws ClassNotFoundException
 */
	private void handleRequest(Socket s) throws IOException, ClassNotFoundException {
		try{
		Connection c = new Connection(s);
		
		Object clientRequest = c.getObject(); //1er mensaje del cliente DEBE ser string "Connect"
		if(!(clientRequest instanceof String) && !((String)clientRequest).equalsIgnoreCase("Connect")){
			c.sendObject(new GameError("Invalid request"));
			c.stop();
			return;
		}
		/*
		 * Limitar los intentos de conexiones por encima del umbral de jugadores 
		 */
		if(this.numOfConnectedPlayers > gameFactory.gameRules().maxPlayers()){
			c.sendObject(new GameError("Maximum players connections reached"));
		/*
		 * Incrementar el numero de clientes conectados
		 * y añadir "c" a la lista de clientes
		 */
		this.numOfConnectedPlayers++;
		this.clients.add(c);
		}
		/*
		 * Enviar String "OK" al cliente, el gameFactory y la pieza
		 * de la lista pieces en su posicion i-esima
		 */
		
		/*
		 * Si se cumple con el numero de jugadores se inicia la partida
		 */
		
		/*
		 * Invocar al startClientListener para iniciar una hebra para 
		 * recibir comandos del cliente
		 */
		startClientListener(c);
		}catch(IOException | ClassNotFoundException _e){}
	}

	/**
	 * 
	 * @param c
	 */
	private void startClientListener(Connection c) {
	this.gameOver = false;
	
	Thread t = new Thread();
	
	/*
	 * Iniciar una hebra para ejecutar el bucle mientras no haya terminado el juego
	 * ni que el servidor se haya parado
	 */
	t.start();
	
	while(!stopped && !gameOver){}
		//leer el comadno
		Command cmd = (Command) c;
		//ejecutar el comando
		cmd.execute(this);
}

//------------------------------------------OBSERVABLE EVENTS--------------------------------------//


	void fowardNotification (Response r){
		//call c.sendObject(r) para cada conexion de cliente
	}
	
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		fowardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		fowardNotification(new GameOverResponse(board, state, winner));
		this.stop();
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		fowardNotification(new MoveStartResponse(board, turn));
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		fowardNotification(new MoveEndResponse(board, turn, success));
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		fowardNotification(new ChangeTurnResponse(board, turn));
	}

	@Override
	public void onError(String msg) {
		fowardNotification(new ErrorResponse(msg));
	}

}
