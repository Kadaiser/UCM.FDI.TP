package es.ucm.fdi.tp.pr2.comando;

import java.io.FileNotFoundException;
import java.io.IOException;

import es.ucm.fdi.tp.pr2.control.Controlador;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorLecturaFichero;

/*
 * La interfaz heredada de Comando para implementar toda clase comando reconocida a partir de un array
 * de String de longitud igual a dos (Ej: vaciar celulascomplejas)
 */

public interface ComandoDoble extends Comando {
	
	public abstract String ejecuta(Controlador controlador) throws FileNotFoundException, IOException, ErrorFormatoNumerico, ErrorLecturaFichero;
	
	public abstract Comando parsea(String[] datos);
	
	public abstract String textoAyuda();
}
