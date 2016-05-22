package es.ucm.fdi.tp.Practica6.server.response;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameStartResponse implements Response {

	private static final long serialVersionUID = 1L;
	private Board board;
	private String gameDesc;
	private List<Piece> pieces;
	private Piece turn;

	/**
	 * <b>ChangeTurnResponse</b>
	 * <p>Contructor de la respuesta enviada en el canal de comunicacion</p>
	 * @param board of the game
	 * @param gameDesc description of game
	 * @param pieces list of players
	 * @param turn piece of the turn
	 */
	public GameStartResponse(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		this.gameDesc = gameDesc;
		this.pieces = pieces;
		this.turn = turn;
	}

	@Override
	public void run(GameObserver o) {
		o.onGameStart(board, gameDesc, pieces, turn);

	}

}
