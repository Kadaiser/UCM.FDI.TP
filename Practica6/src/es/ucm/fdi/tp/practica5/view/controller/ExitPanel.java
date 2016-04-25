package es.ucm.fdi.tp.practica5.view.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.practica5.view.sound.MakeSound;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;


public class ExitPanel extends JPanel implements GameObserver{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton quit;
	JButton restart;
	
	public ExitPanel(Color color, String label, Controller controller, Observable<GameObserver> game) {
		this.setBackground(color);
		TitledBorder labeledBoder = BorderFactory.createTitledBorder(label);
		this.setBorder(labeledBoder);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		initGUI(controller);
		game.addObserver(this);
	}

	protected void initGUI(Controller controller) {
		
		this.quit = new JButton("Quit");
		this.add(quit);
		quit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MakeSound.RunPlaySound("sound/exitgame.wav");
				int n = JOptionPane.showOptionDialog(new JFrame(),
						"Are sure you want to quit?", "Close Game",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
						null, null);

				if (n == 0) {
					controller.stop();
					System.exit(0);
				}
			}
			
		});
		
		this.restart = new JButton("Restart");
		this.add(restart);
		restart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(),
						"Are sure you want to restart the game?", "Resest Game",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
						null, null);

				if (n == 0) {
					controller.restart();
				}
			}
			
		});
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
