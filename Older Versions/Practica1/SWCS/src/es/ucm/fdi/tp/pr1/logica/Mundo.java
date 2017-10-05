package es.ucm.fdi.tp.pr1.logica;

import java.util.Random;

public class Mundo {
	private Superficie superficie;
	
	private static final int NUM_FILAS = 3;
	private static final int NUM_COLUMNAS = 4;

/**
 * Constructor generico de la clase Mundo que inicializa un mundo con una superficie dada a partir de dos constantes
 */
	public Mundo(){
		this.superficie = new Superficie(NUM_FILAS, NUM_COLUMNAS);
	}
/**
 * Constructor de la clase Mundo que inicializa un mundo con una superficie dada a partir de tres valores (filas y columnas)
 * y genera una superficie en base a esos parametros.
 * @param f
 * @param c
 */
	public Mundo(int f, int c){
		this.superficie = new Superficie(f,c);
	}

/**
 * Metodo de consulta del valor de longitud de filas de la superficie del mundo.
 * @return valor de filas asiganadas al atributo del objeto superficie del mundo
 */
	public int filasMundo(){
		return this.superficie.getFilas();
	}

/**
 * Metodo de consulta del valor de longitud de columnas de la superficie del mundo.
 * @return valor de columnas asiganadas al atributo del objeto superficie del mundo
 */
	public int columnasMundo(){
		return this.superficie.getColumnas();
	}
	
/**
 * 
 * @param f representa el valor entero positivo del numero de fila
 * @param c representa el valor entero positivo del numero de columna
 * @return TRUE si el se pudo generar una celula en una celda vacia.FALSE para el resto de casos.
 */
	public boolean crearCelula(int f, int c){
		return this.superficie.llenarCelda(f, c, new Celula());
	}
	
/**
 * 
 * @param f representa el valor entero positivo del numero de fila
 * @param c representa el valor entero positivo del numero de columna
 * @return TRUE si se pudo vaciar la celda habiendo en la misma una celula
 * FALSE para el resto de casos.
 */
	public boolean eliminarCelula(int f, int c){
		return this.superficie.vaciarCelda(f, c);
	}

/**
 * Procedimiento encargado de poner a null todas las celdas de la superficie del mundo
 */
	public void vaciarMundo(){
		this.superficie.reset();
	}

/**
* Genera un random a modulo n;
* @param n es un valor entero positivo
* @return un valor entero positivo aleatorio a modulo con el parametro n.
*/
	private int generarPosicion(int n){
		Random aleatorio = new Random();
		return aleatorio.nextInt(n);
	}
			
/**
* Metodo que genera una celula en una posicion aleatoria libre en la superficie.
* Se generan posiciones aleatorias hasta que se pueda encontrar una celda libre o se alcance
* el numero maximo de celdas en la superficie.
* @param  @param n es el numero entero positivo de celulas que se deben generarse en posiciones aleatorias libres de la superficie.
* @return TRUE si se pudo generar la celula. FALSE si no habia sitio para generarla.
*/
	public void generarCelulasAleatorias(int n){

		int i = 0;
		do{
			int f = generarPosicion(this.filasMundo());
			int c = generarPosicion(this.columnasMundo());
				if(this.crearCelula(f,c))
					i++;
			}while(i < n);
	}

/**
 * Metodo para representar en filas por columnas las celdas y las celulas existentes en ellas
 * @return String con la informacion de el mundo.
 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mundo de tamaño ");
		builder.append(this.filasMundo());
		builder.append(" X ");
		builder.append(this.columnasMundo());
		builder.append('\n');
		builder.append("Numero de celulas: ");
		builder.append(this.superficie.getCelulas());
		builder.append('\n');
		builder.append(this.superficie.toString());
		return builder.toString();
	}


/**
 * Metodo a la superficie
 * Método para gestionar la evolucion en un "paso" en el mundo. para ello, se moveran todas las celulas
 * del tablero UNA SOLA VEZ POR CELULA, y se les aplicara las correspondientes reglas de muerte o
 * reproduccion en funcion de lo que le corresponda a cada una. 
 */
	public void evoluciona(){

		int ocupadas = this.superficie.getCelulas();
		Casilla[] celdasOcupadas = new Casilla[ocupadas];
		/*
		 * Generamos el array de celdas que debemos mover
		 */
		llenarCeldasOcupadas(celdasOcupadas);
		/*
		 * Consultamos celula a celula (recorrido lineal) las posiciones del array y la
		 * correspondiente accion a aplicarle. En primer lugar tratara de moverse y dependiendo
		 * de su correspondiente situacion le tocara moverse, moverse y reproducirse o morir.
		 */
		for(int i = 0; i < ocupadas; i++)
			paso(celdasOcupadas[i]);
	}
	
	
/**
 * Procedimiento para llenar el contenido del array con las casillas que ocupan la superficie.
 * @param celdasOcupadas es el array de objetos tipo Casilla vacio
 */
	private void llenarCeldasOcupadas(Casilla[] celdasOcupadas){
		int cont = 0;
		for(int f = 0; f < this.filasMundo(); f++){
			for(int c = 0; c < this.columnasMundo(); c++){
				if(!this.superficie.verificarCeldaVacia(f,c)){
					celdasOcupadas[cont]= new Casilla(f,c);
					cont++;
				}
			}
		}
	}

	
/**
 * Metodo que desarrolla el paso de una celula en funcion de si ha podido moverse
 * @param casilla es la referencia de la posicion de una celula en la superficie
 */
	private void paso(Casilla casilla){
		/*
		 * Almacenamos los valores de fila y columna para los casos de reproduccion
		 */
		int f = casilla.getFila();
		int c = casilla.getColumna();
		
		/*
		 * Movemos la celula y solo en los casos que no podamos moverla, trataremos el
		 * decremento de sus pasos hasta la muerte, y la aplicion de la muerte si agoto los pasos correspondientes
		 */
		if(moverCasilla(casilla)){
			/*
			 * Si los valores de casilla cambian, debemos reflejar ese cambio en la superficie y aplicar las reglas de reproduccion
			 */
			this.superficie.moverCelula(f, c, casilla.getFila(), casilla.getColumna());
				System.out.println("Movimiento de ("+ f + "," + c + ") a (" + casilla.getFila() + "," + casilla.getColumna()+ ")" );
				if(!this.superficie.decrementarPasosReproduccion(casilla.getFila(), casilla.getColumna())){
					this.crearCelula(f, c);
					System.out.println("Nace nueva celula en  ("+ f + "," + c + ") cuyo padre ha sido (" + casilla.getFila() + "," + casilla.getColumna()+ ")" );
				}
		}
		else{
			if(this.superficie.pasosReproduccionAgotados(f, c)){
				this.eliminarCelula(f, c);
				System.out.println("Muere la celula en la casilla ("+ f + "," + c + ") por no poder reproducirse");				
			}
			else if(!this.superficie.decrementarPasosSinMover(f, c)){
					this.eliminarCelula(f, c);
					System.out.println("Muere la celula en la casilla ("+ f + "," + c + ") por inactividad");
			}
		}
	}

/**
 * Metodo que recie una dupla de valores enteros (x,y) y selecciona una de las posibles casillas libres circundantes.
 * @param casilla con valores enteros positivos y acotados dentro del rango de la longitud de la superficie.
 * @return TRUE si se cambio alguno de los parametros de la casilla, FALSE en caso contrario.
 */
	private  boolean moverCasilla(Casilla casilla){
		boolean cambio = false;
		Casilla[] casillasLibres = new Casilla[9];
		int libresEncontradas = 0;

		for(int f = Math.max(casilla.getFila()-1, 0); f <= Math.min(casilla.getFila() + 1, this.filasMundo() - 1); f++)
			for (int c = Math.max(casilla.getColumna() - 1, 0); c <= Math.min(casilla.getColumna() + 1, this.columnasMundo() - 1); c++)
				if(this.superficie.verificarCeldaVacia(f,c)){
					casillasLibres[libresEncontradas] = new Casilla(f,c);
					libresEncontradas++;
				}
		if(libresEncontradas > 0){
		casilla.cambiarCasilla(casillasLibres[(int) (Math.random()* libresEncontradas)]);
		
		cambio = true;
		}
		return cambio;
	}
//Fin de la clase
}