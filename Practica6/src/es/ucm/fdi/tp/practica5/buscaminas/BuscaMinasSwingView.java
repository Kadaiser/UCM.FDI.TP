package es.ucm.fdi.tp.practica5.buscaminas;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.RectBoardSwingView;

public class BuscaMinasSwingView extends RectBoardSwingView {

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
	private int destinyRow;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int destinyCol;
	
	private BuscaMinasSwingPlayer player;

	public BuscaMinasSwingView(Observable<GameObserver> game, Controller c, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		super(game, c, localPiece, randPlayer, aiPlayer);
		//MakeSound.RunPlaySound("sound/ataxxopen.wav");
		this.player = new BuscaMinasSwingPlayer();
	}
	
	@Override
	protected void handelMouseClick(int row, int col, int clickCount, int mouseButton) {
		this.destinyRow = row;
		this.destinyCol = col;
		this.player.setMoveValue(this.destinyRow, this.destinyCol);
		this.caseMakeManualMove(this.player);
	}

	@Override
	protected void handleMousePressed(int row, int col, int clickcounter, int mouseButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMouseReleased(int row, int col, int clickcounter, int mouseButton) {
		// TODO Auto-generated method stub
		
	}

}
