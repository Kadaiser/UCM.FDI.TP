package es.ucm.fdi.tp.basecode.attt;

import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectN.ConnectNRandomPlayer;

/**
 * A random player for Advanced Tic-Tac-Toe. It inherits the functionality from
 * {@link AtaxxRandomPlayer} just by overriding the method
 * {@link #createMove(int, int, Piece)}.
 * 
 * <p>
 * Jugador aleatorio para el juego Tic-Tac-Toe avanzado. Hereda la funcionalidad
 * de {@link AtaxxRandomPlayer} pues sobrescribe solamente el metodo
 * {@link #createMove(int, int, Piece)}.
 *
 */
public class AdvancedTTTRandomPlayer extends ConnectNRandomPlayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected GameMove createMove(int row, int col, Piece p) {
		return new AdvancedTTTMove(row, col, p);
	}
}
