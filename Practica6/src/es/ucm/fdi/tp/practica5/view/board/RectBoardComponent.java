package es.ucm.fdi.tp.practica5.view.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
//import javax.swing.SwingUtilities;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

public abstract class RectBoardComponent extends JComponent implements GameObserver  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <b>board</b>
	 * <p>referencia al tablero de juego del modelo</p>
	 */
	protected Board board;
	
	/**
	 * A map that associates pieces Colors).
	 * 
	 * <p>
	 * Map que asocia fichas con colores.
	 */
	protected Map<Piece,Color> colorPieces;

	
	protected int _CELL_HEIGHT = 100;
	protected int _CELL_WIDTH = 100;
	
	public enum Tipes{
		PIECE, CELL, OBSTACLE
	}
	
	
	public RectBoardComponent(final Observable<GameObserver> game, Board board){
		this.board = board;
		initGUI();
	/*	
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				game.addObserver(RectBoardComponent.this);
			}
		});
		*/
		game.addObserver(RectBoardComponent.this);
	}
	
	
	
	protected void initGUI() {
		
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Mouse Released: " + "(" + e.getX() + "," + e.getY() + ")");
				int row = (e.getX()/_CELL_WIDTH);
				int col = (e.getY()/_CELL_HEIGHT);
				RectBoardComponent.this.mouseReleased(row, col, e.getClickCount(), e.getButton());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse Pressed: " + "(" + e.getX() + "," + e.getY() + ")");
				int row = (e.getX()/_CELL_WIDTH);
				int col = (e.getY()/_CELL_HEIGHT);
				RectBoardComponent.this.mousePressed(row, col, e.getClickCount(), e.getButton());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("Mouse Exited Component: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("Mouse Entered Component: " + "(" + e.getX() + "," + e.getY() + ")");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(
						"Mouse Button " + e.getButton() + " Clicked at " + "(" + e.getX() + "," + e.getY() + ")");
				int row = (e.getX()/_CELL_WIDTH);
				int col = (e.getY()/_CELL_HEIGHT);
				RectBoardComponent.this.mouseClicked(row, col, e.getClickCount(), e.getButton());
			}
		});
		this.setSize(new Dimension(board.getRows() * _CELL_HEIGHT, board.getCols() * _CELL_WIDTH));
		repaint();
	}
	
	protected abstract void mousePressed(int row, int col, int clickCount, int mouseButton);
	protected abstract void mouseReleased(int row, int col, int clickCount, int mouseButton);
	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);
	protected abstract Color getPieceColor(Piece piece);
	protected abstract Tipes getPieceTipe(Piece p);



	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fillBoard(g);
	}

	private void fillBoard(Graphics g) {
		int x, y;
		this._CELL_WIDTH  = this.getWidth()  / board.getCols();
		this._CELL_HEIGHT = this.getHeight() / board.getRows();
	  
		for (int i = 0; i < board.getRows(); i++){
			for (int j = 0; j < board.getCols(); j++) {
				
				x = i * _CELL_WIDTH;
				y = j * _CELL_HEIGHT;
				
				drawCell(x,y,g);

				switch(getPieceTipe(board.getPosition(i, j))){
					case PIECE:
						g.setColor(getPieceColor(board.getPosition(i, j)));
						drawPiece(x,y,g);
						break;
					case OBSTACLE:
						drawObtacle(x,y,g);
						break;
					default:
						drawCell(x,y,g);
						break;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param g
	 */
	protected void drawCell(int x, int y, Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);
		
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param g
	 */
	protected void drawPiece(int x, int y, Graphics g){
		g.setPaintMode();
		g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		g.setColor(Color.black);
		g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param g
	 */
	protected void drawObtacle(int x, int y, Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
	}

	
	
	
	
	
	
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		repaint();
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		this.board = board;
		repaint();
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		this.board = board;
		repaint();
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		this.board = board;
		repaint();
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		this.board = board;
		repaint();
	}

	@Override
	public void onError(String msg) {
	}	
	
}
