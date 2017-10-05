package es.ucm.fdi.tp.practica.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;


public class AtaxxRandomPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		int options, option;
		List<GameMove> validMoves = new ArrayList<GameMove>();
		
		if (board.isFull()) {
			throw new GameError("The board is full, cannot make a random move!!");
		}
		validMoves = rules.validMoves(board, pieces, p);
		options = validMoves.size();
		
		if(options <= 0)
			throw new GameError("player " + p.getId() + " cannot move any pieces");
		
		option = Utils.randomInt(options); //throws IllegalArgumentException if size = 0

		return validMoves.get(option);
	}

}
