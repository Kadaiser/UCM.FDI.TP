package es.ucm.fdi.tp.pr1.logica;

public class Celula {
	private int pasosSinMover;
	private int pasosReproduccion;
	
	private static final int MAX_PASOS_SIN_MOVER = 1;
	private static final int PASOS_REPRODUCCION = 2;

	
/**
 * Constructor generico de la clase Celula, inicializa los valores de los atributos del objeto.
 */
	public Celula(){
		this.pasosSinMover = MAX_PASOS_SIN_MOVER;
		this.pasosReproduccion = PASOS_REPRODUCCION;
	}
/**
 * Constructor con atributos de la clase Celula, inicializa los valores de los atributos del objeto segun los parametros	
 * @param pasosSinMover
 * @param pasosReproduccion
 */
	public Celula(int pasosSinMover, int pasosReproduccion){
		this.pasosSinMover = pasosSinMover;
		this.pasosReproduccion = pasosReproduccion;
	}
	
/**
 * Metodo que decrementa el atributo de pasosSinMover de una celula hasta el valor entero 0. 
 * No decrementa si el valor ya es 0.
 * @return TRUE si la celula pudo decrementar el numero de pasos sin mover.
 *  FALSE si el valor ya era 0.
 */
	public boolean decrementarPasosSinMover(){
		boolean decremento = false;
		if(this.pasosSinMover > 0){
			this.pasosSinMover--;
			decremento = true;
		}
		return decremento;
	}
	
/**
 * Funcion que incrementa el valor del atributo pasosReproduccion de una celula en 1;
 * cuando alcanza el valor PASOS_REPRODUCCION el valor de pasosReproduccion se reinicia a PASOS_REPRODUCCION
 * @return TRUE si la celula pudo decrementar el numero de pasos hasta reproducirse sin mover.
 *  FALSE si el valor ya era 0.
 */
	public boolean decrementarPasosReproduccion(){
		boolean decremento = false;
		if(this.pasosReproduccion > 0){
			this.pasosReproduccion--;
			decremento = true;
		}
		else{
			this.pasosReproduccion = PASOS_REPRODUCCION;
		}
		return decremento;
	}
/**
 * Metodo que consulta si el valor del atributo pasosReproduccion se encunentra a 0
 * @return TRUE si el valor del atributo es cero, FALSE para el resto de casos.
 */
	public boolean pasosReproduccionAgotados(){
		return this.pasosReproduccion == 0;
	}


/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return pasosSinMover + "-" + pasosReproduccion;
}
		
//Fin de la clase
}
