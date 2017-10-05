package es.ucm.fdi.tp.practica5.ataxx;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.RectBoardSwingView;
import es.ucm.fdi.tp.practica5.view.sound.MakeSound;

public class AtaxxSwingView extends RectBoardSwingView {
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
	
	/**
	 * Define if its the second click for the movement
	 */
	//private boolean secondClick;

	private AtaxxSwingPlayer player;

	public AtaxxSwingView(Observable<GameObserver> game, Controller c, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		super(game, c, localPiece, randPlayer, aiPlayer);
		MakeSound.RunPlaySound("sound/ataxxopen.wav");
	//	this.secondClick = false;
		this.player = new AtaxxSwingPlayer();
	}

	@Override
	protected void handelMouseClick(int row, int col, int clickCount, int mouseButton) {
		
		/*
		 * if(!secondClick){ this.originRow = row; this.originCol = col;
		 * this.secondClick = true;
		 * 
		 * } else{ this.destinyRow = row; this.destinyCol = col;
		 * this.secondClick = false;
		 * 
		 * this.player.setMoveValue(this.originRow, this.originCol,
		 * this.destinyRow, this.destinyCol); if(mouseButton == 1)
		 * this.caseMakeManualMove(this.player); }
		 */
	}

	@Override
	protected void handleMousePressed(int row, int col, int clickCount, int mouseButton) {
		this.originRow = row;
		this.originCol = col;

	}

	@Override
	protected void handleMouseReleased(int row, int col, int clickCount, int mouseButton) {
		this.destinyRow = row;
		this.destinyCol = col;
		this.player.setMoveValue(this.originRow, this.originCol, this.destinyRow, this.destinyCol);
		this.caseMakeManualMove(this.player);

	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		if (this.getTurn() == null)
			this.inMove = true;
		this.addMessageToTextArea(turn.toString() + " mueve pieza de (" + this.originRow + "," + this.originCol
				+ ") a (" + this.destinyRow + "," + this.destinyCol + ")");
	}

}
