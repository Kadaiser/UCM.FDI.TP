package es.ucm.fdi.tp.Practica6.server;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {

	public GameServer(Game game, List<Piece> pieces) {
		super(game, pieces);
		// TODO Auto-generated constructor stub
	}

	public synchronized void MakeMove(Player player) {
		try {
			super.makeMove(player);
		} catch (GameError e) {
		}
	}

	public synchronized void stop() {
		try {
			super.stop(player);
		} catch (GameError e) {

		}
	}

	public synchronized void restart() {
		try {
			super.restart(player);
		} catch (GameError e) {
		}
	}

	public void start() {
		controlGUI();
		startServer();
	}

//------------------------------------------OBSERVABLE EVENTS--------------------------------------//

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub

	}

}
