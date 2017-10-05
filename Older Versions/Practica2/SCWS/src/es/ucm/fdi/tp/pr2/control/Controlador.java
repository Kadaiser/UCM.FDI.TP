package es.ucm.fdi.tp.pr2.control;
import java.util.Scanner;

import es.ucm.fdi.tp.pr2.comando.Comando;
import es.ucm.fdi.tp.pr2.comando.ParserComando;
import es.ucm.fdi.tp.pr2.logica.Mundo;

/**
 * Constructor de clase Controlador recibe dos parametros
 */
public class Controlador {
	private Mundo mundo;
	private Scanner entrada;

/**
 * Constructor generico  de la clase Controlador, inicializa un flujo de teclado para que el usuario pueda introducir
 * comandos por consola que controlen el desarrollo de la partida.
 *  
 */
	public Controlador(){
		this.entrada = new Scanner(System.in);
		this.mundo = new Mundo();
	}

/**
 * Constructor de la clase Controlador que recibe los parametros necesarios para inciar una partida tipo
 * @param mundo es el objeto cuyo comportamiento va a ser simulado
 * @param entrada es el flujo de entrada de datos del usuario (por teclado)
 */
	public Controlador(Mundo mundo, Scanner entrada){
		this.mundo = mundo;
		this.entrada = entrada;
	}
	
/**
 * El procedimiento de la simulacion solicita al usuario comandos de manera recursiva
 * hasta que invoque al comando "salir". La lectura del datos del usuario su usa para instanciar
 * nuevos comandos que desarrollen el curso de la simulacion
 */
	public void realizarSimulacion(){
		
		while(this.mundo.getSimulacionTerminada()){
			System.out.println(this.mundo.toString());
			
			String[] datos = this.construirComando();
			
			Comando comando = ParserComando.parseaComando(datos);
			if(comando != null)
				System.out.println(comando.ejecuta(this.mundo));
	
			else
				System.out.println("Comando desconocido, introduzca de nuevo: ");
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
		System.out.print("Introduzca el comando: ");

		if(entrada.hasNextLine()){
			usuario = entrada.nextLine();
			String mutable  = usuario.toLowerCase();
			comando = mutable.split(" ");
			if(comando.length == 3 && !validarDatos(comando[1],comando[2])){
				System.out.println("Lo sentimos, los valores de posicion (fila y columna) no son validos");
				comando = construirComando();
			}
		}
		return comando;
	}

	
/**
 * Metodo verificador del rango de valores acotados dentro del criterio valido.
 * @param f valor entero acotado cerrado en el intervalo 0 y numero de filas de la superficie de mundo
 * @param c valor entero acotado cerrado en el intervalo 0 y numero de columnas de la superficie de mundo
 * @return TRUE si ambos valores se consideran validos
 */
	private boolean validarDatos(String f, String c){
		boolean ok = false;
		if(tryParseInt(f) && tryParseInt(c)){
			int numf=Integer.parseInt(f);
			int numc=Integer.parseInt(c);
			if((numf >= 0 && numf < this.mundo.filasMundo()) && (numc >= 0 && numc < this.mundo.columnasMundo()))
				ok = true;
		}
		return ok;
	}
	
/**
 * Metodo discriminador de conversion de elementos no enteros para filtar la excepcion de valores de usuario no previstos
 * @param valor String que espera ser convertido en variable tipo int
 * @return TRUE si el valor ES correspondiente a un valor entero
 */
	private boolean tryParseInt(String valor) {
		boolean ok = false;
		try {
			Integer.parseInt(valor);
			ok = true;
		} 
		catch (NumberFormatException e){
		}
	return ok;
	}
//Fin de la clase	
}
