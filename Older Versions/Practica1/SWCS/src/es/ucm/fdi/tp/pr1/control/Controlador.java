package es.ucm.fdi.tp.pr1.control;

import java.util.Scanner;

import es.ucm.fdi.tp.pr1.logica.Mundo;

/**
 * Constructor de clase Controlador recibe dos parametros
 */
public class Controlador {
	private Mundo mundo;
	private Scanner entrada;
	
	private static final int NUM_CELULAS = 6;
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
 * @param m
 * @param sc
 */
	public Controlador(Mundo mundo, Scanner entrada){
		this.mundo = mundo;
		this.entrada = entrada;
	}
	
/**
 * El procedimiento de la simulacion solicita al usuario comandos de manera recursiva
 */
	public void realizarSimulacion(){
		boolean terminar = false;
		int f, c, n = NUM_CELULAS;
		
		
		while(!terminar){
			System.out.println(this.mundo.toString());
			
			String[] datos = this.construirComando();
			
			switch(datos[0]){			
	/*
	 * Para avanzar la simulacion, es necesario llamar al metodo evoluciona() de la clase Mundo
	 */
			case "paso":
				if(datos.length == 1){
				this.mundo.evoluciona();
				}
				break;		
	/*
	 * Iniciar un mundo: Se vacia el mundo actual , lo cual permite generar una superficie con nuevas celulas aleatorias.
	 */
			case "iniciar":
				if(datos.length == 1){
				System.out.println("Iniciando el mundo...");		
				this.mundo.vaciarMundo();
				this.mundo.generarCelulasAleatorias(n);
				}
				break;
	/*
	 * Para vaciar un mundo llamamos al metodo vaciarMundo() de la clase Mundo
	 */
			case "vaciar":
				if(datos.length == 1){
				System.out.println("Masacrando las celulas del mundo...(Cuanta maldad!)");
				this.mundo.vaciarMundo();
				}
				break;		
	/*
	 * Esta opcion muestra una lista de los posibles comandos del juego
	 */
			case "ayuda":
				if(datos.length == 1){
					System.out.println(this.mostrarAyuda());
				}
				break;		
	/*
	 * Esta opcion cambia el estado del booleano de control del bucle, finalizando la ejecucion
	 * del metodo.
	 */
			case "salir":
				if(datos.length == 1){
					System.out.println("Terminando la partida");
					terminar = true;
				}
				break;		
	/*
	 * Para generar una celula nueva necesitamos verificar que hay espacio en el tablero. Si esta condicion es TRUE, entonces
	 * hay que solicitar al usuario una posicion valida en la superficie evitando que introduzca valores erroneos.
	 */
			case "crearcelula":	
				if(datos.length == 3){
					f = Integer.parseInt(datos[1]);
					c = Integer.parseInt(datos[2]);
					
					if(validarDatos(f,c)){
						if(this.mundo.crearCelula(f,c))
							System.out.println("Se ha generado una celula nueva en la posicion (" + datos[1] + "," + datos [2] + ")");
						else
							System.out.println("Lo sentimos, ya existe una celula en esta posicion");
					}
					else
						System.out.println("Lo sentimos, los valores de posicion (fila y columna) no son validos");
				}
				break;
	/*
	 * Para eliminar una celula  necesitamos verificar que hay una celula en la posicion indicada. Previamente hay
	 * hay que solicitar al usuario una posicion valida en la superficie evitando que introduzca valores erroneos.
	 */
			case "eliminarcelula":	
				if(datos.length == 3){
					f = Integer.parseInt(datos[1]);
					c = Integer.parseInt(datos[2]);
					
					if(validarDatos(f,c)){
						if(this.mundo.eliminarCelula(f, c))
							System.out.println("Se ha eliminado una celula en la posicion (" + datos[1] + "," + datos [2] + ")");
						else
							System.out.println("Lo sentimos, no existe una celula en esta posicion");
					}
					else
						System.out.println("Lo sentimos, los valores de posicion (fila y columna) no son validos");
				}
				break;
		/*
		 * Todo comando no coincidente con los anterios sera reportado al usuario por pantalla	
		 */
			default :
				System.out.println("Comando desconocido, introduzca de nuevo: ");
				break;
			}
		}
	}



/**
 * Metodo que procesa el string del usuario y lo separa palabras guiandose del caracter ' ';
 * @return array de Strings en minusculas con el comando.
 */
	private String[] construirComando(){
		String usuario;
		String[] comando;
		System.out.print("Introduzca el comando: ");
		usuario = entrada.nextLine();
		String mutable  = usuario.toLowerCase();
		comando = mutable.split(" ");
	return comando;
	}
	
	
/**
 * 
 * @param f valor entero positivo acotado en el rango del numero de filas de la superficie de mundo
 * @param c valor entero positivo acotado en el rango del numero de columnas de la superficie de mundo
 * @return TRUE si ambos valores se consideran validos (positivos y acotados cerrados en la matriz)
 */
	private boolean validarDatos(int f, int c){
		return (f >= 0 && f < this.mundo.filasMundo()) && (c >= 0 && c < this.mundo.columnasMundo());
	}
	
/**
 * Procedimiento que solicita valores en la generacion manual de un mundo.
 * @param f es el valor de filas para la superficie
 * @param c es el valor de columnas para la superficie
 * @param n es el valor de celulas para la superficie
 */
	private void pedirDatosMundo(int f, int c, int n){
		
		System.out.println("Numero de filas del mundo: ");
		f = entrada.nextInt();
		while(f < 0){
			System.out.println("Inserta un numero POSITIVO de filas: ");
			f = entrada.nextInt();
		}
		
		System.out.println("Numero de columnas del mundo: ");
		c = entrada.nextInt();
		while(c < 0){
			System.out.println("Inserta un numero POSITIVO de columnas: ");
			c = entrada.nextInt();
		}	
	}

/**
 * Procedimiento para mostrar informacion de los comandos disponibles
 */
	private String mostrarAyuda(){
		StringBuilder builder = new StringBuilder();
		builder.append("Comandos disponibles para SWSC " + '\n');
		this.insertarSeparador(builder);
		builder.append("PASO: Mueve cada una de las celulas del mundo, respetando las reglas de evolucion." + '\n');
		builder.append("INICIAR: Elimina las celulas de la superficie e introduce nuevas celulas aleatorias." + '\n');
		builder.append("CREARCELULA f c: Crea la celula del mundo en la posicion (f,c)." + '\n');
		builder.append("ELIMINARCELULA f c: Elimina la celula del mundo en la posicion (f,c)." + '\n');
		builder.append("VACIAR: Elimina todas las celulas del mundo." + '\n');
		builder.append("SALIR: Es una metainstruccion que nos permite abandonar la simulacion." + '\n');
		builder.append("AYUDA : Lo que estas leyendo." + '\n');
		this.insertarSeparador(builder);
		return builder.toString();
	}

/**
 * Funcion que genera una cadena de caracteres de guiones a modo de separador
 * su uso es recomedable en funciones de salida por pantalla con salto de linea includio. ("printnl")
 * @return String de 35 caracteres '-'.
 */
	
	private void insertarSeparador(StringBuilder builder){
		for(int i = 0; i < 70; i++)
			builder.append('_');
		builder.append('\n');
	}
	
//Fin de la clase	
}