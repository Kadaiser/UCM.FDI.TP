package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.control.Controlador;

public class Vaciar implements ComandoSimple {

	@Override
	public String ejecuta(Controlador controlador) {
		controlador.vaciarMundo();	
		return "Masacrando las celulas del mundo...(Cuanta maldad!)";
	}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("vaciar") && (datos.length == 1))
			comando = new Vaciar();
		else
			comando = null;	
	return comando;
	}

	
	/**
	 * Metodo que retorna la informacion del comando Vaciar y su funcionamiento.
	 * @return tipo string 
	 */
	public String textoAyuda() {
		return ("VACIAR: Elimina todas las celulas del mundo." + System.getProperty("line.separator"));
	}

}
