package es.ucm.fdi.tp.practica5.view;


import java.awt.BorderLayout;
import java.awt.Color;
//import javafx.scene.paint.Color;									//mola bastante mas....pero funciona igual?
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.board.RectBoardComponent;
import es.ucm.fdi.tp.practica5.view.color.ColorChooser;
import es.ucm.fdi.tp.practica5.view.sound.MakeSound;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;

public abstract class SwingView extends JFrame implements GameObserver{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//------------------------------MODOS DE JUEGO-------------------------------//		
	/**
	 * <p>Player modes (manual, random, etc.)</p>
	 * <p> Modos de juego.</p>
	 */
	public enum PlayerMode {
		MANUAL("Manual"), RANDOM("Random"), AI("Automatics");

		private String desc;

		PlayerMode(String desc){
			this.desc = desc;
		}
		
		@Override
		public String toString() {
			return desc;
		}
	}
	
	/**
	 * A map that associates pieces with players modes (manual, random, etc.).
	 * 
	 * <p>
	 * Map que asocia fichas con modos de jugadores (manual, random, etc.).
	 */
	private Map<Piece,PlayerMode> playersModes;
	
	/**
	 * <p>Used for recieve the reference of game modes for the list of pieces</p>
	 * @return the players modes Map<Piece,Player> of this object
	 */
	public Map<Piece,PlayerMode> getPlayerModes(){
		return this.playersModes;
	}

//------------------------------COLORES DE PIEZAS-------------------------------//		
	/**
	 * A map that associates pieces Colors).
	 * 
	 * <p>
	 * Map que asocia fichas con colores.
	 */
	private Map<Piece,Color> pieceColors;
	
	/**
	 * <b><getPieceColors/b>
	 * <p>Getter for the reference of the pieceColor attribute</p>
	 * @return this pieceColors
	 */
	public Map<Piece,Color> getPieceColors(){
		return this.pieceColors;
	}
	
	/**
	 * <b><getPieceColors/b>
	 * <p>Getter for the reference of the pieceColor attribute</p>
	 * @param p is the piece reference for the Map
	 * @return the color settled for the key reference
	 */
	protected Color getPieceColor(Piece p) {
		//return (pieceColors.get(p) == null ? (Color) Utils.colorsGenerator() : pieceColors.get(p));
		return pieceColors.get(p);
	}
	
	/**
	 * <b><setPieceColor/b>
	 * <p>Getter for the reference of the pieceColor attribute</p>
	 * @param p is the piece reference for the Map
	 * @param c is the color to set on the key piece
	 * @return a settled color for the key piece
	 */
	protected Color setPieceColor(Piece p, Color c) {
		return pieceColors.put(p, c);
	}
	

//------------------------------LISTA DE PIEZAS-------------------------------//
	/**
	 *<b>pieces</b>
	 *<p>The ArrayList reference of player pieces in the game</p>
	 */
	private List<Piece>pieces;
	
	/**
	 * <b>getPieces<b>
	 * @return list of piece for this game
	 */
	final protected List<Piece> getPieces() {
		return this.pieces;
	}
	
//------------------------------PIEZA DE SWINGVIEW-------------------------------//	
	/**
	 * <b>turn</b>
	 * <p>Reference to the owner of the turn in the game</p>
	 */
	protected Piece turn;
	/**
	 * <b>getTurn<b>
	 * @return the turn for this game
	 */
	final protected Piece getTurn() {
		return this.turn;
	}

//------------------------------REFERENCIA DE TABLERO-------------------------------//
	/**
	 * <b>board</b>
	 * <p>referencia al tablero de juego del modelo</p>
	 */
	protected Board board;
	/**
	 * <b>getBoard<b>
	 * @return the model board for this game
	 */
	final protected Board getBoard() {
		return this.board;
	}
	
//------------------------------COMPONENTE DE TABLERO-------------------------------//	
	/**
	 * <b>ControllerPanel</b>
	 * <p>Panel aglomerador de componentes</p>
	 */
	private JPanel boardPanel;
	/**
	 * 
	 */
	protected RectBoardComponent boardComponent;
	/**
	 * <b>setBoardArea</b>
	 * @param component to add to the boardPanel;
	 */
	final protected void setBoardArea(JComponent component) {
		this.boardPanel.add(component, BorderLayout.CENTER);
	}
	
//------------------------------ATRIBUTOS DE SWING VIEW-------------------------------//
	/**
	 * <b>Observable</b>
	 *<p> Reference for the observer of the game changes</p>
	 */
	protected Observable<GameObserver> game;
	
	/**
	 * <b>controller</b>
	 *<p> Reference for the controller of the game </p>
	 */
	protected Controller controller;
	
	/**
	 * <b>localPiece</b>
	 *<p> Reference for the owner of this view, if value is null, means there are not multiviews enable</p>
	 */
	private Piece localPiece;
	
	/**
	 * <b>randPlayer</b>
	 *<p> A random algorithm to create moves </p>
	 */
	private Player randPlayer;
	
	/**
	 *<b>aiPlayer</b>
	 *<p>An artificial intelligent suppose to govern over the world, now released to play this game </p>
	 */
	private Player aiPlayer;
	
	
	protected boolean newGame, inPlay, inMove;
	
	
	
	/**
	 * <b>SwingView</b>
	 * <p> Constructor for the abstract father SwingView</p>
	 * <p> Constructor de la clase padre abstracta SwingView</p>
	 * @param game
	 * @param controller it a reference to call the methods of the controller of the MVC
	 * @param localPiece its an indicator of the owner for this SwingView
	 * @param randPlayer a builded random player for random moves
	 * @param aiPlayer a builded automatic player for automatics moves
	 */
	public SwingView(Observable<GameObserver> game, Controller controller, Piece localPiece, Player randPlayer,
			Player aiPlayer) {

		this.game = game;
		this.controller = controller;
		this.localPiece = localPiece;
		this.randPlayer = randPlayer;
		this.aiPlayer = aiPlayer;
		this.pieceColors = new HashMap<Piece, Color>();
		this.playersModes = new HashMap<Piece, PlayerMode>();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				game.addObserver(SwingView.this);
			}
		});

		initGUI();
	}
	
	/**
	 * <b>initGUI</b>
	 * <p> Creation procedure for the main frame</p>
	 * <p> Procedimiento de maquetación del frame principal</p> 
	 */
	private void initGUI() {

		this.setLayout(new BorderLayout(3, 3));
		createPanels();

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (n == 0) {
					try {
						controller.stop();
					} catch (GameError _e) {

					}
					setVisible(false);
					dispose();
					System.exit(0);

				}

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}

		});
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension(800, 600));
		this.setVisible(true);

	}
	

//----------------------------------PANEL DE CONTROLADOR------------------------------------//
	
	/**
	 * <b>ControllerPanel</b>
	 * <p>Panel aglomerador de componentes</p>
	 */
	private JPanel ControllerPanel;
	
	/**
	 * <b>gameMessagesPanel</b>
	 * <p> Creation procedure accumulator panel components</p>
	 * <p> Procedimiento de creación del panel aglomerador de componentes</p> 
	 */
	final private void createPanels() {

		this.boardPanel = new JPanel(new BorderLayout(5, 5));
		this.ControllerPanel = new JPanel(new BorderLayout(5, 5));
		this.ControllerPanel.setLayout(new BoxLayout(ControllerPanel, BoxLayout.Y_AXIS));
		this.ControllerPanel.setBackground(Color.BLACK);
		gameMessagesComponent();
		playerInformationComponent();
		selectColorComponent();
		selectModePlayerComponent();
		automaticMoveComponent();
		exitComponent();
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(ControllerPanel, BorderLayout.LINE_END);
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
	
//----------------------------------COMPONENTE DE TEXTO------------------------------------//
	
	/**
	 * <b>gameMessagesPanel</b>
	 * <p>Panel contenedor del componente de mesajes del juego </p>
	 */
	private JPanel gameMessagesPanel;
	
	/**
	 * <b>gameMessages</b>
	 * <p>Area de texto de salida de los mesajes del juego </p>
	 */
	private JTextArea gameMessages;
	
	/**
	 * <b>gameMessagesPanel</b>
	 * <p> Component creation process for the status game messages </p>
	 * <p> Procedimiento de creación del componente de mensajes de estado del juego</p> 
	 */
	private void gameMessagesComponent() {
		this.gameMessagesPanel = createPanelLabeled("Game Information", Color.LIGHT_GRAY);
		this.gameMessages = new JTextArea(10, 25);
		this.gameMessages.setEditable(false);
		this.gameMessages.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		this.gameMessages.setHighlighter(null);
		this.gameMessages.setLineWrap(false);
		this.gameMessagesPanel.add(new JScrollPane(this.gameMessages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		this.ControllerPanel.add(this.gameMessagesPanel);
	}
	
//--------------------------METODOS DEL COMPONENTE DE TEXTO-------------------------------//
	/**
	 * <b>gameMessagesPanel</b>
	 * <p> Component creation process for the status game messages </p>
	 * <p> Procedimiento de insercion de mensajes en el area de texto</p> 
	 * @param message to show in the tex area
	 */
	final protected void addMessageToTextArea(String message) {
		try {
			this.gameMessages.append("* " + message + System.getProperty("line.separator"));
		} catch (NullPointerException e) {
			this.gameMessages.setText("null");
		}
	}
	
//-------------------------COMPONENTE DE INFORMACION DE JUGADORES--------------------------//	
	
	/**
	 * <b>playerInformationPanel</b>
	 * <p>Panel contenedor del tablon de información de jugadores </p>
	 */
	private JPanel playerInformationPanel;
	
	/**
	 * <b>infoTable</b>
	 * <p>Tabla contenedora de la informacion de los jugadores</p>
	 */
	private PlayerInformationTable infoTable;
	
	/**
	 * <b>playerInformationComponent</b>
	 * <p> Component creation process for the payers information </p>
	 * <p> Procedimiento de creación del componente de informacion de jugadores</p> 
	 */
	private void playerInformationComponent() {
		this.playerInformationPanel = createPanelLabeled("Player Information", Color.LIGHT_GRAY);
		this.infoTable = new PlayerInformationTable();
		@SuppressWarnings("serial")
		JTable infoPanel = new JTable(this.infoTable) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				comp.setBackground(pieceColors.get(pieces.get(row)));
				return comp;
			}
		};
		infoPanel.setBackground(getForeground());
		infoPanel.setEnabled(false);
		infoPanel.setPreferredScrollableViewportSize(new Dimension(15, 60));
		infoPanel.setFillsViewportHeight(false);
		JScrollPane scroll = new JScrollPane(infoPanel);

		this.ControllerPanel.add(this.playerInformationPanel.add(scroll));
	}
	
//-------------------------COMPONENTE DE CAMBIO DE COLORES-------------------------------//	
	/**
	 * <b>selectColorPanel</b>
	 * <p>Panel contenedor del desplegable y boton de cambio de color </p>
	 */
	private JPanel selectColorPanel;
	
	/**
	 * <b>pieceColorCombo</b>
	 * <p>desplegable con el contenido de piezas del juego para el cambio de colores</p>
	 */
	private JComboBox<Piece> pieceColorCombo;
	
	/**
	 * <b>selectColorComponent</b>
	 * <p> Component creation process for the colors change </p>
	 * <p> Procedimiento de creación del componente de cambio de colores</p> 
	 */
	private void selectColorComponent() {
		this.selectColorPanel = createPanelLabeled("Color Selection", Color.LIGHT_GRAY);

		this.pieceColorCombo = new JComboBox<Piece>();
		this.selectColorPanel.add(pieceColorCombo);

		JButton setColor = new JButton("Choose Color");
		setColor.setToolTipText("Why not to try a diferent look for the pieces??");
		this.selectColorPanel.add(setColor);

		setColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Piece p = (Piece) pieceColorCombo.getSelectedItem();
				ColorChooser choosedColor = new ColorChooser(new JFrame(), "Select Piece Color", pieceColors.get(p));

				if (choosedColor.getColor() != null) {
					pieceColors.put(p, choosedColor.getColor());
					repaint();
				}
			}

		});
		this.ControllerPanel.add(this.selectColorPanel);
	}
	
//-------------------------COMPONENTE DE CAMBIO DE MODOS-------------------------------//	
	
	/**
	 * <b>selectModePlayerPanel</b>
	 * <p>Panel contenedor del los desplegables y boton de cambio de modos </p>
	 */
	private JPanel selectModePlayerPanel;
	
	
	/**
	 * <b>modeCombo</b>
	 * <p>desplegable con el contenido de piezas del juego para el cambio de modos</p>
	 */
	private JComboBox<Piece> pieceModeCombo;
	
	/**
	 * <b>modeCombo</b>
	 * <p>desplegable con el contenido de tipos de modos para los jugadores</p>
	 */
	private JComboBox<PlayerMode> modeCombo;
	
	/**
	 * <b>selectModePlayerComponent</b>
	 * <p> Component creation process for the modes change </p>
	 * <p> Procedimiento de creación del componente de cambio de modos</p> 
	 */
	@SuppressWarnings("serial")
	private void selectModePlayerComponent() {

		this.selectModePlayerPanel = createPanelLabeled("Select Mode", Color.LIGHT_GRAY);
		this.modeCombo = new JComboBox<PlayerMode>();
		this.modeCombo.addItem(PlayerMode.MANUAL);

		if (randPlayer != null) {
			modeCombo.addItem(PlayerMode.RANDOM);
		}

		if (aiPlayer != null) {
			modeCombo.addItem(PlayerMode.AI);
		}
		this.pieceModeCombo = new JComboBox<Piece>(new DefaultComboBoxModel<Piece>() {
			@Override
			public void setSelectedItem(Object o) {
				super.setSelectedItem(o);
				if (playersModes.get(o) != PlayerMode.MANUAL) {
					modeCombo.setSelectedItem(PlayerMode.AI);
				} else {
					modeCombo.setSelectedItem(PlayerMode.MANUAL);
				}
			}
		});

		this.selectModePlayerPanel.add(pieceModeCombo);
		this.selectModePlayerPanel.add(modeCombo);

		JButton setMode = new JButton("Set");
		setMode.setToolTipText("Set the game mode for the player choosed on the dropdowns");
		this.selectModePlayerPanel.add(setMode);
		setMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Piece p = (Piece) pieceModeCombo.getSelectedItem();
				PlayerMode m = (PlayerMode) modeCombo.getSelectedItem();
				PlayerMode currMode = playersModes.get(p);
				playersModes.put(p, m);
				infoTable.refresh();
				if (currMode == PlayerMode.MANUAL && m != PlayerMode.MANUAL) {
					caseMakeAutomaticMove();
				}
			}
		});
		this.ControllerPanel.add(this.selectModePlayerPanel);
	}

	
//--------------------------COMPONENTE DE MOVIMIENTO AUTOMATICO-------------------------------//	
	
	/**
	 * <b>automaticMovePanel</b>
	 * <p>Panel contenedor del los botones para la ejecucion de movimientos automaticos</p>
	 * <p>container panel buttons for the execution of automatic movements<p>
	 */
	private JPanel automaticMovePanel;
	
	/**
	 * <b>automaticMoveComponent</b>
	 * <p> Component creation process for the automatic movements </p>
	 * <p> Procedimiento de creación del componente de movimientos automaticos</p> 
	 */
	private void automaticMoveComponent() {

		this.automaticMovePanel = createPanelLabeled("Automatic Move", Color.LIGHT_GRAY);

		JButton random = new JButton("Random");
		random.setToolTipText("Decide an aleatory movement for this turn");
		random.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeControllerMove(randPlayer);
			}
		});
		this.automaticMovePanel.add(random);

		JButton intelligent = new JButton("Intelligent");
		intelligent.setToolTipText("Simulate an intelligent movement for this turn");
		intelligent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeControllerMove(randPlayer);
			}
		});
		this.automaticMovePanel.add(intelligent);

		this.ControllerPanel.add(this.automaticMovePanel);
	}
	
//--------------------------COMPONENTE DE SALIDA Y RESET-------------------------------//
	
	/**
	 * <b>exitPanel</b>
	 * <p>Panel contenedor del los botones para salir o reiniciar el juego</p>
	 * <p>container panel buttons for exit or restart the game<p>
	 */
	private JPanel exitPanel;

	/**
	 * <b>exitComponent</b>
	 * <p> Component creation process for the automatic movements </p>
	 * <p> Procedimiento de creación del componente de movimientos automaticos</p> 
	 */
	private void exitComponent() {

		this.exitPanel = createPanelLabeled("Finish Game", Color.LIGHT_GRAY);

		JButton quit = new JButton("Quit");
		quit.setToolTipText("Exit the game and finish the play");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (n == 0) {
					try {
						controller.stop();
					} catch (GameError _e) {

					}
					setVisible(false);
					dispose();
					System.exit(0);

				}
			}
		});

		this.exitPanel.add(quit);

		JButton restartButton = new JButton("Restart");
		restartButton.setToolTipText("Reset the game to start a new play");

		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are you sure you want to restart the game?",
						"Restart", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (n == 0) {
					try {
						controller.restart();
						newGame = true;
					} catch (GameError _e) {

					}
					setVisible(true);
					repaint();
				}
			}
		});

		this.exitPanel.add(restartButton);

		this.ControllerPanel.add(this.exitPanel);

	}

//--------------------------METODOS DEL MOVIMIENTO AUTOMATICO-------------------------------//
	
	
	/**
	 * <b>decideMakeManualMove</b>
	 * <p> Execution of a procedure manual movement</p>
	 * <p> Procedimiento de ejecucion de un movimiento manual</p> 
	 */
	final protected void caseMakeManualMove(Player manualPlayer) {
		if (this.inMove || !this.inPlay)
			return;
		if (this.localPiece != null && !this.localPiece.equals(turn))
			return;
		if (this.playersModes.get(turn) != PlayerMode.MANUAL)
			return;
		executeControllerMove(manualPlayer);
	}
	
	/**
	 * <b>caseMakeAutomaticMove</b>
	 * <p> Procedure for execution of an automatic movement </p>
	 * <p> Procedimiento de ejecucion de un movimiento automatico</p> 
	 */
	final protected void caseMakeAutomaticMove() {
		if (this.inMove && !this.inPlay)
			return;
		if (this.localPiece != null && !this.localPiece.equals(this.turn))
			return;
		switch (this.playersModes.get(this.turn)) {
		case AI:
			executeControllerMove(this.aiPlayer);
			break;

		case RANDOM:
			executeControllerMove(this.randPlayer);
			break;

		default:
			break;
		}
	}

	/**
	 * <b>decideMakeAutomaticMove</b>
	 * <p> Procedure for execution of an movement </p>
	 * <p> Procedimiento de ejecucion de un movimiento</p> 
	 */
	private void executeControllerMove(final Player player) {
		this.setEnabled(false);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					controller.makeMove(player);
				} catch (GameError e) {
				}
			}
		});
		this.setEnabled(true);
	}

//------------------------------EVENTOS DEL OBSERVADOR-------------------------------//		
	@Override
	public void onGameStart(final Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleGameStart(board, gameDesc, pieces, turn);
			}
		});
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleGameOver(board, state, winner);
			}
		});
	}
	
	@Override
	public void onMoveStart(Board board, Piece turn) {
		if (this.turn == turn)
			this.inMove = true;
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		if (this.turn == turn)
			this.inMove = false;
		if (!success)
			handleChangeTurn(turn);
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		this.requestFocusInWindow();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleChangeTurn(turn);
			}
		});
	}

	@Override
	public void onError(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
//-------------------------HANDLERS DE LOS EVENTOS OBSERVADOR-------------------------------//	
	/**
	 * <b>handleGameStart</b>
	 * <p>handle process of status Game Start</p>
	 * @param board of the model
	 * @param gameDesc of the model
	 * @param pieces of the model
	 * @param turn of the model
	 */
	private void handleGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.setTitle(gameDesc + (this.localPiece == null ? "" : "(" + this.localPiece + ")"));
		this.board = board;
		this.pieces = pieces;
		this.turn = turn;
		this.inPlay = true;
		this.newGame = true;

		initBoardGui(this.controller, this.game);
		// GENERAR COLORES PARA LAS PIEZAS
		pieceColorCombo.removeAllItems();
		for (Piece p : pieces) {
			if (pieceColors.get(p) == null)
				pieceColors.put(p, Utils.randomColor());
			pieceColorCombo.addItem(p);
		}

		// GENERAR MODOS PARA LOS JUGADORES
		if (this.localPiece == null) { // MONOVISTA
			for (Piece p : pieces)
				if (this.playersModes.get(p) == null) {
					this.playersModes.put(p, PlayerMode.MANUAL);
					pieceModeCombo.addItem(p);
				}
		}

		else { // MULTIVISTA
			if (this.playersModes.get(this.localPiece) == null) {
				for (Piece localPiece : pieces)
					this.playersModes.put(localPiece, PlayerMode.MANUAL);
				pieceModeCombo.addItem(localPiece);
			}

		}
		this.gameMessages.setText("");
		this.addMessageToTextArea(" --  --   GAME START  --  -- *");

		handleChangeTurn(turn);
	}

	/**
	/**
	 * <b>handleGameStart</b>
	 * <p>handle process of status game over</p>
	 * @param board of the model
	 * @param state of the game
	 * @param winner of the game
	 */
	private void handleGameOver(Board board, State state, Piece winner) {
		this.infoTable.refresh();
		this.board = board;

		this.addMessageToTextArea(" GAME OVER *");

		switch (state) {
		case Won:
			MakeSound.RunPlaySound("sound/winner.wav");
			this.addMessageToTextArea(" " + winner + " is the winner!!");
			break;
		case Draw:
			MakeSound.RunPlaySound("sound/draw.wav");
			this.addMessageToTextArea("Look at this, we have a draw!!");
			break;
		case Stopped:
			this.addMessageToTextArea(" Expecting to see you soon!! ");
			break;
		default:
			this.addMessageToTextArea(" Something is weird here.... ");
			break;

		}

		int n = JOptionPane.showOptionDialog(new JFrame(), "Want another play?", "New Game", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (n == 0) {
			try {
				controller.restart();
				newGame = true;
			} catch (GameError _e) {

			}
			this.setEnabled(true);

		} else {
			controller.stop();
			this.setEnabled(false);
			dispose();
			System.exit(0);
		}

	}	

	/**
	 * <b>handleGameStart</b>
	 * <p>handle process of status change turn</p>
	 * @param board of the model
	 * @param turn
	 */
	private void handleChangeTurn(Piece turn) {
		this.infoTable.refresh();
		this.turn = turn;
		this.addMessageToTextArea("Turn for " + (turn.equals(localPiece) ? "You!" : turn.toString()));
		if ((localPiece == null || localPiece.equals(turn)) && playersModes.get(turn) == PlayerMode.MANUAL)
			this.setEnabled(true);
		else
			this.setEnabled(false);
		caseMakeAutomaticMove();
	}
	
//-----------------------METODOS ABSTRACTOS DE CLASE--------------------------//
	/**
	 * <b>initBoardGui</b>
	 * <p>implementation of the boar game, it´s supposed to be created on the FiniteRectSwingView</p>
	 * @param ctrl of the game
	 * @param game observable of the model
	 */
	protected abstract void initBoardGui(Controller ctrl, Observable<GameObserver> game);
	
	/**
	 * <b>activateBoard</b>
	 * <p>whenever it´s used, the board of the game have to be enabled</p>
	 */
	protected abstract void activateBoard();
	
	/**
	 * <b>deActivateBoard</b>
	 * <p>whenever it´s used, the board of the game have to be disabled</p>
	 */
	protected abstract void deActivateBoard();
	
	/**
	 * <b>redrawBoard</b>
	 * <p>whenever it´s used, the board of the game have to be repaint</p>
	 */
	protected abstract void redrawBoard();
	
	
	

//-----------------------MODELO DE TABLA PARA LA INFORMACION DE JUGADORES--------------------------//	
	
	
	@SuppressWarnings("serial")
	class PlayerInformationTable extends DefaultTableModel {

		String[] columnNames;

		public PlayerInformationTable() {

			this.columnNames = new String[] { "Player", "Mode", "#Pieces" };
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return (pieces == null ? 0 : pieces.size());
		}

		public Object getValueAt(int row, int col) {
			Object object = null;
			if (pieces != null) {
				Piece piece = pieces.get(row);
				switch (col) {
				case 0:
					object = piece;
					break;
				case 1:
					object = playersModes.get(piece);
					break;
				case 2:
					object = board.getPieceCount(piece);
					break;
				default:
					break;
				}
			}
			return object;
		}
		
		/**
		 * <b>refresh</b>
		 * <p>Procedure to refresh the data in the table according to the current state of the model</p>
		 * <p>Procedimiento para refrescar los datos de la tabla segun el actual estado del modelo</p>
		 */
		public void refresh(){
			this.fireTableDataChanged();
		}
	}
	
}
