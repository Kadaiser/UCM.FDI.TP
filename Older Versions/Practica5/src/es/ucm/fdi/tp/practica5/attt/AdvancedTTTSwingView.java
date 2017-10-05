package es.ucm.fdi.tp.practica5.attt;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.RectBoardSwingView;
import es.ucm.fdi.tp.practica5.view.sound.MakeSound;

public class AdvancedTTTSwingView extends RectBoardSwingView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The row where the piece is return return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originRow;

	/**
	 * The column where the piece is return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se encuentra la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	private int originCol;
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
	
	private AdvancedTTTSwingPlayer player;
	
	private boolean secondClick;

	public AdvancedTTTSwingView(Observable<GameObserver> game, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(game, c, localPiece, randPlayer, aiPlayer);
		MakeSound.RunPlaySound("sound/atttopen.wav");
		this.player = new AdvancedTTTSwingPlayer();
		this.secondClick = false;
	}

	@Override
	protected void handelMouseClick(int row, int col, int clickcounter, int mouseButton) {
		if(mouseButton == 1){
			if (!secondClick) {
				this.originRow = row;
				this.originCol = col;
				this.secondClick = true;
			}

			else {
				this.destinyRow = row;
				this.destinyCol = col;
				this.secondClick = false;

				this.player.setMoveValue(this.originRow, this.originCol, this.destinyRow, this.destinyCol);
				this.caseMakeManualMove(this.player);
			}
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
		if (this.getTurn() == null) {
			this.inMove = true;
			if (board.getPieceCount(turn) < 5)
				this.addMessageToTextArea(turn.toString() + " mueve pieza de (" + this.originRow + "," + this.originCol
						+ ") a (" + this.destinyRow + "," + this.destinyCol + ")");
			else
				this.addMessageToTextArea(
						turn.toString() + " coloca una pieza en (" + this.destinyRow + "," + this.destinyCol + ")");
		}
	}
}