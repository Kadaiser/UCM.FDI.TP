package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.logica.Mundo;

/*
 * La interfaz de Comando establece un criterio de implementacion para toda clase heredada de la mima.
 * Esto incluye el procedimiento de ejecucion de un comando sobre un mundo, El reconocimiento e 
 * instanciamiento del comando a partir del array de String del usuario, y su correspondiente
 * descripcion de uso para el comando "ayuda"
 */
public interface Comando {
	/**
 * Procedimiento aplicado al objeto de la clase Comando, desencadenado las acciones pertientes
 * programadas para dicho comando en el desarrollo del juego. 
 * @param mundo corresponde al objeto sobre el que se aplican las acciones del comando
 * @return String con la informacion del resultado del comando.
 */
	public abstract String ejecuta(Mundo mundo);
	
/**
 * Metodo reconocedor del array de Strings entregado por el usuario. Se precisa las coincidencias
 * necesarias de longitud del array para el comando, y la igualdad de caracteres en el primer String
 * del array respecto del comando comparado
 * @param datos es Array de Strings con la lectura del teclado del usuario
 * @return Comando instanciado si todas coincidancias son correctas con alguno de los posibles comandos.
 * Comando instanciado a NULL para el resto de casos
 */
	public abstract Comando parsea(String[] datos);
	
/**
 * @return String con la descripcion de funcionamiento de un comando
 */
	public abstract String textoAyuda();
	
}
