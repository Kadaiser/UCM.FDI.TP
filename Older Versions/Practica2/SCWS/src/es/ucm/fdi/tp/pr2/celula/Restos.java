package es.ucm.fdi.tp.pr2.celula;

import es.ucm.fdi.tp.pr2.logica.Casilla;
import es.ucm.fdi.tp.pr2.logica.Superficie;

public class Restos implements Celula {
	private int turnos;
	
	private static final int TURNOS = 5;
	
	/**
	 * Constructor generico de la clase Restos, instancia por defecto un valor de turnos 0 = 0
	 */
		public Restos(){
			this.turnos = 0;
		}
/**
 * El metodo incrementa el valor del atributo "contador" siempre que no se alcance el valor maximo de MAX_COMER celulas.
 * @return TRUE si el valor de celulas comidas pudo incrementarse  FALSE si el valor ya esta en su maximo posible
 */
	public boolean incrementarContador(){
		boolean incremento = false;
		if(this.turnos < TURNOS){
			this.turnos++;
			incremento = true;	
		}
		return incremento;
	}

	@Override
	public Casilla ejecutaMovimiento(int fila, int columna, Superficie superficie, StringBuilder informe) {
		String respuesta = "";
		if(!this.incrementarContador()){
			superficie.vaciarCelda(fila, columna);
			respuesta = "Los restos se han degradado dejando la posicion (" + fila + "," + columna + ") libre en la superficie" + System.getProperty("line.separator");
		}
		informe.append(respuesta);
		return null;
	}

	@Override
	public boolean esComestible() {
		return false;
	}
	@Override
	public String mostrarCelula() {
		return "   ";
	}

}
