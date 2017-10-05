package es.ucm.fdi.tp.pr2.logica;

import es.ucm.fdi.tp.pr2.celula.Celula;

public class Superficie {
	private Celula[][] superficie;
	private int filas;
	private int columnas;
	private int celulas;
	

/**
 * Constructor generico de la clase Superficie, se genera una superficie vacia de tamaño 5x5
 */
	public Superficie(){
		this.filas = 0;
		this.columnas = 0;
		this.celulas = 0;
	}
	
/**
 * Constructor de la clase Superficie, genera un objeto superficie.
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
 * Se cambian a null todas las posiciones de la matriz Superficie y se restaura el valor del atributo "Celulas" a '0'
 */
	public void reset(){
		for(int i = 0; i < this.filas; i++)
			for(int j = 0; j < this.columnas; j++)
				this.superficie[i][j]= null;
		this.celulas = 0;
	}
	
/**
 * Metodo para programacion defensiva de la clase Superficie (A falta de excepciones)
 * @param f representa el valor entero positivo de el numero de filas
 * @param c representa el valor entero positivo de el numero de columnas
 * @return TRUE si el valor de los parametros se consideran dentro del rango de la superficie
 */
	private boolean dentroRango(int f, int c){
		return ((f < this.getFilas() && f >= 0) && (c < this.getColumnas() && c >=0));
	}
	
/**
 * Consultar el numero de celulas existentes la superficie.
 * @param f representa el valor entero positivo de el numero de fila
 * @param c representa el valor entero positivo de el numero de columna
 * @return TRUE si la celda verificada tiene valor NULL. FALSE para el resto de casos.
 */
	public boolean verificarCeldaVacia(int f, int c){
	return (this.superficie[f][c] == null);
	}

/**
 * Se elimina una celula en la posicion definida por los parametros
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
 * Se inserta  en la posicion definida por los parametros
 * @param f representa el valor entero positivo de el numero de fila
 * @param c representa el valor entero positivo de el numero de columna
 * @param celula es el objeto que se va a copiar en la superficie
 * @return TRUE si la celda estaba vacia y pudo generarse una nueva celula. FALSE si la celda ya estaba ocupada.
 */
	public boolean llenarCelda(int f, int c, Celula celula){
		boolean ok = false;
		if(espacioLibre()){
			if(verificarCeldaVacia(f,c) && dentroRango(f,c)){
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
		return (this.getCelulas() < this.getFilas()*this.getColumnas());
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
 * Se reubica una celula en una posicion dada sobre una nueva posicion definida por dos nuevos valores 
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
 * Aprovecha la polimorfia de la clase Celula para mover cada tipo de celula segun sus respectivas normas por el tablero
 * no se puede aplicar este metodo a una posicion de la superficie vacia.
 * @param f Entero que representa la fila de la celula
 * @param c Entero que representa la columna de la celula
 * @param informe entregado al metodo para incluir una descripcion del comportamiento de la celula para esa ejecución
 * @return Casilla modificada o null si no se modifico.
 */
	public Casilla ejecutaMovimiento(int f, int c, StringBuilder informe){
		Casilla casillaMovida = null;
		if(!this.verificarCeldaVacia(f, c))
			casillaMovida = superficie[f][c].ejecutaMovimiento(f,c, this, informe);
		return casillaMovida;
	}

/**
 * Se procesa una celula especifica, la polimorfia define que tipo de procesamiento se ejecuta en funcion del tipo de celula a tratar
 * @param f valor de posicion de fila de la celula
 * @param c valor de posicion de fila de la celula
 * @return String con sus respectivos atributos
 */
	public String guardar(int f, int c){
			return superficie[f][c].guardar();
	}
	
/**
 * Consulta la implementacion de una celula derivada instanciada en la clase padre Celula en la superficie para informar de si es comestible
 * @param f Entero que representa la fila de la celula
 * @param c Entero que representa la columna de la celula
 * @return segun la implementacion de la calse derivada de celula
 */
	public boolean esComestible(int f, int c){		
		return superficie[f][c].esComestible();
		
	}
/**
 * Construiye un String concatenado con todas las posiciones de la matriz.
 * representando con el caracter '-' la ausencia de un objeto en una celda vacia.
 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.filas; i++){
			builder.append(System.getProperty("line.separator"));
			for (int j = 0; j < this.columnas; j++){
				builder.append(' ');
				
				if(!verificarCeldaVacia(i, j))
					builder.append (this.superficie[i][j].mostrarCelula());
		
				else
					builder.append(" - ");
			}
		}
		builder.append (System.getProperty("line.separator"));
		
		return builder.toString();
	}

	//Fin de la clase
}
