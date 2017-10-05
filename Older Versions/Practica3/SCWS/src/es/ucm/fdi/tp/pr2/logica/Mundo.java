package es.ucm.fdi.tp.pr2.logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.celula.ParserCelulas;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorLecturaFichero;



public abstract class Mundo {
	protected Superficie superficie;
	protected ParserCelulas celulas;

	
	public Mundo(){
		this.superficie = new Superficie();
	}
/**
 * Constructor de la clase abstracta Mundo con paramteros que determianan el tamaño del contenedor superficie
 * @param f valor entero positivo con el numero de filas de la superficie
 * @param c valor entero positivo con el numero de columna de la superficie
 */
	public Mundo(int f, int c){
		this.superficie = new Superficie(f, c);
	}
	
	
/**
* Genera un random a modulo n;
* @param n es un valor entero positivo
* @return un valor entero positivo aleatorio a modulo con el parametro n.
*/
	protected static int generarPosicion(int n){
		Random aleatorio = new Random();
		return aleatorio.nextInt(n);
	}	

/**
 * Se reiniciar el mundo con una cantidad determinada de celulas con la que dicho mundo fue instanciado
 */
	public abstract void inicializaMundo();
	
	
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
 * Consulta el numero de celulas de la superficie del mundo.
 * @return valor de filas asiganadas al atributo del objeto superficie del mundo
 */
	public int celulasMundo(){
		return this.superficie.getCelulas();
	}

/**
 * Se genera un informe con las posibles celulas que se pueden crear en ese mundo
 * @return string con la informacion de celulas disponibles para el mundo instanciado
 */
	public abstract String celulasDisponibles();
	
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
 * Se parsea un tipo especifico de celula permitido en el mundo actual
 * @param solicitud es el estring que ha de coincidir con algun tipo de celula
 * @return Celula coincidente al creiterio del parametro, null para el resto
 */
	public abstract Celula insertarcelula(String solicitud);

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
		builder.append("Mundo ");
		builder.append(this.identidadMundo());
		builder.append(" de tamaño ");
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
 * Identifica el tipo de mundo instanciado
 * @return string con la identidad del mundo
 */
	public abstract String identidadMundo();
	
/**
 * Se procesa el estado del mundo para almacenarse en un String que pueda ser escrito en un fichero
 * @return String con la configuracion de las celulas en la superficie.
 */
	public String guardar(){
		int f, c;
		int ocupadas = this.superficie.getCelulas();
		Casilla[] celdasOcupadas = new Casilla[ocupadas];
		llenarCeldasOcupadas(celdasOcupadas);
		
		StringBuilder salida = new StringBuilder();
		salida.append(this.identidadMundo() + System.getProperty("line.separator"));
		salida.append(this.superficie.getFilas() + System.getProperty("line.separator"));
		salida.append(this.superficie.getColumnas() + System.getProperty("line.separator"));
		salida.append(ocupadas + System.getProperty("line.separator"));
		
		for(int i = 0; i < ocupadas; i++)	{	
			f = celdasOcupadas[i].getFila();
			c = celdasOcupadas[i].getColumna(); 
			salida.append(f + " " + c + " " +this.superficie.guardar(f, c) + System.getProperty("line.separator") );
		}
		return salida.toString();
	}
		
/**
 * Se procesa el contenido de un fichero de texto plano para generar un mundo en el estado en el que se guardo
 * @param flujoCarga es el flujo de datos del fichero que se usara para reconstruir el mundo
 * @throws ErrorLecturaFichero con informacion especifica de la linea corrupta que impide cargar correctamente el fichero en el juego
 * @throws IOException si el flujo de escritura se interrumpe durante la ejecucion del metodo
 */
	public void cargar(BufferedReader flujoCarga) throws  ErrorLecturaFichero, IOException{
		String[] datos;
		Celula celula;
		try{
			int numCelulas = Integer.parseInt(flujoCarga.readLine());
	
			for(int i = 0; i < numCelulas; i++){
				datos = (flujoCarga.readLine()).split(" ");
				try{
					celula = this.celulas.cargaCelula(datos);	
					this.superficie.llenarCelda(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), celula);
				}catch(ErrorFormatoNumerico e){
					throw new ErrorLecturaFichero(i + 5, e.getMessage()); //sumamos 5 al contador para ajustar el contador a la linea adecauda (las lineas de celulas empiezan a partir de las 5)
				}catch(ErrorLecturaFichero e){
					throw new ErrorLecturaFichero(i + 5, e.getMensaje());
				}
			}
		}catch(IOException e){
		}catch(NumberFormatException e){
			throw new ErrorLecturaFichero(4, " el contador de celulas del fichero no es un formato numerico");
		}catch(NullPointerException e){
			throw new ErrorLecturaFichero(4, " el numero de Celulas del fichero es superior al numero de celulas listado");
		}
		if(flujoCarga.read() != -1) //si no se encuentra el EOF entonces quedan celulas por cargar (o fichero pendiente de tratar)
			throw new ErrorLecturaFichero(4, " el numero de Celulas del fichero es inferior al numero de celulas listado");
	}

/**
 * Se entrega un objeto instanciado si se recibe la conicidencia adecuada por parametro
 * @param identidad del mundo que se desea instancair
 * @return Mundo instanciado si la coincidancia es correcta con alguno de los posibles mundos.
 * Comando instanciado a NULL para el resto de casos
 */
	public abstract Mundo parsea(String[] identidad);
		
}
