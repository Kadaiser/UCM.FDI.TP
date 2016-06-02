package es.ucm.fdi.tp.practica4.FourLine;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class FourLineMove extends GameMove {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int col;
	
	public FourLineMove(){
		
	}
	
	public FourLineMove(int col, Piece p){
		super(p);
		this.col = col;
		}
	
	@Override
	public void execute(Board board, List<Piece> pieces) {
		Piece p = getPiece();
		int row = 1;
		
		if(board.getPosition(row, this.col) != null)
			throw new GameError("Columm (" + this.col + ") is full");
		
		while(row + 1 < board.getRows() &&  board.getPosition(row + 1, this.col) == null){
			row++;
		}
		
		board.setPosition(row, this.col, p);
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 1) {
			return null;
		}

		try {
			int col;
			col = Integer.parseInt(words[0]);
			return createMove(col, p);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	private GameMove createMove(int col, Piece p) {
		return new FourLineMove(col, p);
	}

	@Override
	public String help() {
			return "Deploy a piece at the selected column";
	}

}
