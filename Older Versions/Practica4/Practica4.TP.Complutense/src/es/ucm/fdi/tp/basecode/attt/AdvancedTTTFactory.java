package es.ucm.fdi.tp.basecode.attt;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.ttt.TicTacToeFactory;

/**
 * A factory for Advanced Tic-Tac-Toe. See {@link AdvancedTTTRules} for the game
 * rules.
 * 
 * <p>
 * Factoria para el juego Tic-Tac-Toe avanzado. Vease {@link AdvancedTTTRules}
 * para ver las reglas del juego.
 */
public class AdvancedTTTFactory extends TicTacToeFactory {

	@Override
	public GameRules gameRules() {
		return new AdvancedTTTRules();
	}

	@Override
	public Player createRandomPlayer() {
		return new AdvancedTTTRandomPlayer();
	}

}
