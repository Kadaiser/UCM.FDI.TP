package es.ucm.fdi.tp.pr2.celula;

import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.logica.Casilla;
import es.ucm.fdi.tp.pr2.logica.Superficie;

public class CelulaSimple implements Celula {
	
	private int pasosReproduccion;
	private int pasosSinMover;
	
	private static final int PASOS_REPRODUCCION = 2;
	private static final int MAX_PASOS_SIN_MOVER = 1;

	
/**
 * Constructor con parametros de la clase Celulasimple, los atributos seran definidos manualmente en los parametros
 * @param pasosReproduccion valor de pasos hasta la reproduccion
 * @param pasosSinMover valor de pasos restantes sin mover hasta morir
 */
	public CelulaSimple(int pasosReproduccion, int pasosSinMover){
		this.pasosReproduccion = pasosReproduccion;
		this.pasosSinMover = pasosSinMover;
	}
	
/**
 * Constructor generico de la clase CelulaSimple, los atributos finales de la clase definen los valores de los atributos para
 * el objeto generado.
 */
	public CelulaSimple(){
		this.pasosReproduccion = PASOS_REPRODUCCION;
		this.pasosSinMover = MAX_PASOS_SIN_MOVER;
	}
	
/**
 * Decrementa el atributo de pasosSinMover de una celula en 1 hasta el valor 0. No decrementa si el valor ya es 0.
 * @return TRUE si la celula pudo decrementar el numero de pasos sin mover.
 *  FALSE si el valor ya era 0.
 */
	private boolean decrementarPasosSinMover(){
		boolean decremento = false;
		if(this.pasosSinMover > 0){
			this.pasosSinMover--;
			decremento = true;
		}
		return decremento;
	}
		
/**
 * @return atributo pasosSinMover del objeto
 */
	public int getPasosSinMover() {
		return pasosSinMover;
	}

/**
 * Incrementa el valor del atributo pasosReproduccion de una celula en 1;
 * cuando alcanza el valor PASOS_REPRODUCCION el valor de pasosReproduccion se reinicia a PASOS_REPRODUCCION
 * @return TRUE si la celula pudo decrementar el numero de pasos hasta reproducirse sin mover.
 *  FALSE si el valor ya era 0.
 */
	private boolean decrementarPasosReproduccion(){
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
	
@Override
	public Casilla ejecutaMovimiento(int fila, int columna, Superficie superficie, StringBuilder informe){
			String respuesta = "";
			Casilla casilla = new Casilla(fila,columna);
			
			if(moverCasilla(casilla, superficie)){

				superficie.moverCelula(fila, columna, casilla.getFila(), casilla.getColumna());
					respuesta = "Movimiento de CelulaSimple de ("+ fila + "," + columna + ") a (" + casilla.getFila() + "," + casilla.getColumna()+ ")";
					if(!this.decrementarPasosReproduccion()){
						superficie.llenarCelda(fila, columna, new CelulaSimple());
						respuesta +=" reproduciendo una nueva celula en ("+ fila + "," + columna + ")";
					}
			}
			
			else{
				casilla = null;
				
				if(this.pasosReproduccionAgotados()){
					superficie.vaciarCelda(fila, columna);
					respuesta = "Muere la CelulaSimple en la casilla ("+ fila + "," + columna + ") por no poder reproducirse";				
				}
				else if(!this.decrementarPasosSinMover()){
						superficie.vaciarCelda(fila, columna);
						respuesta = "Muere la CelulaSimple en la casilla ("+ fila + "," + columna + ") por inactividad";
				}
				else
					respuesta = "CelulaSimple en la casilla ("+ fila + "," + columna + ") no puede moverse";
			}
			informe.append(respuesta + System.getProperty("line.separator"));
			return casilla;
		}

	/**
 * Metodo que recie una dupla de valores enteros (x,y) y selecciona una de las posibles casillas libres circundantes.
 * @param casilla con valores enteros positivos y acotados dentro del rango de la longitud de la superficie.
 * @return TRUE si se cambio alguno de los parametros de la casilla, FALSE en caso contrario.
 */
	private  boolean moverCasilla(Casilla casilla, Superficie superficie){
		boolean cambio = false;
		Casilla[] casillasLibres = new Casilla[8];
		int libresEncontradas = 0;

		for(int f = Math.max(casilla.getFila()-1, 0); f <= Math.min(casilla.getFila() + 1, superficie.getFilas() - 1); f++)
			for (int c = Math.max(casilla.getColumna() - 1, 0); c <= Math.min(casilla.getColumna() + 1, superficie.getColumnas() - 1); c++)
				if(superficie.verificarCeldaVacia(f,c)){
					casillasLibres[libresEncontradas] = new Casilla(f,c);
					libresEncontradas++;
				}
		if(libresEncontradas > 0){
		casilla.cambiarCasilla(casillasLibres[(int) (Math.random()* libresEncontradas)]);
		cambio = true;
		}
		return cambio;
	}


/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
	public String mostrarCelula() {
		return this.pasosSinMover + "-" + this.pasosReproduccion;
	}

@Override
	public boolean esComestible() {
		// TODO Auto-generated method stub
		return true;
	}

@Override
	public String guardar() {	
		return ("simple " + this.pasosReproduccion + " " + this.pasosSinMover);
	}

@Override
	public Celula parsea(String datos) {
		Celula celula;
		if(datos.equalsIgnoreCase("1")){
			celula = new CelulaSimple();
		}
		else
			celula = null;
		return celula;	
	}

@Override
	public Celula cargar(String[] datos) throws ErrorFormatoNumerico {
		Celula celula = null;
		if(datos[2].equalsIgnoreCase("simple") && datos.length== 5){
			try{
				int PSM = Integer.parseInt(datos[3]);
				int PR = Integer.parseInt(datos[4]);
				celula = new CelulaSimple(PSM,PR);
			}catch(NumberFormatException e){
				throw new ErrorFormatoNumerico(" se encontro un atributo de la celula en formato no numerico");	
			}
		}
		return celula;
	}


}
