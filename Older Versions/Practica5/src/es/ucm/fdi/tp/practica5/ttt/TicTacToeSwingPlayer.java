package es.ucm.fdi.tp.practica5.ttt;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

public class TicTacToeSwingPlayer extends Player {

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
	
	public TicTacToeSwingPlayer(){
		
	}
	/**
	 * <b>setMoveValue</b>
	 * <p>Procedimiento para almacenar el valores vectoriales de posicion en la superficie del tablero de un movimiento</p>
	 * @param destinyRow posicion de fila relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param destinyCol posicion de columna relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 */
	public void setMoveValue(int destinyRow, int destinyCol){
		this.destinyCol = destinyCol;
		this.destinyRow = destinyRow;
	}
	
	
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return this.GameCreateMove(this.destinyRow, this.destinyCol, p);
	}
	/**
	 * <b>GameCreateMove</b>
	 * <p>Metodo para la construccion de un movimiento especifico para un juego determinado</p>
	 * @param destinyRow posicion de fila relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param destinyCol posicion de columna relativa del segundo click en la superficie del tablero de la SwingView respecto del tablero del modelo.
	 * @param p pieza definida para realizar el movimiento
	 * @return un mocimiento para el tipo de juego especifico
	 */
	protected GameMove GameCreateMove(int destinyRow, int destinyCol, Piece p){
		return new ConnectNMove(destinyRow, destinyCol, p);
	}
	
}
