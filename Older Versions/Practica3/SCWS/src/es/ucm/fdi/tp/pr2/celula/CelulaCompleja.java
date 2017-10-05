package es.ucm.fdi.tp.pr2.celula;


import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.logica.Casilla;
import es.ucm.fdi.tp.pr2.logica.Superficie;

public class CelulaCompleja implements Celula {

	private int celulasComidas;
	
	private static final int MAX_COMER = 5;

/**
 * Constructor con parametros de una celula Compleja, que define el atributo mediante parametro
 * @param celulasComidas valor de celulas restantes posibles para comer antes de explotar
 */
	public CelulaCompleja(int celulasComidas){
		this.celulasComidas = celulasComidas;
	}
	
/**
 * Constructor generico de la clase CelulaCompleja, instancia por defecto un valor de celulasComidas = 0
 */
	public CelulaCompleja(){
		this.celulasComidas = 0;
	}
	
/**
 * El metodo incrementa el valor del atributo "celulasComidas" siempre que no se alcance el valor maximo de MAX_COMER celulas.
 * @return TRUE si el valor de celulas comidas pudo incrementarse  FALSE si el valor ya esta en su maximo posible
 */
	public boolean incrementarCelulasComidas(){
		boolean incremento = false;
		if(this.celulasComidas < MAX_COMER){
			this.celulasComidas++;
			incremento = true;
		}
		return incremento;
	}

@Override
	public Casilla ejecutaMovimiento(int fila, int columna, Superficie superficie, StringBuilder informe) {
	String respuesta = "";
	Casilla casilla = new Casilla(fila,columna);

	
			if(moverCasilla(casilla, superficie)){
					respuesta = "Movimiento de CelulaCompleja en (" +  fila + "," + columna + ") a (" + casilla.getFila() + "," + casilla.getColumna() + ")";
				
				if(!superficie.verificarCeldaVacia(casilla.getFila(), casilla.getColumna())){
						superficie.vaciarCelda(casilla.getFila(), casilla.getColumna());
						superficie.moverCelula(fila, columna, casilla.getFila(), casilla.getColumna());
						respuesta = "CelulaCompleja de (" +  fila + "," + columna + ") devora a celula de (" + casilla.getFila() + "," + casilla.getColumna() + ")";
						
					if(!this.incrementarCelulasComidas()){
						superficie.vaciarCelda(casilla.getFila(), casilla.getColumna());
						respuesta += " y explota por comer demasidas celulas";
					}
				}
				else
					superficie.moverCelula(fila, columna, casilla.getFila(), casilla.getColumna());
			}
			else{
				casilla = null;
				respuesta = "CelulaCompleja en la posicion (" +  fila + "," + columna + ") no puede moverse por falta de espacio";
			}
			
	informe.append(respuesta + System.getProperty("line.separator"));
	return casilla;
	}

/**
 * El metodo genera una nueva posicion aleatoria de casilla para una celulaCompleja. Las celulas complejas pueden desplzarse a posiciones vacias u ocupadas por celulas cuyo
 * respuesta al metodo esComestible sea TRUE.
 * @param casilla que se va a mover
 * @param superficie donde se va a desplzar la celula
 * @return TRUE si el valor de casilla se modifico FALSE para el caso contrario
 */
	private  boolean moverCasilla(Casilla casilla, Superficie superficie){
		
		boolean cambio = false;
		Casilla[] casillasLibres = new Casilla[superficie.getFilas()*superficie.getColumnas() - 1];
		int libresEncontradas = 0;
		
		for(int fila = 0; fila < superficie.getFilas(); fila++)
			for(int columna = 0; columna < superficie.getColumnas(); columna++)
				if(superficie.verificarCeldaVacia(fila,columna) || superficie.esComestible(fila,columna)){
					casillasLibres[libresEncontradas] = new Casilla(fila,columna);
					libresEncontradas++;
				}
		if(libresEncontradas > 0){
		casilla.cambiarCasilla(casillasLibres[(int) (Math.random()* libresEncontradas)]);
		cambio = true;
		}
		return cambio;			
	}

	
@Override
	public String mostrarCelula() {
		return ":" + celulasComidas + ":";
	}
	
@Override
	public boolean esComestible() {
		return false;
	}

@Override
	public String guardar() {
		return ("compleja " + this.celulasComidas);		
	}

@Override
	public Celula parsea(String datos) {
		Celula celula;
		if(datos.equalsIgnoreCase("2")){
			celula = new CelulaCompleja();
		}
		else
			celula = null;
		return celula;	
	}	

@Override
	public Celula cargar(String[] datos) throws ErrorFormatoNumerico {
		Celula celula = null;
		if(datos[2].equalsIgnoreCase("compleja" )&& datos.length== 4){
			try{
				int CC = Integer.parseInt(datos[3]);
				celula = new CelulaCompleja(CC);
			}catch(NumberFormatException e){
				throw new ErrorFormatoNumerico(" se encontro un atributo en formato no numerico");	
			}
		}
		return celula;
	}
	
}
