package es.ucm.fdi.tp.pr1.logica;


public class Superficie {
	private Celula[][] superficie;
	private int filas;
	private int columnas;
	private int celulas;
	

/**
 * Constructor generico de la clase Superficie, se genera una superficie vacia de tamaño 5x5
 */
	public Superficie(){
		this.filas = 5;
		this.columnas = 5;
		this.celulas = 0;
		this.superficie = new Celula[5][5];
	}
/**
 * Metodo constructor de la clase Superficie, genera un objeto superficie.
 * @param f representa el valor entero positivo de el numero de filas
 * @param c representa el valor entero positivo de el numero de columnas
 */
	public Superficie(int f, int c){
		this.filas = f;
		this.columnas = c;
		this.celulas = 0;
		this.superficie = new Celula[this.filas][this.columnas];
	}
	
/**
 * Procedimiento para cambiar a null todas las posiciones de la matriz Superficie
 */
	public void reset(){
		for(int i = 0; i < this.filas; i++)
			for(int j = 0; j < this.columnas; j++)
				this.superficie[i][j]= null;
		this.celulas = 0;
	}
/**
 * Metodo para programacion defensiva de la clase Superficie (sight...)
 * @param f
 * @param c
 * @return TRUE si el valor de los parametros se consideran dentro del rango de la superficie
 */
	private boolean dentroRango(int f, int c){
		return ((f < this.getFilas() && f >= 0) && (c < this.getColumnas() && c >=0));
	}
/**
 * Metodo para consultar el numero de celulas existentes la superficie.
 * @param f representa el valor entero positivo de el numero de fila
 * @param c representa el valor entero positivo de el numero de columna
 * @return TRUE si la celda verificada tiene valor NULL. FALSE para el resto de casos.
 */
	public boolean verificarCeldaVacia(int f, int c){
	return (this.superficie[f][c] == null);
	}

/**
 * Metodo para eliminar una celula en la posicion definida por los parametros
 * @param f representa el valor entero positivo de el numero de fila
 * @param c representa el valor entero positivo de el numero de columna
 * @return TRUE si la celda estaba llena y pudo vaciarse. FALSE si la celda ya estaba vacia.
 */
	public boolean vaciarCelda(int f, int c){
		boolean ok = false;
		if(!verificarCeldaVacia(f,c) && dentroRango(f,c)){
			this.superficie[f][c] = null;
			ok = true;
			this.celulas--;
		}
		return ok;
	}
	
/**
 * 
 * @param f representa el valor entero positivo de el numero de fila
 * @param c representa el valor entero positivo de el numero de columna
 * @return TRUE si la celda estaba vacia y pudo generarse una nueva celula. FALSE si la celda ya estaba ocupada.
 */
	public boolean llenarCelda(int f, int c, Celula celula){
		boolean ok = false;
		if(espacioLibre()){
			if(verificarCeldaVacia(f,c) && dentroRango(f,c) ){
				this.superficie[f][c] = celula;
				ok = true;
				this.celulas++;
			}
		}
		return ok;
	}

/**
 * Metodo para comprobar espacio disponible en una superficie.
 * @return TRUE si queda al menos una celda libre en la superficie. False para caso contrario.
 */
	public boolean espacioLibre(){
		return (this.getCelulas() <= this.getFilas()*this.getColumnas());
	}

/**
 * @return numero entero positivo de filas del objeto superficie
 */
	public int getFilas() {
		return filas;
	}

/**
 * @return numero entero positivo de columnas del objeto superficie
 */
	public int getColumnas() {
		return columnas;
	}
	
/**
 * @return numero entero positivo de columnas del objeto superficie
 */
	public int getCelulas() {
		return celulas;
	}
		

/**
 * Procedimeinto que reubica una celula en una posicion dada sobre una nueva posicion definida por dos nuevos valores 
 * @param fila representa el valor entero positivo del numero de fila en la que se encuentra la celula
 * @param columna representa el valor entero positivo del numero de columna en la que se encuentra la celula
 * @param nuevafila representa el valor entero positivo del numero de fila de la nueva posicion (distintno del parametro fila)
 * @param nuevaColumna representa el valor entero positivo del numero de columna de la nueva posicion (distintno del parametro columna)
 */
	public void moverCelula(int fila, int columna, int nuevafila, int nuevaColumna){
		this.superficie[nuevafila][nuevaColumna] = this.superficie[fila][columna];
		this.superficie[fila][columna] = null;
	}
	
/**
 * Metodo para decrementar el valor del atributo pasosSinMover de una celula 
 * en una posicion de la superficie definida por los parametros
 * @param f representa el valor entero positivo de el numero de fila
 * @param c representa el valor entero positivo de el numero de columna
 * @return True si el metodo pudo decrementar los valores, False indica que la celula debe morir
 */
	public boolean decrementarPasosSinMover(int f, int c){
		return this.superficie[f][c].decrementarPasosSinMover();
	}
	
/**
 * Metodo para decrementar el valor del atributo pasosReproduccion de una celula 
 * en una posicion de la superficie definida por los parametros
 * @param f representa el valor entero positivo del numero de fila
 * @param c representa el valor entero positivo del numero de columna
 * @return True si el metodo pudo decrementar los valores, False indica que la celula debe reproducirse
 */
	public boolean decrementarPasosReproduccion(int f, int c){
		return this.superficie[f][c].decrementarPasosReproduccion();
	}
/**
 * Metodo que consulta si el valor del atributo pasosReproduccion se encunentra a 0
 * @param f representa el valor entero positivo del numero de fila
 * @param c representa el valor entero positivo del numero de columna
 * @return TRUE si el valor del atributo es cero, FALSE para el resto de casos.
 */
	public boolean pasosReproduccionAgotados(int f, int c){
		return this.superficie[f][c].pasosReproduccionAgotados();
	}
	
/**
 * Metodo para construir un String concatenado con todas las posiciones de la matriz.
 * representando con el caracter '-' la ausencia de un objeto en una celda vacia.
 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.filas; i++){
			builder.append('\n');
			for (int j = 0; j < this.columnas; j++){
				builder.append(' ');
				
				if(!verificarCeldaVacia(i, j))
					builder.append (this.superficie[i][j].toString());
		
				else
					builder.append(" - ");
			}
		}
		builder.append ('\n');
		
		return builder.toString();
	}

	//Fin de la clase
}
