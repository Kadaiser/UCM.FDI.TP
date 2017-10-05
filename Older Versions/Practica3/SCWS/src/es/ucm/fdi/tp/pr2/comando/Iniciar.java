package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.control.Controlador;


public class Iniciar implements ComandoSimple{
	
	@Override
	public String ejecuta(Controlador controlador){
		controlador.vaciarMundo();
		controlador.inicializaMundo();
		return "Iniciando el mundo...";
	}
	
	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("iniciar") && (datos.length == 1))
			comando = new Iniciar();
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return ("INICIAR: Elimina las celulas de la superficie e introduce nuevas celulas aleatorias." + System.getProperty("line.separator"));
	}

}
