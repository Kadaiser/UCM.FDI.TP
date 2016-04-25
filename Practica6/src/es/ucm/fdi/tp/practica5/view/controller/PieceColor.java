package es.ucm.fdi.tp.practica5.view.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.view.color.ColorChooser;


public class PieceColor extends JPanel implements GameObserver {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton selectColor;

	public PieceColor(Color color, String label, Map<Piece, Color> pieceColors, Observable<GameObserver> game, JComboBox<Piece> pieceColorCombo) {
		this.setBackground(color);
		this.setBorder(BorderFactory.createTitledBorder(label));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(pieceColorCombo);

		this.selectColor = new JButton("SelectColor");
		this.add(this.selectColor);
		selectColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//MakeSound.RunLoopPlaySound("sound/choosecolor.wav", e);
				try {
					ColorChooser c = new ColorChooser(new JFrame(), "Choose Line Color", null);
					if (c.getColor() != null) {
						pieceColors.put((Piece) pieceColorCombo.getSelectedItem(), c.getColor());
						repaint();
					}
				} catch (Exception _e) {}
			}
		});
		game.addObserver(this);
	}




@Override
public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
}

@Override
public void onGameOver(Board board, State state, Piece winner) {
}

@Override
public void onMoveStart(Board board, Piece turn) {
	// TODO Auto-generated method stub
	
}

@Override
public void onMoveEnd(Board board, Piece turn, boolean success) {

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
