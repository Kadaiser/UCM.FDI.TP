package es.ucm.fdi.tp.basecode.ttt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayerFromListOfMoves;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectN.ConnectNFactory;

/**
 * A Factory for Tic-Tac-Toe. It basically the same as the of ConnectN. Except
 * that it uses a different console player, creates different rules, and is
 * restricted to two players only.
 * 
 * <p>
 * Factoría del juego Tic-Tac-Toe (3 en raya). Es fundamentalmente el mismo
 * juego que ConnectN, excepto que utiliza un jugador de modo consola diferente
 * y crea reglas diferentes.
 * 
 */
public class TicTacToeFactory extends ConnectNFactory {

	@Override
	public GameRules gameRules() {
		return new TicTacToeRules();
	}

	@Override
	public Player createConsolePlayer() {
		return new ConsolePlayerFromListOfMoves(new Scanner(System.in));
	}

	/**
	 * By default, we have two player X and O.
	 * <p>
	 * Por defecto, dos jugadores, X y O.
	 */
	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		return pieces;
	}
}
