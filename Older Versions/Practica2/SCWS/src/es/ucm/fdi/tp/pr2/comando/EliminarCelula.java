package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.logica.Mundo;

public class EliminarCelula implements ComandoTriple {
	private int fila;
	private int columna;
	
	public EliminarCelula(int fila, int columna){
		this.fila = fila;
		this.columna = columna;
	}
	
	@Override
	public String ejecuta(Mundo mundo) {
		String respuesta = "";
		if(mundo.eliminarCelula(this.fila, this.columna))
			respuesta = "Se ha eliminado una celula en la posicion (" + this.fila + "," + this.columna + ")";
		else
			respuesta = "Lo sentimos, no existe una celula en esta posicion";
		return respuesta;
		}

	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("eliminarcelula") && (datos.length == 3)){
			int f = Integer.parseInt(datos[1]);
			int c = Integer.parseInt(datos[2]);
			comando = new EliminarCelula(f,c);
		}
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return ("ELIMINARCELULA F C: Elimina la celula en el mundo en la posicion (F ,C)." + '\n');
	}

}
