package es.ucm.fdi.tp.practica5.view.controller;


import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class StatusMessages extends JPanel implements GameObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JScrollPane scroll;
	private JTextArea textArea;
	
	
	public StatusMessages(Color color, String label, Observable<GameObserver> game){
		this.setBackground(color);
		this.setBorder(BorderFactory.createTitledBorder(label));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		initGUI();
		game.addObserver(this);
	}
	
	protected void initGUI() {

		this.textArea = new JTextArea(5,30);
		this.textArea.setEnabled(false);
		this.textArea.setEditable(true);
		this.textArea.setLineWrap(false);
		
		this.scroll = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(this.scroll);
	}
	
	public void setMessage(String messeage){
		this.textArea.setText(messeage);
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		setMessage("Game Started!");	
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		setMessage("Game Over!");
		if(State.Draw.equals(state)){
			setMessage("It´s a draw!");
		}
		else if(state.equals(State.Won)){
			setMessage(winner.toString() + " is the winner!");
		}
		
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	
}
