package es.ucm.fdi.tp.pr1.logica;

public class Casilla {
	private int fila;
	private int columna;
	
	/**
	 * @param fila
	 * @param columna
	 */
	public Casilla(int fila, int columna) {
		super();
		this.fila = fila;
		this.columna = columna;
	}
	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}

	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
	/**
	 * @param fila the fila to set
	 */
	private void setFila(int fila) {
		this.fila = fila;
	}
	/**
	 * @param columna the columna to set
	 */
	private void setColumna(int columna) {
		this.columna = columna;
	}
	
	/**
	 * Procedimiento que recibe por parametro una casilla, de la cual se tomaran los valores de sus atributos para
	 * sustiturlos en los atributos de la casilla que llame al metodo.
	 * @param nuevaCasilla objeto tipo Casilla con atributos definidos.
	 */
	public void cambiarCasilla(Casilla nuevaCasilla){
		this.setFila(nuevaCasilla.getFila());
		this.setColumna(nuevaCasilla.getColumna());
	}
	
//Fin de la clase
}