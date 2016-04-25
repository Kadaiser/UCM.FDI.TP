package es.ucm.fdi.tp.practica5.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AdvancedTTTSwingPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * The row where the piece is return return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originRow;

	/**
	 * The column where the piece is return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originCol;
	
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
	

	public AdvancedTTTSwingPlayer() {
		
	}

	/**
	 * <b>setMoveValue</b>
	 * <p>Procedimiento para almacenar los dos valores vectoriales de posicion en la superficie del tablero de un movimiento</p>
	 * @param originRow posicion de fila relativa del primer click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param originCol posicion de columna relativa del primer click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param destinyRow posicion de fila relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param destinyCol posicion de columna relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 */
	public void setMoveValue(int originRow, int originCol, int destinyRow, int destinyCol){
		this.originRow = originRow;
		this.originCol = originCol;
		this.destinyCol = destinyCol;
		this.destinyRow = destinyRow;
	}
	

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return this.GameCreateMove(this.originRow, this.originCol, this.destinyRow, this.destinyCol, p);
	}
	
	/**
	 * <b>GameCreateMove</b>
	 * <p>Metodo para la construccion de un movimiento especifico para un juego determinado</p>
	 * @param originRow posicion de fila relativa del primer click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param originCol posicion de columna relativa del primer click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param destinyRow posicion de fila relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param destinyCol posicion de columna relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param p pieza definida para realizar el movimiento
	 * @return un mocimiento para el tipo de juego especifico
	 */
	protected GameMove GameCreateMove(int originRow, int originCol, int destinyRow, int destinyCol, Piece p){
		return new AdvancedTTTMove(originRow, originCol, destinyRow, destinyCol, p) ;
	}
}
