package es.ucm.fdi.tp.pr2.comando;

import java.io.IOException;
import es.ucm.fdi.tp.pr2.control.Controlador;

public class Guardar implements ComandoDoble {
	private String fichero;
	
/**
 * Constructor de la clase Guardar con parametro
 * @param fichero contiene un String con el nombre del fichero qsobre el que se desea guardar
 */
	public Guardar(String fichero){
		this.fichero = fichero;
	}
	
	@Override
	public String ejecuta(Controlador controlador) throws IOException {
		controlador.guardar(this.fichero);
		return "proceso de guardado ejecutado";
	}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("guardar") && (datos.length == 2))	
			comando = new Guardar(datos[1]);
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return "GUARDAR: Se procesa el estado de la partida para almacenarse en un fichero de texto con el nombre solicitado" + System.getProperty("line.separator");
	}

}
