package es.ucm.fdi.tp.practica.ataxx;

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
import es.ucm.fdi.tp.practica.ataxx.AtaxxMove;
import es.ucm.fdi.tp.practica.ataxx.AtaxxRandomPlayer;

public class AtaxxFactory implements GameFactory {
	
	private int dimension;
	private int obstacles;
	
	
	
	public AtaxxFactory(){
		this.dimension = 5;
		this.obstacles = 0;
	}
	
	public AtaxxFactory(int dimension){
		if(dimension < 5){
			throw new GameError("Dimension must be at least 5 :" + dimension);
		}
		this.dimension = dimension;
		this.obstacles = 0;
	}
	
	
	public AtaxxFactory(int dimension, int obstacles){
		if(dimension < 5){
			throw new GameError("Dimension must be at least 5 :" + dimension);
		}
		else{
			if(obstacles > (dimension * dimension)- 8){
				throw new GameError("Obstacles must be less than " + dimension * dimension);
			}
			else{
				this.dimension = dimension;
				this.obstacles = obstacles;
			}
		}
	}

	@Override
	public GameRules gameRules() {
		return new AtaxxRules(this.dimension , this.obstacles);
	}

	@Override
	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new AtaxxMove());
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}

	@Override
	public Player createRandomPlayer() {
		return new AtaxxRandomPlayer();
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		return new DummyAIPlayer(createRandomPlayer(), 1000);
	}

	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> game, Controller ctrl) {
		new GenericConsoleView(game, ctrl);

	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) {
		throw new UnsupportedOperationException("There is no swing view");
	}

}
