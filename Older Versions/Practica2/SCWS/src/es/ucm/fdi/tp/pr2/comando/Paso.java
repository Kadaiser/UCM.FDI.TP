package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.logica.Mundo;

public class Paso implements ComandoSimple{

	@Override
	public String ejecuta(Mundo mundo) {
		return mundo.evoluciona();
	}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("paso") && (datos.length == 1))
			comando = new Paso();
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return ("PASO: Mueve cada una de las celulas del mundo, respetando las reglas de evolucion." + '\n');
	}

}
