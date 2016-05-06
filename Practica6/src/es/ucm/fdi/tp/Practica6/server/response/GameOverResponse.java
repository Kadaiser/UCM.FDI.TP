package es.ucm.fdi.tp.Practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameOverResponse implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameOverResponse(Board board, State state, Piece winner) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void rum(GameObserver o) {
		// TODO Auto-generated method stub

	}

}
