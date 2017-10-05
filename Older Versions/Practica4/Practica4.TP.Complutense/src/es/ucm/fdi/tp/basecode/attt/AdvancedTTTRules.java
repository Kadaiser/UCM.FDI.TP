package es.ucm.fdi.tp.basecode.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.ttt.TicTacToeRules;

/**
 * Rules for Advanced Tic-Tac-Toe. It is exactly like Tic-Tac-Toe, but each
 * player has at most 3 pieces.
 * 
 * <p>
 * Reglas del juego Tic-Tac-Toe avanzado. Es como Tic-Tac-Toe, pero cada jugador
 * tiene como mucho 3 fichas.
 */
public class AdvancedTTTRules extends TicTacToeRules {
	@Override
	public String gameDesc() {
		return "Advanced Tic-Tac-Toe";
	}

	@Override
	public Board createBoard(List<Piece> playersPieces) {
		Board b = new FiniteRectBoard(3, 3);
		for (Piece p : playersPieces) {
			b.setPieceCount(p, 3);
		}
		return b;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> playersPieces, Piece lastPlayer) {
		List<Piece> pieces = playersPieces;
		int numPieces = pieces.size();

		int i = pieces.indexOf(lastPlayer);
		int j = (i + 1) % numPieces;
		while (i != j && board.getPieceCount(pieces.get(j)) == 0) {
			j = (j + 1) % numPieces;
		}
		if (j != i) {
			return pieces.get(j);
		} else {
			return null;
		}
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> playersPieces, Piece lastPlayer) {
		Pair<State, Piece> r = super.updateState(board, playersPieces, lastPlayer);

		// draw if no one has more pieces
		if (r.getFirst() == State.InPlay && nextPlayer(board, playersPieces, lastPlayer) == null) {
			return new Pair<State, Piece>(State.Draw, null);
		} else {
			return r;
		}
	}

}
