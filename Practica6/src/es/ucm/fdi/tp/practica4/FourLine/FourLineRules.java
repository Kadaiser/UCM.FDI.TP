package es.ucm.fdi.tp.practica4.FourLine;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class FourLineRules implements GameRules {

	
	private int dimRows;
	private int dimCols;
	
	
	public FourLineRules(int dimRows, int dimCols) {
		
		if(dimRows < 7 || dimCols < 7){
			throw new GameError("Dimension must be at least 4 :" + dimRows + " x " + dimCols);
		}
		this.dimRows = dimRows;
		this.dimCols = dimCols;
	}

	@Override
	public String gameDesc() {
		return "FourLine " + this.dimRows + "x" + this.dimCols;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		return new FiniteRectBoard(this.dimRows, this.dimCols);
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {
		return pieces.get(0);
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int maxPlayers() {
		return 4;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> pieces, Piece turn) {
		int counter;
		Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);

		//Check Rows
		for(int i= 1; i < this.dimRows; i++ ){
			counter = 0;
			for(int j = 0; j < this.dimCols; j++){
				if(counter == 4){
				 return new Pair<State, Piece>(State.Won, turn);
				}
				else{
					if(turn.equals(board.getPosition(i, j)))
						counter++;
					else
						counter = 0;
				}
			}
		}

		if (board.isFull()) {
			return new Pair<State, Piece>(State.Draw, null);
		}

		return gameInPlayResult;
	
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> pieces, Piece turn) {
		List<Piece> playerPieces = pieces;
		int i = playerPieces.indexOf(turn);
		return playerPieces.get((i + 1) % playerPieces.size());
	}

	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> pieces, Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();
		
			for (int j = 0; j < board.getCols(); j++) {
					moves.add(new ConnectNMove(0, j, turn));
			}
		return moves;
	}

}
