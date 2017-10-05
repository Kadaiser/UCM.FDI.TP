package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.celula.CelulaSimple;
import es.ucm.fdi.tp.pr2.logica.Mundo;

public class CrearCelulaSimple implements ComandoTriple {
	private int fila;
	private int columna;
	
	public CrearCelulaSimple(int fila, int columna){
		this.fila = fila;
		this.columna = columna;
	}
	
	@Override
	public String ejecuta(Mundo mundo) {
		String respuesta = "";
		if(mundo.crearCelula(this.fila, this.columna, new CelulaSimple()))
			respuesta = "Se ha generado una celula nueva en la posicion (" + this.fila + "," + this.columna + ")";
		else
			respuesta = "Lo sentimos, ya existe una celula en esta posicion";
		return respuesta;
	}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("crearcelulasimple") && (datos.length == 3)){
			int f = Integer.parseInt(datos[1]);
			int c = Integer.parseInt(datos[2]);
			comando = new CrearCelulaSimple(f,c);
		}
		else
			comando = null;	
	return comando;
	}
	
	@Override
	public String textoAyuda() {
		return ("CREARCELULASIMPLE F C: Crea una celula simple en el mundo en la posicion (F,C)." + '\n');
	}

}
