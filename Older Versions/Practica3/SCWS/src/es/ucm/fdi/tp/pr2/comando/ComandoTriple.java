package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.control.Controlador;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFueraDeSuperficie;

/*
 * La interfaz heredada de Comando para implementar toda clase comando reconocida a partir de un array
 * de String de longitud igual a tres (Ej: eliminarcelula fila columna)
 */
public interface ComandoTriple extends Comando {

	public abstract String ejecuta(Controlador controlador) throws ErrorFueraDeSuperficie;
	
	public abstract Comando parsea(String[] datos) throws ErrorFormatoNumerico;
	
	public abstract String textoAyuda();
}
