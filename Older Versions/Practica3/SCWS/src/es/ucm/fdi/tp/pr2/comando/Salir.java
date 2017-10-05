package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.control.Controlador;

public class Salir implements ComandoSimple{

	@Override
	public String ejecuta(Controlador controlador) {
		controlador.setSimulacionTerminada(true);
		return "Simulación terminada";
	}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("salir") && (datos.length == 1))
			comando = new Salir();
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return ("SALIR: Es una metainstruccion que nos permite abandonar la simulacion." + System.getProperty("line.separator"));
	}

}
