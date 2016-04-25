package es.ucm.fdi.tp.practica5.connectn;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.RectBoardSwingView;
import es.ucm.fdi.tp.practica5.view.sound.MakeSound;

public class ConnectNSwingview extends RectBoardSwingView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ConnectNSwingPlayer player;

	public ConnectNSwingview(Observable<GameObserver> game, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(game, c, localPiece, randPlayer, aiPlayer);
		MakeSound.RunPlaySound("sound/conectnopen.wav");
		player = new ConnectNSwingPlayer();
	}
	
	@Override
	protected void handelMouseClick(int row, int col, int clickcounter, int mouseButton) {
		if(this.inPlay && mouseButton == 1){
			player.setMoveValue(row, col);
			this.caseMakeManualMove(player);
			this.addMessageToTextArea(turn.toString() + " coloca una pieza en (" + row + "," + col +")");
		}
		
	}

	@Override
	protected void handleMousePressed(int row, int col, int clickcounter, int mouseButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMouseReleased(int row, int col, int clickcounter, int mouseButton) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMoveStart(Board board, Piece turn) {
		if (this.getTurn() == null)
			this.inMove = true;
	}
}

