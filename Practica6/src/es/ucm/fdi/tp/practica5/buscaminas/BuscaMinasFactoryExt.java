package es.ucm.fdi.tp.practica5.buscaminas;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.buscaminas.BuscaMinasFactory;

public class BuscaMinasFactoryExt extends BuscaMinasFactory {

	
	public BuscaMinasFactoryExt() {
		super();
	}
	
	public BuscaMinasFactoryExt(int dimRows, int dimCols) {
		super(dimRows,dimCols);
	}
	

	public BuscaMinasFactoryExt(int dimRows, int dimCols, Integer numBombs) {
		super(dimRows, dimCols, numBombs);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					new BuscaMinasSwingView(game, ctrl, viewPiece, randPlayer, aiPlayer);
					}
				});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
