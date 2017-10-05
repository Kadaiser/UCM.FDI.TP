package es.ucm.fdi.tp.practica5.ataxx;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxFactory;

public class AtaxxFactoryExt extends AtaxxFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtaxxFactoryExt() {
		super();
	}

	public AtaxxFactoryExt(int dimension, int obstacles) {
		super(dimension, obstacles);
	}

	public AtaxxFactoryExt(int dimension) {
		super(dimension);
	}
	
	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AtaxxSwingView(game, ctrl, viewPiece, randPlayer, aiPlayer);
				}
			});

	}
}