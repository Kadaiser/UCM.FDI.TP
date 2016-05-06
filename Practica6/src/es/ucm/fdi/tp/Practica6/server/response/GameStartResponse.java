package es.ucm.fdi.tp.Practica6.server.response;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameStartResponse implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public GameStartResponse(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void rum(GameObserver o) {
		// TODO Auto-generated method stub

	}

}
