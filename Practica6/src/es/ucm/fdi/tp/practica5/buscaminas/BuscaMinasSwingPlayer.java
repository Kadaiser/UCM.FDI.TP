package es.ucm.fdi.tp.practica5.buscaminas;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.buscaminas.BuscaMinasMove;

public class BuscaMinasSwingPlayer extends Player{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int destinyRow;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int destinyCol;
	
	public BuscaMinasSwingPlayer(){
		
	}
	
	public void setMoveValue(int destinyRow, int destinyCol){
		this.destinyRow = destinyRow;
		this.destinyCol = destinyCol;
	}

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return this.GameCreateMove(this.destinyRow, this.destinyCol, p);
	}
	
	protected GameMove GameCreateMove(int destinyRow, int destinyCol, Piece p){
		return new BuscaMinasMove(destinyRow, destinyCol, p) ;
	}

}
