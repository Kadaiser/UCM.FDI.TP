package es.ucm.fdi.tp.pr2.celula;

import es.ucm.fdi.tp.pr2.logica.Casilla;
import es.ucm.fdi.tp.pr2.logica.Superficie;

public class CelulaSuicida implements Celula {
	
	private int contador;
	
	private static final int CONTADOR = 10;
	private static final int RADIO = 2; //Define la longitud de alcance de la explosion de la celula suicida
	
/**
 * Constructor generico de la clase CelulaSuicida, instancia por defecto un valor de Contador 0 = 0
 */
	public CelulaSuicida(){
		this.contador = 0;
	}
		
	/**
	 * El metodo incrementa el valor del atributo "contador" siempre que no se alcance el valor maximo de MAX_COMER celulas.
	 * @return TRUE si el valor de celulas comidas pudo incrementarse  FALSE si el valor ya esta en su maximo posible
	 */
		public boolean incrementarContador(){
			boolean incremento = false;
			if(this.contador < CONTADOR){
				this.contador++;
				incremento = true;
			}
			return incremento;
		}
	
	@Override
	public Casilla ejecutaMovimiento(int fila, int columna, Superficie superficie, StringBuilder informe) {
		String respuesta = "";
		/* PUEDE IMPLEMENTARSE UNA FORMA DE MOVERSE SI SE DESEA
		 * 
		Casilla casilla = new Casilla(fila,columna);
		if(moverCasilla(casilla, superficie)){
			respuesta = "Movimiento de CelulaSuicida en (" +  fila + "," + columna + ") a (" + casilla.getFila() + "," + casilla.getColumna() + ")";
		*/
		if(!this.incrementarContador()){
			respuesta += "CelulaSuicida en (" + fila + "," + columna + ") ha explotado aniquilando a todas las celulas circundantes en radio " + RADIO + ", los restos hacen que esa posicion sea inaccesible por varios turnos"  + System.getProperty("line.separator");

			for(int f = Math.max(fila - RADIO, 0); f <= Math.min(fila + RADIO, superficie.getFilas() - 1); f++)
				for(int c = Math.max(columna - RADIO, 0); c <= Math.min(columna + RADIO, superficie.getColumnas() - 1); c++){
					if(f == fila && c == columna){
						superficie.vaciarCelda(fila, columna);
						superficie.llenarCelda(fila, columna, new Restos());
					}
					else if(superficie.vaciarCelda(f, c))
						respuesta += "celula en (" + f + "," + c + ") es aniquilada por la explosion suicida!" + System.getProperty("line.separator");
				}
			}
		informe.append(respuesta);
		//return casilla; DE MOMENTO A ESTA CELULA NO SE LE HA IMPLEMENTADO UN MOVIMIENTO
		return null;
	}

	@Override
	public boolean esComestible() {
		return true;
	}

	public String mostrarCelula(){
		return "[" + this.contador + "]";
		
	}
}
