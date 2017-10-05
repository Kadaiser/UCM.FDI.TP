package es.ucm.fdi.tp.pr2.comando;


import java.io.IOException;

import es.ucm.fdi.tp.pr2.control.Controlador;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorLecturaFichero;

public class Cargar implements ComandoDoble {
	private String fichero;
	
	/**
	 * Constructor de la clase Guardar con parametro
	 * @param fichero contiene un String con el nombre del fichero sobre el que se desea guardar
	 */
		public Cargar(String fichero){
			this.fichero = fichero;
		}
		
	@Override
	public String ejecuta(Controlador controlador) throws IOException, ErrorLecturaFichero {
		controlador.cargar(this.fichero);		
		return "proceso de carga ejecutado";
	}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("cargar") && (datos.length == 2))
			comando = new Cargar(datos[1]);
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return "CARGAR: Se procesa un fichero de texto con el nombre solicitado para restaurar el estado de una partida" + System.getProperty("line.separator");
	}

}
