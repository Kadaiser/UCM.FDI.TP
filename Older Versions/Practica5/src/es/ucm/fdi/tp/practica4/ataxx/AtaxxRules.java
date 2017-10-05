package es.ucm.fdi.tp.practica4.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;



/**
 * Rules for Ataxx game.
 * <ul>
 * <li>The game is played on an NxN board (with N>=5 and odd).</li>
 * <li>The number of players is between 2 and 4.</li>
 * <li>The player turn in the given order, each placing two pieces on the
 * symmetrical corners of the boards (2 players) and on the symmetrical 
 * middles of the borders(4 players). The winner is the one who have 
 * the biggest number of pieces of their own color whenever the board is full or
 * anyone cannot move.</li>
 * </ul>
 * 
 * <p>
 * Reglas del juego Ataxx.
 * <ul>
 * <li>El juego se juega en un tablero NxN (con N>=5 e impar).</li>
 * <li>El numero de jugadores esta entre 2 y 4.</li>
 * <li>Los jugadores juegan en el orden proporcionado, cada uno colocando dos
 * ficha en las esquinas simetricas del tablero(2 jugadores) y las medianas 
 * del borde del tablero(4 jugadres). El ganador es el jugador que consiga 
 * el mayor numero de fichas de su color cuando el tablero este lleno 
 * o ningun jugador pueda moverse.</li>
 * </ul>
 *
 */
public class AtaxxRules implements GameRules {
	
	private int dimension;
	private int obstacles;
	private final Piece obstacle = new Piece("+");
	
	
	
	/**
	 * This constructor should be used ONLY to get an instance of
	 * {@link AtaxxFactory} to generate a game play from the 
	 * attributes in the command line
	 * 
	 * <p>
	 * Solo se debe usar este constructor para obtener objetos de
	 * {@link AtaxxFactory} para generar juegos a partir de los parametros
	 * entregados en los argumentos.
	 * 
	 * @param dimension paramtro de la longitud del tablero
	 * @param obstacles parametro con el numero de obstaculos a generar en el tablero
	 */

	public AtaxxRules(int dimension, int obstacles){
		if(dimension < 5){
			throw new GameError("Dimension must be at least 5 :" + dimension);
		}
		else{
			if(obstacles > (dimension * dimension)- 8){
				throw new GameError("Obstacles must be less than " + dimension * dimension);
			}
			else{
				this.dimension = dimension;
				this.obstacles = obstacles;
			}
		}
	}
	/**
	 * This constructor should be used ONLY to get an instance of
	 * {@link AtaxxFactory} to generate a game play from the 
	 * attributes in the command line
	 * 
	 * <p>
	 * Solo se debe usar este constructor para obtener objetos de
	 * {@link AtaxxFactory} para generar juegos a partir de los parametros
	 * entregados en los argumentos.
	 * 
	 * @param dimension paramtro de la longitud del tablero
	 */
	public AtaxxRules(int dimension){
		if(dimension < 5){
			throw new GameError("Dimension must be at least 5 :" + dimension);
		}
		this.dimension = dimension;
		this.obstacles = 0;
	}

	
	@Override
	public String gameDesc() {
		return "Ataxx " + this.dimension + "X" + this.dimension;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		Board board = new  FiniteRectBoard(this.dimension, this.dimension);
			
		Piece p1 = pieces.get(0);
		board.setPosition(0, 0, p1);
		board.setPosition(symmetrical(0), symmetrical(0), p1);
		board.setPieceCount(p1, 2);
		
		Piece p2 = pieces.get(1);
		board.setPosition(0, this.dimension - 1, p2);
		board.setPosition(symmetrical(0), symmetrical(this.dimension - 1), p2);
		board.setPieceCount(p2, 2);
		
		if(pieces.size() > 2){
			Piece p3 = pieces.get(2);
			board.setPosition(0, this.dimension / 2, p3);
			board.setPosition(symmetrical(0), symmetrical(this.dimension /2), p3);
			board.setPieceCount(p3, 2);
			
			if(pieces.size() > 3){
				Piece p4 = pieces.get(3);
				board.setPosition(this.dimension / 2, 0, p4);
				board.setPosition(symmetrical(this.dimension / 2), symmetrical(0), p4);
				board.setPieceCount(p4, 2);
			}
		}
		if(this.obstacles > 0)
			fillOfObstacles(board);
		
		return board;
	}
	
	/**
	 * Metodo que resuelve el valor simetrico central de un valor dado respecto del tablero.
	 * 
	 * @param origin es una variable de coordenada de una posicion del tablero
	 * @return el valor simetrico central respecto de el centrol del tablero de dimension impar.
	 */
	private int symmetrical(int origin){

		int symmetrical;
		int middle = this.dimension/2;
		if(origin > middle)
			symmetrical = Math.abs((origin-middle)- middle);
		else if(origin < middle)
			symmetrical =(Math.abs(origin-middle) + middle);
		else
			symmetrical = origin;
		return symmetrical;
	}
	/**
	 * Procedimiento que coloca de manera aleatoria los obstaculos definidos en el atributo de 
	 * la clase AttaxRules. La colocacion de obstaculos se realiza de manera simetrica central
	 * respecto de la posicion central para un tablero regular.
	 * 
	 * @param board tablero sobre el que se han de colocar los obstaculos.
	 */
	private void fillOfObstacles(Board board){
		int r, c;
		int counter = this.obstacles;
		if(counter % 2 != 0){
			board.setPosition(this.dimension/2, this.dimension/2, this.obstacle);
			counter--;
		}
		while(counter != 0){
			r = Utils.randomInt(this.dimension);
			c = Utils.randomInt(this.dimension);
			if(board.getPosition(r, c) == null){
				board.setPosition(r, c, this.obstacle);
				board.setPosition(symmetrical(r),symmetrical(c), this.obstacle);
				counter = counter - 2;
			}
		}
	}
	

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {
		return pieces.get(0);
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int maxPlayers() {
		return 4;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> pieces, Piece turn) {
		Pair<State, Piece> result = new Pair<State, Piece>(State.InPlay, null) ;
		
		if(board.isFull()){
			result = returnResult(board, pieces, turn);
		}
		else if(this.nextPlayer(board, pieces, turn).equals(turn)){
			if(this.allPlayersBlocked(board, pieces))
				result = returnResult(board, pieces, turn);
		}
		return result;
	}
	/**
	 * Metodo que determina si todos los jugadores de la partida excepto uno se han quedado sin fichas
	 * 
	 * @param board tablero donde se desarrolla la partida
	 * @param pieces lista de piezas de los jugadores
	 * @return
	 */
	protected boolean allPlayersBlocked(Board board, List<Piece> pieces){
		boolean blocked = false;
		int counter = 0;
		
		for(int i = 0; i < pieces.size(); i++){
			if(this.validMoves(board, pieces, pieces.get(i)).size() <= 0)
				counter++;
		}
		if(counter == pieces.size() - 1)
			blocked = true;
		return blocked;
	}
	
	/**
	 * Resulve el final de una partida indicando el jugador ganador y el numero de piezas
	 * @param board tablero donde se desarrolla la partida
	 * @param pieces lista de tipos de piezas de los jugadores
	 * @param turn pieza a la que le corresponde el turno actual
	 * @return Pair<State, Piece> con el estado final de la aprtida y el jugador correspondiente.
	 */
	private Pair<State, Piece> returnResult(Board board, List<Piece> pieces, Piece turn){
		
		State game=State.InPlay;
		Piece piece = null;
		int higerValue = 0;
		int[] playersScore = new int[pieces.size()];
		
		for(int i = 0; i < pieces.size(); i++){
			playersScore[i] = board.getPieceCount(pieces.get(i));
			if(higerValue == playersScore[i]){
				game = State.Draw;
				
			}
			else if(higerValue < playersScore[i]){
				higerValue = playersScore[i];
				piece = pieces.get(i);
				game = State.Won;
			}
		}
		Pair<State, Piece> result = new Pair<State, Piece>(game, piece);
		
		return result;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> pieces, Piece turn) {
		Piece piece;
		int numPlayers = pieces.size();
		int i = pieces.indexOf(turn);
		int cantidad, counter = 1;

		do{
			piece	= pieces.get((i + counter) % numPlayers);
			cantidad = this.validMoves(board, pieces, piece).size();
			counter++;
		}while(cantidad <= 0 && counter <= numPlayers);

		return piece;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		Piece piece = turn;
		List<GameMove> validMoves = new ArrayList<GameMove>();
		for(int r= 0; r < board.getRows(); r++)
			for(int c = 0; c < board.getCols(); c++){
				if(board.getPosition(r, c) == piece){
					validMoves.addAll(validMoveOfPiece(board, turn, r, c));
				}
			}
		return validMoves;
	}
	
	/**
	 * Establece una lista de posibles movimientos de una pieza en una posicion especifica del tablero
	 * @param board tablero donde se ubica la pieza
	 * @param piece pieza sobre la que se desean consultar los posibles movimientos
	 * @param row fila de la ubicacion de la ficha consultada
	 * @param col columna de la ubicacion de la ficha consultada
	 * @return
	 */
	protected List<GameMove> validMoveOfPiece(Board board, Piece piece, int row, int col){
		
		int rows = board.getRows();
		int cols = board.getCols();
		
		List<GameMove> validMoves = new ArrayList<GameMove>();
		for(int r = Math.max(row - 2, 0); r <= Math.min(row + 2, rows - 1); r++) 
			for (int c = Math.max(col - 2, 0); c <= Math.min(col + 2, cols - 1); c++)
				if(board.getPosition(r, c) == null){
					validMoves.add(new AtaxxMove(row, col,r,c, piece));
				}
		return validMoves;
		
	}
	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
