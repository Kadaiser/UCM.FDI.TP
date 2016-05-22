package es.ucm.fdi.tp.Practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class ChangeTurnResponse implements Response {

	private static final long serialVersionUID = 1L;
	private Board board;
	private Piece turn;

	/**
	 * <b>ChangeTurnResponse</b>
	 * <p>Contructor de la respuesta enviada en el canal de comunicacion</p>
	 * @param board of the game
	 * @param turn of the piece
	 */
	public ChangeTurnResponse(Board board, Piece turn) {
		this.board = board;
		this.turn = turn;
	}

	@Override
	public void run(GameObserver o) {
		o.onChangeTurn(board, turn);
	}

}
