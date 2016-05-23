package es.ucm.fdi.tp.Practica6.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	 * <p>numero de jugadores necesarios para desarrollar el juego</p>
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
	
	/**
	 * <b>infoPlayersArea</b>
	 * <p>Campo de informe de los jugadores conectados al servidor</p> 
	 */
	private  JFrame window;
	
	/**
	 * <b>firstRound</b>
	 * <p>boleano de control de primera ronda en el inicio de servidor, para el inicio/reset de partidas/p> 
	 */
	private boolean firstRound = true;


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
	 * <p>Constructor of the game server</p>
	 * @param game controller of the server
	 * @param pieces of players defined
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

	/**
	 * <b>start</b>
	 * <p>Start the server, including the GUI controller and the server controller itself</p>
	 */
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
	 * <p>El uso de invokeAndWait nos permite utilizar correctamente el metodo "log"
	 * para añadir mensajes,etc</p>
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
		this.window = new JFrame("Game Server");
		
		JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
		JPanel StatusServerPanel = new JPanel(new BorderLayout(5, 5));
		JPanel StatusPlayerPanel = new JPanel();
		this.infoStatusArea = new TextArea(20,60);
		this.infoPlayersArea = new TextArea(20,20); 
		this.infoStatusArea.setEditable(false);
		this.infoPlayersArea.setEditable(false);
		this.infoStatusArea.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		StatusServerPanel.add(new JScrollPane(infoStatusArea));
		StatusPlayerPanel.add(new JScrollPane(infoPlayersArea));
		mainPanel.add(StatusServerPanel, BorderLayout.CENTER);

		JButton quitButton = new JButton("Stop Server");
		quitButton.setToolTipText("Stop and close the server");
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to close the server?", "Close server",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				
				if (n == 0) {
					try {
						stopTheServer();
					} catch (GameError _e) {
					}
					window.setVisible(false);
					window.dispose();
					System.exit(0);
				}
			}
			
		});
		mainPanel.add(StatusPlayerPanel, BorderLayout.LINE_END);
		mainPanel.add(quitButton, BorderLayout.PAGE_END);
		window.add(mainPanel);
		window.setMinimumSize(new Dimension(600,400));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		window.setVisible(true);
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
	 * <b>logPlayers</b>
	 * <p> Component process for the info game server </p>
	 * <p> Procedimiento de insercion de mensajes en el area de texto</p> 
	 * @param message to show in the tex area
	 */
	private void logPlayers(String message){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				try {
					infoPlayersArea.append(message + System.getProperty("line.separator"));
				} catch (NullPointerException e) {
					infoPlayersArea.setText("null");
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
		this.log("Server started to listen clients");
		this.log(this.numPlayers + " players to go.");
		
		/*
		 * El bucle del servidor: esperar a que un cliente conecete y pasar el socket correspondiente a handle Request para resonder a la peticion
		 */
		while(!this.stopped){
			try{
				//accept a connection into a socket s
				Socket s = this.server.accept();
				
				//log a corresponding message
				this.log("client trying to connect from port " + s.getPort());
				this.logPlayers(this.pieces.get(this.numOfConnectedPlayers).toString());
				//call handleRequest(s) to handle the request in a thread per client
				handleRequest(s);
			}catch(IOException | ClassNotFoundException e ){
				if (!this.stopped)
					this.log("Error while waiting for a connection: " + e.getMessage());
			}
		}
	}


	/**
	 * <b>handleRequest</b>
	 * <p>Manejador de la peticion de conexion</p>
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
		if(this.numOfConnectedPlayers >= this.numPlayers){
			c.sendObject(new GameError("Maximum players connections reached"));
			c.stop();
			this.log("A client connection was refused: Maximum players connections reached ");
			return;

		}else{
			
				/*
				 * Incrementar el numero de clientes conectados
				 * y añadir "c" a la lista de clientes
				 */
				this.clients.add(c);
				this.numOfConnectedPlayers++;
				this.log(this.numPlayers - (this.numOfConnectedPlayers) + " players to go.");
		}
				/*
				 * Enviar String "ok" al cliente, el gameFactory y la pieza
				 * de la lista pieces en su posicion i-esima
				 */
				c.sendObject("ok");
				c.sendObject(this.gameFactory);
				c.sendObject(this.pieces.get(numOfConnectedPlayers-1));
					this.log((String) c.getObject());
					
		/*
		 * Si se cumple con el numero de jugadores necesarios se inicia la partida
		 */
			if(this.numOfConnectedPlayers == this.numPlayers){
				if(this.firstRound){
					this.firstRound = false;
					this.log( "players ready. Starting game...");
					game.start(pieces);
				}else
					this.log( "players ready. Re-starting a new game...");
					game.restart();
					this.log(this.numPlayers + " players to go.");
			}
		
		
		/*
		 * Invocar al startClientListener para iniciar una hebra para 
		 * recibir comandos del cliente
		 */
		startClientListener(c);
		
		}catch(IOException | ClassNotFoundException _e){}
	}

	/**
	 * <b>startClientListener</b>
	 * <p>Procedimiento de recepcion de comandos del cliente</p>
	 * @param c connexion entre el cliente y servidor, contiene el sokect y los canales de envio del mismo
	 */
	private void startClientListener(Connection c) {
		this.gameOver = false;
		
		/*
		 * Hebra de ejecucion del bucle mientras el juego no haya terminado, o el servidor haya parado.
		 */
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				/*
				 * Manetenemos la hebra mientras no termine el juego o se pare el servidor 
				 */
				while(!stopped && !gameOver){
					Command cmd;
					try {
						cmd = (Command) c.getObject();
						cmd.execute(GameServer.this);
					} catch (ClassNotFoundException | IOException e) {
						if(!stopped && !gameOver){
							GameServer.this.stopTheGame();
							log("Client Listener thread generate an exeption while runnig/inGame: " + e.getMessage());
						}
						log("Client Listener thread generate an exeption: " + e.getMessage());
					}
				}
				
			}
			
		});
		t.start();
	
}
	/**
	 * <b>stopTheGame</b>
	 * <p>Procedimiento de parada de partida, se cierran las conexiones y se limpian las listas de clientes./p>
	 * 
	 */
	private void stopTheGame(){
		this.gameOver = true;
		if(this.game.getState().equals(State.InPlay)){
			this.game.stop();
		}
		for (Connection c : this.clients)
			try {
				c.stop();
				this.log("the connection with client in port (" + c.getPort() +") have been closed");
				this.numOfConnectedPlayers--;
			} catch (IOException e) {
				this.log("Stop the game generate an excetion :" + e.getMessage());
			}
		this.clients.clear();
		this.infoPlayersArea.setText(null);
	}
	/**
	 * <b>stopTheServer</b>
	 * <p>Procedimiento de parada de servidor. Cierra el servidor de conexiones, previas conexiones y juego /p>
	 */
	private void stopTheServer(){
		this.stopped = false;
		if(this.game.getState().equals(State.InPlay)){
			this.game.stop();
		}
		stopTheGame();
		try {
			this.server.close();
		} catch (IOException e) {
			this.log("El servidor de conexiones genereo una excepcion: " + e.getMessage());
		}
		this.window.dispose();
	}

//------------------------------------------OBSERVABLE EVENTS--------------------------------------//
	
	/**
	 * <b>fowardNotification</b>
	 * <p>Send response method for the clients</p>
	 * @param r object send to the clients
	 */
	void fowardNotification (Response r){
		int port = -1;
		try {
			for (Connection c : clients) {
				port = c.getPort();
				c.sendObject(r);
			}
			} catch (IOException e) {
				this.log("Exception generated in client comunnication by port(" + port +") :" + e.getMessage());
				stopTheGame();
			}
	}
	
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		fowardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		this.log("Game have finished. Reporting to clients");
		fowardNotification(new GameOverResponse(board, state, winner));
		this.log("Closing all conections");
		this.stopTheGame();
		
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
