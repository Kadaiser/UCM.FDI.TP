package es.ucm.fdi.tp.pr2.celula;

import es.ucm.fdi.tp.pr2.logica.Casilla;
import es.ucm.fdi.tp.pr2.logica.Superficie;

public interface Celula {

/**
 * El metodo realiza el movimiento de una celula en base a la implementacion dada para la clase derivada que le corresponde
 * al objeto en la superficie.
 * @param fila valor entero que define la posicion de fila de la celula
 * @param columna valor entero que define la posicion de columna de la celula
 * @param superficie en la que se va a mover la celula
 * @param informe contiene una concatenacion de string explicando la evolucion de la celula en la ejecucion del metodo
 * @return casilla instanciada con la nueva posicion a la que se desplaza una celula tras el movimiento. valor null si la celula no se desplaza.
 */
	public abstract Casilla ejecutaMovimiento(int fila, int columna, Superficie superficie, StringBuilder informe);
	
/**
 * Consulta el valor definido de una celula para ser devorada
 * @return la condicion definida para la clase derivada
 */
	public abstract boolean esComestible();
	
/**
 * Se muestra en formato string los valores de los parametros de la celula
 * IMPORTANTE: La longitud del String representativo de la celula DEBE ser de 3 caracteres
 * @return estado de la celula
 */
	public abstract String mostrarCelula();
	

}
