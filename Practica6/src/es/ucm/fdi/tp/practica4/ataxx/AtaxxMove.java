package es.ucm.fdi.tp.practica4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxMove;

/**
 * A Class representing a move for Ataxx.
 * 
 * <p>
 * Clase para representar un movimiento del juego Ataxx.
 * 
 */

public class AtaxxMove extends GameMove {

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
	protected int originRow;

	/**
	 * The column where the piece is return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int originCol;
	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int destinyRow;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int destinyCol;

	/**
	 * This constructor should be used ONLY to get an instance of
	 * {@link AtaxxMove} to generate game moves from strings by calling
	 * {fromString(String)}
	 * 
	 * <p>
	 * Solo se debe usar este constructor para obtener objetos de
	 * {@link AtaxxMove} para generar movimientos a partir de strings usando
	 * el metodo {fromString(String)}
	 * 
	 */

	public AtaxxMove() {
	}

	/**
	 * Constructs a move for placing a piece of the type referenced by {@code p}
	 * at position ({@code row},{@code col}).
	 * 
	 * <p>
	 * Construye un movimiento para colocar una ficha del tipo referenciado por
	 * {@code p} en la posicion ({@code row},{@code col}).
	 * 
	 * @param originRow
	 *            Number of origin row.
	 *            <p>
	 *            Numero de origen fila.
	 * @param originCol
	 *            Number of origin column.
	 *            <p>
	 *            Numero de origen columna.
	 * @param destinyRow
	 *            Number of destiny row.
	 *            <p>
	 *            Numero de destino fila.
	 * @param destinyCol
	 *            Number of destiny column.
	 *            <p>
	 *            Numero de destino columna.
	 * @param p
	 *            A piece to be place at ({@code row},{@code col}).
	 *            <p>
	 *            Ficha a colocar en ({@code row},{@code col}).
	 */
	public AtaxxMove(int originRow, int originCol, int destinyRow, int destinyCol, Piece p) {
		super(p);
		this.originRow = originRow;
		this.originCol = originCol;
		this.destinyRow = destinyRow;
		this.destinyCol = destinyCol;
	}
	@Override
	public void execute(Board board, List<Piece> pieces) {
			Piece p = getPiece();
			int distance = Math.max(Math.abs(this.originRow - this.destinyRow), Math.abs(this.originCol - this.destinyCol));
			this.checkMove(board, p, distance);
	
				if(distance == 1){
					board.setPosition(this.destinyRow, this.destinyCol, p);
					board.setPieceCount(p, board.getPieceCount(p) + 1);
				}
				else if(distance == 2){
					board.setPosition(this.destinyRow, this.destinyCol, p);
					board.setPosition(this.originRow, this.originCol, null);
				}
			this.convertPiecesArround(board, pieces, p);
	}

	
	private void checkMove(Board board, Piece p, int distance) throws GameError{
		
		if (board.getPieceCount(p) <= 0)
			throw new GameError("There are no pieces of type " + p + " available");
		else if (board.getPosition(this.originRow, this.originCol) == null) 
			throw new GameError("Position (" + this.originRow + "," + this.originCol + ") there is not piece there.");
		else if (board.getPosition(this.destinyRow, this.destinyCol) != null) 
			throw new GameError("Position (" + this.destinyRow + "," + this.destinyCol + ") is already occupied");
		else if(board.getPosition(this.originRow, this.originCol) != p)
			throw new GameError("Position (" + this.originRow + "," + this.originCol + ") is a piece from other player!!");
		else if(distance > 2)
			throw new GameError("Position (" + this.destinyRow + "," + this.destinyCol + ") distance is larger than 2 from the origin");
	}
		
	private void convertPiecesArround(Board board, List<Piece> pieces, Piece piece){
		
		int rows = board.getRows();
		int cols = board.getCols();
			 
		for(int r = Math.max(this.destinyRow - 1, 0); r <= Math.min(this.destinyRow + 1, rows - 1); r++) 
			for (int c = Math.max(this.destinyCol - 1, 0); c <= Math.min(this.destinyCol + 1, cols - 1); c++) 
				if(board.getPosition(r, c) != null  && pieces.contains(board.getPosition(r, c))){
					if(piece.getId() != board.getPosition(r, c).getId() ){
						board.setPieceCount(board.getPosition(r, c), board.getPieceCount(board.getPosition(r, c)) - 1);
						board.setPieceCount(piece, board.getPieceCount(piece) + 1);
						board.setPosition(r, c, piece);
						}
					}
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 4) {
			return null;
		}

		try {
			int originRow, originCol, destinyRow, destinyCol;
			originRow = Integer.parseInt(words[0]);
			originCol = Integer.parseInt(words[1]);
			destinyRow = Integer.parseInt(words[2]);
			destinyCol = Integer.parseInt(words[3]);
			return createMove(originRow, originCol, destinyRow, destinyCol, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Creates a move that is called from {@link #fromString(Piece, String)}.
	 * Separating it from that method allows us to use this class for other
	 * similar games by overriding this method.
	 * 
	 * <p>
	 * Crea un nuevo movimiento con la misma ficha utilizada en el movimiento
	 * actual. Llamado desde {@link #fromString(Piece, String)}; se separa este
	 * metodo del anterior para permitir utilizar esta clase para otros juegos
	 * similares sobrescribiendo este metodo.
	 * 
	 * @param row
	 *            Row of the move being created.
	 *            <p>
	 *            Fila del nuevo movimiento.
	 * 
	 * @param col
	 *            Column of the move being created.
	 *            <p>
	 *            Columna del nuevo movimiento.
	 */
	protected GameMove createMove(int originRow, int originCol, int destinyRow, int destinyCol, Piece p) {
		return new AtaxxMove(originRow, originCol, destinyRow, destinyCol, p);
	}
	@Override
	public String help() {
		return "'originRow originCol destinyRow destinyCol', to place/create a piece at the corresponding position.";
	}
}
