package es.ucm.fdi.tp.basecode.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectN.ConnectNMove;

/**
 * A class representing a move for Advance Tic-Tac-Toe. It is similar to
 * {@link AtaxxMove}, but it takes the piece-count into account. Note that it
 * inherits the fromString capabilities from AtaxxMove.
 * 
 * <p>
 * Clase que representa un movimiento de Tic-Tac-Toe avanzado. Es similar a
 * {@link AtaxxMove}, pero tiene en cuenta el contador de fichas. Observa que
 * hereda de AtaxxMove el metodo formString.
 */

public class AdvancedTTTMove extends ConnectNMove {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdvancedTTTMove() {
	}

	/**
	 * Construct a move that places a piece of type {@code p} at position (
	 * {@code row},{@code col}).
	 * 
	 * <p>
	 * Construye un movimiento que coloca una ficha de tipo {@code p} en la
	 * posicion ({@code row},{@code col}).
	 * 
	 * @param row
	 *            Number of row.
	 *            <p>
	 *            Numero de fila.
	 * @param col
	 *            Number of column.
	 *            <p>
	 *            Numero de columna.
	 * @param p
	 *            A piece to place at position ({@code row},{@code col}).
	 *            <p>
	 *            Ficha que se debe colocar en la posicion ({@code row},
	 *            {@code col}).
	 */
	public AdvancedTTTMove(int row, int col, Piece p) {
		super(row, col, p);
	}

	@Override
	public void execute(Board board, List<Piece> pieces) {
		Piece p = getPiece();

		if (board.getPieceCount(p) <= 0) {
			throw new GameError("There are no pieces of type " + p + " available");
		} else if (board.getPosition(row, col) != null) {
			throw new GameError("Position (" + row + "," + col + ") is already occupied");
		} else {
			board.setPosition(row, col, p);
			board.setPieceCount(p, board.getPieceCount(p) - 1);
		}
	}

	@Override
	protected GameMove createMove(int row, int col, Piece p) {
		return new AdvancedTTTMove(row, col, p);
	}

}
