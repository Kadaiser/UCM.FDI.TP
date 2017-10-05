package es.ucm.fdi.tp.pr2.control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.comando.Comando;
import es.ucm.fdi.tp.pr2.comando.ParserComando;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorComandoDesconocido;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFueraDeSuperficie;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorIniciarPartida;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorLecturaFichero;
import es.ucm.fdi.tp.pr2.logica.Mundo;
import es.ucm.fdi.tp.pr2.logica.mundos.MundoSimple;
import es.ucm.fdi.tp.pr2.logica.mundos.ParserMundo;
import es.ucm.fdi.tp.pr2.vista.SalidaConsola;

/**
 * Constructor de clase Controlador recibe dos parametros
 */
public class Controlador {
	private boolean SimulacionTerminada = false;
	private Mundo mundo;
	private Scanner entrada;

/**
 * Constructor generico de la clase Controlador
 */
	public Controlador(){
		this.mundo = new MundoSimple(0,0);
		this.entrada = new Scanner(System.in);
	}
	
/**
 * Se sustitulle un objeto mundo por el correspondiente en el parametro.
 * @param mundo es el nuevo objeto que ha de sustituir el atributo del controlador
 */
	public void juega(Mundo mundo){
		this.mundo = mundo;
	}
	
/**
 * Modifica el valor del atributo para la clase Controlador.
 * @param estado al que se desea cambiar el valor del atributo
 */
	public void setSimulacionTerminada(boolean estado){
		this.SimulacionTerminada = estado;
	}
/**
 * 
 * El procedimiento de la simulacion solicita al usuario comandos de manera recursiva
 * hasta que invoque al comando "salir". La lectura del datos del usuario su usa para instanciar
 * nuevos comandos que desarrollen el curso de la simulacion
 */
	public void realizarSimulacion(){
		Mundo mundoAuxiliar = null; //En caso de fallo del proceso de carga
		
		while(!this.SimulacionTerminada){
			
			mundoAuxiliar = this.mundo; //backup del mundo para posbiles errores del proceso de carga
			SalidaConsola.consolaEstandar(this.mundo.toString());
			
			String[] datos = this.construirComando();
			try{
				Comando comando = ParserComando.parseaComando(datos);
				SalidaConsola.consolaEstandar(comando.ejecuta(this));
				
			}catch (ErrorComandoDesconocido e){
				SalidaConsola.consolaError(e.getMessage());
			}catch (ErrorFormatoNumerico e){
				SalidaConsola.consolaError(e.getMessage());
			}catch (ErrorFueraDeSuperficie e){
				SalidaConsola.consolaError(e.getMessage());
			}catch (ErrorIniciarPartida e){
				SalidaConsola.consolaError(e.getMessage());
			}catch (FileNotFoundException e){
				SalidaConsola.consolaError(e.getMessage());
			}catch (IOException e){
				SalidaConsola.consolaError(e.getMessage());
			}catch (ErrorLecturaFichero e){
				SalidaConsola.consolaError(e.getMensaje());
				this.mundo = mundoAuxiliar; //si el proceso de cargado instancia un mundo nuevo que genero un error, restauramos el que habia
			}
		}
	}



/**
 * Metodo que procesa el string del usuario y lo separa palabras guiandose del caracter ' ';
 * Solicita de manera recursiva comandos con multiples argumentos si fallan los valores correspondientes a las
 * filas y columnas de comandos "triples"
 * @return array de Strings en minusculas con el comando.
 */
	private String[] construirComando(){

		String usuario;
		String[] comando = null;
		SalidaConsola.consolaEstandar("Introduzca el comando: ");

		if(entrada.hasNextLine()){
			usuario = entrada.nextLine();
			String mutable  = usuario.toLowerCase();
			comando = mutable.split(" ");
		}
		return comando;
	}
	
/**
 * Se muestra una lista de celulas para que el usuario elija la deseada
 * @return una celula instanciada si se encontro alguna coincidencia, celula a null en el resto de casos
 */
	public Celula pedirCelula(){
		SalidaConsola.consolaEstandar(this.mundo.celulasDisponibles());
		String solicitud = this.entrada.nextLine();		
		return this.mundo.insertarcelula(solicitud);
	}
	
//==========================================Funciones de conexion  Mundo-Controlador ==================================================//
/**
 * Identifica el tipo de mundo instanciado
 * @return string con la identidad del mundo
 */
	public String identidadMundo(){
		return this.mundo.identidadMundo();
	}
/**
 * Consulta el valor de numero de filas de la superficie del mundo.
 * @return valor de filas asiganadas al atributo del objeto superficie del mundo
 */
	public int filasMundo(){
		return this.mundo.filasMundo();
	}

/**
 * Consulta el valor de numero de columnas de la superficie del mundo.
 * @return valor de columnas asiganadas al atributo del objeto superficie del mundo
 */
	public int columnasMundo(){
		return this.mundo.columnasMundo();
	}
			
/**
 * Consulta el numero de celulas de la superficie del mundo.
 * @return valor de filas asiganadas al atributo del objeto superficie del mundo
 */
	public int celulasMundo(){
		return this.mundo.celulasMundo();
	}
		
	public void inicializaMundo(){
		this.mundo.inicializaMundo();
	}
/**
 * 
 * @param f representa el valor entero positivo del numero de fila
 * @param c representa el valor entero positivo del numero de columna
 * @return TRUE si se pudo vaciar la celda habiendo en la misma una celula
 * FALSE para el resto de casos.
 */
	public boolean eliminarCelula(int f, int c){
		return this.mundo.eliminarCelula(f, c);
	}
	
/**
 * Se ubica un objeto Celula en una posicion especifica de la superficie
 * @param f representa el valor entero positivo del numero de fila
 * @param c representa el valor entero positivo del numero de columna
 * @param celula es un objeto tipo celula instanciado en la llamada al metodo
 * @return TRUE si el se pudo generar una celula en una celda vacia.FALSE para el resto de casos.
 */
	public boolean crearCelula(int f, int c, Celula celula){
		return this.mundo.crearCelula(f, c, celula);
	}
		
/**
 * Se cambia a null todas las celdas de la superficie del mundo
 */
	public void vaciarMundo(){
		this.mundo.vaciarMundo();
	}

/**
 * Se procesa el estado del mundo para almacenarse en un String que pueda ser escrito en un fichero
 * @param fichero string con el contenido del nombre del ficehro que se desea guardar
 * @throws IOException con mensaje definido ante bloqueo de fichero o interrupcion del flujo de lectura sobre el mismo.
 */
	public void guardar(String fichero) throws IOException{
		FileWriter salida = null;
		try {
			 salida = new FileWriter(fichero);
			 salida.write(this.mundo.guardar());
		} catch (IOException e) {
			throw new IOException("El fichero no es accesible");	
		}finally{
			if(salida != null)
				salida.close();
		}
	}
	
/**
 * Se procesa el contenido de un fichero de texto plano para generar un mundo en el estado en el que se guardo
 * @param fichero es el string con el nombre del fichero que se usara para cargar el mundo
 * @throws ErrorLecturaFichero con informacion especifica de la linea corrupta que impide cargar correctamente el fichero en el juego, o informe de la inexistencia del fichero que se desea cargar.
 * @throws IOException con mensaje definido ante bloqueo de fichero o interrupcion del flujo de lectura sobre el mismo.
 */
	public void cargar(String fichero) throws ErrorLecturaFichero, IOException{


		BufferedReader  flujoCarga = null;
		Mundo mundo = null;
		String[] indetidad = new String[3]; //Las 3 primeras lineas del fichero disponen de info suficiente para generar un mundo sin celulas
		try {
			FileReader entrada = new FileReader(fichero);
			flujoCarga = new BufferedReader(entrada);
			indetidad[0] = flujoCarga.readLine(); //Tipo de mundo
			indetidad[1] = flujoCarga.readLine(); //Filas del Mundo
			indetidad[2] = flujoCarga.readLine(); //Columnas del Mundo
			mundo = ParserMundo.parseaMundo(indetidad); //Se devuelve un mundo instanciado por la identidad
			this.juega(mundo);
			this.mundo.cargar(flujoCarga); //Una vez el mundo se instancia se empizan a incluir las celulas del fichero
			
		} catch (FileNotFoundException e){
			throw new ErrorLecturaFichero("El fichero no existe");
		}catch (NullPointerException e){
			throw new ErrorLecturaFichero(1,"El tipo de mundo del fichero es incorrecto");
		} catch (IOException e) {	
			throw new ErrorLecturaFichero("El fichero no es accesible");
		}catch (ErrorLecturaFichero e){
			throw new ErrorLecturaFichero("Hay datos corruptos en el fichero de carga en la linea " + e.getNumLinea() + e.getMensaje());
		}catch (NumberFormatException e){
			throw new ErrorLecturaFichero("los parametros que definen el tamaño del mundo son incorrectos");
		}finally{
			if(flujoCarga != null)
				flujoCarga.close();
		}
	}
	
/**
 * Gestiona la evolución en un "paso" en el mundo. para ello, se moveran todas las celulas
 * del tablero UNA SOLA VEZ POR CELULA, y se les aplicara las correspondientes reglas de muerte o
 * reproduccion en funcion de lo que le corresponda a cada una. 
 * @return String concatenado con el informe de ejecucion de cada uno de los pasos.
 */
	public String evoluciona(){
		return this.mundo.evoluciona();
	}
	
//Fin de la clase	
}
