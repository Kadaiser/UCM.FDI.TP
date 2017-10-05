package es.ucm.fdi.tp.pr2.comando;

import java.io.IOException;
import es.ucm.fdi.tp.pr2.control.Controlador;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFueraDeSuperficie;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorIniciarPartida;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorLecturaFichero;

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
 * @param controlador corresponde al objeto sobre el que se aplican las acciones del comando
 * @return String con la informacion del resultado del comando.
 * @throws IOException si los procesos de carga/guardado fallaron en la interracion con los ficheros.
 * @throws ErrorFueraDeSuperficie si el comando trato de aplicar algun cambio en la superficie fuera de su rango.
 * @throws ErrorFormatoNumerico si el comando se entrego con parametros que se esperaban parseables a enteros y no lo eran
 * @throws ErrorLecturaFichero si el proceso de carga encontro algun error en alguna linea del ficero de carga.
 */
	public abstract String ejecuta(Controlador controlador) throws IOException, ErrorFueraDeSuperficie, ErrorFormatoNumerico, ErrorLecturaFichero;
	
/**
 * Metodo reconocedor del array de Strings entregado por el usuario. Se precisa las coincidencias
 * necesarias de longitud del array para el comando, y la igualdad de caracteres en el primer String
 * del array respecto del comando comparado
 * @param datos es Array de Strings con la lectura del teclado del usuario
 * @return Comando instanciado si todas coincidancias son correctas con alguno de los posibles comandos.
 * Comando instanciado a NULL para el resto de casos
 * @throws ErrorIniciarPartida  si alguno de los parametros para el comando jugar no eran correctos
 * @throws ErrorFormatoNumerico si alguno de los parametros del comando que se esperaban parseables a enteros no lo era
 */
	public abstract Comando parsea(String[] datos) throws ErrorIniciarPartida, ErrorFormatoNumerico;
	
/**
 * @return String con la descripcion de funcionamiento de un comando
 */
	public abstract String textoAyuda();
	
}
