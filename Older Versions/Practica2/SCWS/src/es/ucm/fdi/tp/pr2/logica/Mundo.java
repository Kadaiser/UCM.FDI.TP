package es.ucm.fdi.tp.pr2.logica;


import es.ucm.fdi.tp.pr2.celula.Celula;

public class Mundo {
	private Superficie superficie;
	private boolean SimulacionTerminada = true;
	
	private static final int NUM_FILAS = 5;
	private static final int NUM_COLUMNAS = 5;

/**
 * Constructor generico de la clase Mundo que inicializa un mundo con una superficie dada a partir de dos constantes
 */
	public Mundo(){
		this.superficie = new Superficie(NUM_FILAS, NUM_COLUMNAS);
	}
	
/**
 * Constructor de la clase Mundo que inicializa un mundo con una superficie dada a partir de tres valores (filas y columnas)
 * y genera una superficie en base a esos parametros.
 * @param f valor de numero de filas de la superficie del mundo.
 * @param c valor de numero de columnas de la superficie del mundo.
 */
	public Mundo(int f, int c){
		this.superficie = new Superficie(f,c);
	}

/**
 * Consulta el valor de numero de filas de la superficie del mundo.
 * @return valor de filas asiganadas al atributo del objeto superficie del mundo
 */
	public int filasMundo(){
		return this.superficie.getFilas();
	}

/**
 * Consulta el valor de numero de columnas de la superficie del mundo.
 * @return valor de columnas asiganadas al atributo del objeto superficie del mundo
 */
	public int columnasMundo(){
		return this.superficie.getColumnas();
	}
	
/**
 * Cconsulta el numero de celulas de la superficie del mundo.
 * @return valor de filas asiganadas al atributo del objeto superficie del mundo
 */
	public int celulasMundo(){
		return this.superficie.getCelulas();
	}

/**
 * @param  simulacionTerminada atributo to set
 */
	public void esSimulacionTerminada(boolean simulacionTerminada) {
		SimulacionTerminada = simulacionTerminada;
	}

/**
 * @return valor del atributo simulacionTerminada
 */
	public boolean getSimulacionTerminada() {
		return SimulacionTerminada;
	}

/**
 * Se ubica un objeto Celula en una posicion especifica de la superficie
 * @param f representa el valor entero positivo del numero de fila
 * @param c representa el valor entero positivo del numero de columna
 * @param celula es un objeto tipo celula instanciado en la llamada al metodo
 * @return TRUE si el se pudo generar una celula en una celda vacia.FALSE para el resto de casos.
 */
	public boolean crearCelula(int f, int c, Celula celula){
		return this.superficie.llenarCelda(f, c, celula);
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
 * Se cambia a null todas las celdas de la superficie del mundo
 */
	public void vaciarMundo(){
		this.superficie.reset();
	}


/**
 * Representar en filas por columnas las celdas y las celulas existentes en ellas
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
 * Gestiona la evolución en un "paso" en el mundo. para ello, se moveran todas las celulas
 * del tablero UNA SOLA VEZ POR CELULA, y se les aplicara las correspondientes reglas de muerte o
 * reproduccion en funcion de lo que le corresponda a cada una. 
 * @return String concatenado con el informe de ejecucion de cada uno de los pasos.
 */
	public String evoluciona(){
		StringBuilder informe = new StringBuilder();
		int ocupadas = this.superficie.getCelulas();
		Casilla[] celdasOcupadas = new Casilla[ocupadas];
		
		llenarCeldasOcupadas(celdasOcupadas);

		for(int i = 0; i < ocupadas; i++){
			Casilla casillaMovida = paso(celdasOcupadas[i], informe);
			/*
			 * Buscamos por delante del indice de la casilla movida, en busca de una casilla identica, en cuyo caso se modificara a null para evitar aplicar al mismo objeto dos vece el metodo paso
			 */
			for(int j = i; j < ocupadas; j++){
				if(casillaMovida != null)
					if(casillaMovida.casillaIgual(celdasOcupadas[j]))
						celdasOcupadas[j] = null;
			}
		}
		return informe.toString();
	}
/**
 * Inicializa el contenido del array con las casillas que ocupan la superficie.
 * @param celdasOcupadas es el array de objetos tipo Casilla que se encuentra a valores vacios
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
 * Se ejecuta el movimiento de una celula en una posicion dada de la superficie
 * @param casilla es posicion de la celula que va a desplazarse en la superficie
 * @return Builder con la concatenacion del informe para la celula que se ha movido.
 */
	private Casilla paso(Casilla casilla, StringBuilder informe){
		Casilla casillaMovida = null;
		if(casilla != null){
			int f = casilla.getFila();
			int c = casilla.getColumna();
			casillaMovida = this.superficie.ejecutaMovimiento(f, c, informe);
		}
		return casillaMovida;
	}

//Fin de la clase
}