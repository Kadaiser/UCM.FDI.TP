package es.ucm.fdi.tp.pr2.logica;

public class Casilla {
	private int fila;
	private int columna;
	
/**
 * @param fila valor entero positivo representativo de la fila en la que se encuentra una posicion de la superficie
 * @param columna valor entero positivo representativo de la columna en la que se encuentra una posicion de la superficie
 */
	public Casilla(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}
/**
 * @return atributo de fila de la casilla
 */
	public int getFila() {
		return fila;
	}

/**
 * @return atributo de fila de la casilla
 */
	public int getColumna() {
		return columna;
	}
/**
 * @param atributo fila to set
 */
	private void setFila(int fila) {
		this.fila = fila;
	}
/**
 * @param atributo columna to set
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

/**
 * Se comprueban que los atributos de dos casillas son identicas
 * @param casilla contra la que se quiere comprobar la igualdad
 * @return TRUE si las casillas poseen el mismo valor para los atributos
 * @return FALSE si no se cumplen todas las coincidencias o el parametro es null;
 */
	public boolean casillaIgual(Casilla casilla){
		boolean igual = false;
		if(casilla != null)
			igual = (this.getFila() == casilla.getFila()) && (this.getColumna() == casilla.getColumna());
		return igual;
	}
	
//Fin de la clase
}