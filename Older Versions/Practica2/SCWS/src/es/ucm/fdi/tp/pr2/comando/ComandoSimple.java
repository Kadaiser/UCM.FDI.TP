package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.logica.Mundo;

/*
 * La interfaz heredada de Comando para implementar toda clase comando reconocida a partir de un array
 * de String de longitud igual a uno (Ej: iniciar)
 */
public interface ComandoSimple extends Comando{
	
	public abstract String ejecuta(Mundo mundo);
	
	public abstract Comando parsea(String[] datos);
	
	public abstract String textoAyuda();

}
