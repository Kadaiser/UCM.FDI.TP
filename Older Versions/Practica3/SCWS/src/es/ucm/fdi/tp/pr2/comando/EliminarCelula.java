package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.control.Controlador;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFueraDeSuperficie;

public class EliminarCelula implements ComandoTriple {
	private int fila;
	private int columna;
	
	public EliminarCelula(int fila, int columna){
		this.fila = fila;
		this.columna = columna;
	}
	
	@Override
	public String ejecuta(Controlador controlador) throws ErrorFueraDeSuperficie{
		String respuesta = "";
		try{
			if(controlador.eliminarCelula(this.fila, this.columna))
				respuesta = "Se ha eliminado una celula en la posicion (" + this.fila + "," + this.columna + ")";
			
			else
				respuesta = "Lo sentimos, no existe una celula en esta posicion";
		}catch (ArrayIndexOutOfBoundsException e){
			throw new ErrorFueraDeSuperficie("FAIL: Los valores de la posicion no se encuentran dentro de la superficie");
		}
		return respuesta;
		}

	@Override
	public Comando parsea(String[] datos) throws ErrorFormatoNumerico {
		Comando comando;
		if(datos[0].equalsIgnoreCase("eliminarcelula") && (datos.length == 3)){
			try{
				int f = Integer.parseInt(datos[1]);
				int c = Integer.parseInt(datos[2]);
				comando = new EliminarCelula(f,c);
			}catch(NumberFormatException e){
				throw new ErrorFormatoNumerico("FAIL: Los parametros del comando no son numeros");
			}
		}
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return ("ELIMINARCELULA F C: Elimina la celula en el mundo en la posicion (F ,C)." + System.getProperty("line.separator"));
	}

}
