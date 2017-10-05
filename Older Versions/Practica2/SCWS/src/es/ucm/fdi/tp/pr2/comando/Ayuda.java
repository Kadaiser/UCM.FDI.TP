package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.logica.Mundo;

public class Ayuda implements ComandoSimple {

	@Override
	public String ejecuta(Mundo mundo) {
		return ParserComando.AyudaComandos();
	}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		
		//if(datos[0].equals("ayuda") && (datos.length == 1))
		if(datos[0].equalsIgnoreCase("ayuda") && (datos.length == 1))
			comando = new Ayuda();
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return ("AYUDA : Lo que estas leyendo." + '\n');
	}

}
