package es.ucm.fdi.tp.practica4.buscaminas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayer;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.DummyAIPlayer;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;
public class BuscaMinasFactory implements GameFactory {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int dimRows;
	private int dimCols;
	private int numBombs;
	
	public BuscaMinasFactory() {
		this.dimCols = 7;
		this.dimRows = 7;
		this.numBombs = this.dimCols * this.dimRows / 4;
	}
	
	public BuscaMinasFactory(int dimRows, int dimCols) {
		if(dimRows < 4 || dimCols < 4){
			throw new GameError("Dimension must be at least 4 :" + dimRows + " x " + dimCols);
		}
		this.dimRows = dimRows;
		this.dimCols = dimCols;
		this.numBombs = this.dimCols * this.dimRows / 4;
	}
	

	public BuscaMinasFactory(int dimRows, int dimCols, Integer numBombs) {
		if(dimRows < 4 || dimCols < 4){
			throw new GameError("Dimension must be at least 4 :" + dimRows + " x " + dimCols);
		}
		this.dimRows = dimRows;
		this.dimCols = dimCols;
		this.numBombs = numBombs;
	}

	@Override
	public GameRules gameRules() {
		return new BuscaMinasRules(this.dimRows, this.dimCols, this.numBombs);
	}

	@Override
	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new BuscaMinasMove());
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}

	@Override
	public Player createRandomPlayer() {
		return new BuscaMinasRandomPlayer();
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		return new DummyAIPlayer(createRandomPlayer(), 1000);
	}

	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> game, Controller ctrl) {
		new GenericConsoleView(game, ctrl);
		
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer,
			Player aiPlayer) {
		throw new UnsupportedOperationException("There is no swing view");
		
	}

}
