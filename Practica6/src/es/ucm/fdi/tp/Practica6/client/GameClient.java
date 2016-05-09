package es.ucm.fdi.tp.Practica6.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import es.ucm.fdi.tp.Practica6.buffer.Connection;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameClient extends Controller implements Observable<GameObserver> {

	private String host;
	private int port;
	private List<GameObserver> observers;
	private Piece localPiece;
	private GameFactory gameFactory;
	private Connection connectioToServer;
	private boolean gameOver;
	// …

	public GameClient(String host, int port) throws Exception {
		super(null, null);
		this.host = host;
		this.port = port;
		connect();
	}

	private void connect() throws UnknownHostException, IOException {
			Socket s = new Socket(this.host,this.port);
			Connection c = new Connection(s);
			c.sendObject(new String("Connect"));
	}

	public GameFactory getGameFactoty() {
		return gameFactory;
	}

	public Piece getPlayerPiece() {
		return localPiece;
	}

	public GameClient(Game game, List<Piece> pieces) {
		super(game, pieces);
	}

	@Override
	public void addObserver(GameObserver o) {
	}

	@Override
	public void removeObserver(GameObserver o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeMove(Player p) {
		forwardCommand(new PlayCommand(p));
	}

	@Override
	public void stop() {
		forwardCommand(new QuitCommand());
	}

	@Override
	public void restart() {
		forwardCommand(new RestartCommand());
	}

	private void forwardCommand(Command cmd) {
		// if the game is over do nothing, otherwise
		// send the object cmd to the server
	}
}
