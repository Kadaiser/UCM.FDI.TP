package es.ucm.fdi.tp.practica5.view.controller;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AutomaticMove extends JPanel implements GameObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton random;
	JButton intelligent;

	public AutomaticMove(Color color, String label, Controller controller, Observable<GameObserver> game) {
		this.setBackground(color);
		TitledBorder labeledBoder = BorderFactory.createTitledBorder(label);
		this.setBorder(labeledBoder);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		initGUI(controller);
		game.addObserver(this);
	}
	
	protected void initGUI(Controller controller) {
		this.random = new JButton("Random");
		this.intelligent = new JButton("Intelligent");
		this.add(random);
		this.add(intelligent);
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}

}
