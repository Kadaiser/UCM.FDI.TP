package es.ucm.fdi.tp.practica4.buscaminas;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class BuscaMinasMove extends GameMove{

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
	protected int destinyRow;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int destinyCol;

	public BuscaMinasMove(){
	}

	public BuscaMinasMove(int destinyRow, int destinyCol, Piece p) {
		super(p);
		this.destinyRow = destinyRow;
		this.destinyCol = destinyCol;
	}

	@Override
	public void execute(Board board, List<Piece> pieces) {
		Piece p = getPiece();
		this.checkMove(board, p, pieces);
	}

	private void checkMove(Board board, Piece p, List<Piece> pieces) throws GameError{
			
			if(board.getPosition(this.destinyRow, this.destinyCol)!= null && board.getPosition(this.destinyRow, this.destinyCol).getId().equals("*")){
				BuscaMinasRules.setGameOver(true);
				board.setPosition(this.destinyRow, this.destinyCol, new Piece("9"));
			}
			else if (board.getPosition(this.destinyRow, this.destinyCol)!= null)
				throw new GameError("Position (" + this.destinyRow + "," + this.destinyCol + ") is already cleared");
			else
				board.setPosition(this.destinyRow, this.destinyCol, new Piece(CountBombsArround(board, pieces, p)));

		}
	
	private String CountBombsArround(Board board, List<Piece> pieces, Piece piece){
		
		int rows = board.getRows();
		int cols = board.getCols();
		int numBombs = 0;
		String result = "";
			 
		for(int r = Math.max(this.destinyRow - 1, 0); r <= Math.min(this.destinyRow + 1, rows - 1); r++) 
			for (int c = Math.max(this.destinyCol - 1, 0); c <= Math.min(this.destinyCol + 1, cols - 1); c++) 
				if(board.getPosition(r, c) != null)
					if(board.getPosition(r, c).getId().equals("*"))
						numBombs++;
		
		result += numBombs;
					
		return result;
	}
	
	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 2) {
			return null;
		}

		try {
			int destinyRow, destinyCol;
			destinyRow = Integer.parseInt(words[0]);
			destinyCol = Integer.parseInt(words[1]);
			return createMove(destinyRow, destinyCol, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}
		
	protected GameMove createMove(int destinyRow, int destinyCol, Piece p) {
		return new BuscaMinasMove(destinyRow, destinyCol, p);
	}

	@Override
	public String help() {
		return "'destinyRow destinyCol', to place/create a piece at the corresponding position.";
	}

}
