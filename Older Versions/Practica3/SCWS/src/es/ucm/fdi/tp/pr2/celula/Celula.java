package es.ucm.fdi.tp.pr2.celula;

import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
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
	
/**
 * Se procesa una celula para ser almacenada en un fichero de texto. para ello se guarda la celula con el siguiente formato:
 * fila columna tipo_celula paramtero[1] parametro[2] ... 
 * @return salida es el flujo de caracteres que se insertara en el fichero
 */
	public abstract String guardar();
	
/**
 * Se procesa informacion de un fchero para construir una celula con la posicion y valores de atributos definidos por el flujo.
 * @param datos es un array de strings que se procesara para construir una celula
 * @return Celula instanciada segun el parametro de entrada
 * @throws ErrorFormatoNumerico si alguno de los atributos necesarios para instanciar la celula en los parametros de entrada no fuesen  parseables a un formato no numérico (NumberFormatException)
 */
	public abstract Celula cargar (String[] datos) throws ErrorFormatoNumerico;

/**
 * Reconoce la clase derivada que instancia un String coincidente con la celula deseada. 	
 * @param datos es el array de String que contine toda la informacion de una celula y sus atributos
 * @return una celula acorde a la definicion, construida con los atributos entregados
 */
	public Celula parsea(String datos);
}


