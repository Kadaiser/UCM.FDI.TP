package es.ucm.fdi.tp.practica4.buscaminas;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class BuscaMinasRules implements GameRules{
	
	private final static int MINPLAYERS = 1;
	private final static int MAXPLAYERS = 1;
	
	private int dimRows;
	private int dimCols;
	private int numBombs;
	private final Piece bomb = new Piece("*");
	private static boolean GameOver = false;
	
	public static void setGameOver(boolean set){
		GameOver = set;
	}
		
	public BuscaMinasRules(int dimRows, int dimCols, int numBombs) {
		if(dimRows < 4 || dimCols < 4){
			throw new GameError("Dimension must be at least 4 :" + dimRows + " x " + dimCols);
		}
		this.dimRows = dimRows;
		this.dimCols = dimCols;
		this.numBombs = numBombs;
	}

	@Override
	public String gameDesc() {
		return "Buscaminas game " + this.dimRows + "x" + this.dimCols;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		Board board = new  FiniteRectBoard(this.dimRows, this.dimCols);

		if(this.numBombs > 0)
			fillOfBombs(board);	
		return board;
	}
	
	private void fillOfBombs(Board board){
		int r, c;
		int counter = this.numBombs;
		while(counter != 0){
			r = Utils.randomInt(this.dimRows);
			c = Utils.randomInt(this.dimCols);
			if(board.getPosition(r, c) == null){
				board.setPosition(r, c, this.bomb);
				counter--;
			}
		}
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {
		return pieces.get(0);
	}

	@Override
	public int minPlayers() {
		return this.MINPLAYERS;
	}

	@Override
	public int maxPlayers() {
		return this.MAXPLAYERS;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> pieces, Piece turn) {
		Pair<State, Piece> result = new Pair<State, Piece>(State.InPlay, null) ;
		if(board.isFull()){
			result = new Pair<State, Piece>(State.Won, turn) ;
		}
		else if(GameOver){
			setGameOver(false);
			result = new Pair<State, Piece>(State.Won, null) ;
		}
		
		return result;
	}

	
	@Override
	public Piece nextPlayer(Board board, List<Piece> pieces, Piece turn) {
		int i = pieces.indexOf(turn);
		return pieces.get((i + 1) % pieces.size());
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> pieces, Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();
		
		for (int i = 0; i < board.getCols(); i++) 
			for (int j = 0; j < board.getRows(); j++)
					moves.add(new BuscaMinasMove(i, j, turn));
		return moves;	
	}
	
	
	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p) {
		// TODO Auto-generated method stub
		return 0;
	}
}
