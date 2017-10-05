package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.control.Controlador;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFueraDeSuperficie;

public class CrearCelula implements ComandoTriple {
	private int fila;
	private int columna;
	
	public CrearCelula(int fila, int columna){
		this.fila = fila;
		this.columna = columna;
	}

	@Override
	public String ejecuta(Controlador controlador) throws ErrorFueraDeSuperficie{
		String respuesta = "";
		Celula celula = controlador.pedirCelula();
		if(celula != null){
			try{
				
				if(controlador.crearCelula(this.fila, this.columna, celula))
					respuesta = "Se ha generado una celula nueva en la posicion (" + this.fila + "," + this.columna + ")";
				else
					respuesta = "Lo sentimos, ya existe una celula en esta posicion";
				
			}catch (ArrayIndexOutOfBoundsException e){
				throw new ErrorFueraDeSuperficie("FAIL: Los valores de la posicion no se encuentran dentro de la superficie");
			}
		}
		else
			respuesta = "La celula solicitada no es valida";
		return respuesta;
	}

	
	
	
	
	@Override
	public Comando parsea(String[] datos)throws ErrorFormatoNumerico{
		Comando comando = null;
		if(datos[0].equalsIgnoreCase("crearcelula") && (datos.length == 3)){
			try{
				int f = Integer.parseInt(datos[1]);
				int c = Integer.parseInt(datos[2]);
				comando = new CrearCelula(f,c);
			}catch(NumberFormatException e){
				throw new ErrorFormatoNumerico("FAIL: Los parametros del comando no son numeros");
			}
		}
	return comando;
	}

	@Override
	public String textoAyuda() {

		return ("CREARCELULA F C: Crea una celula definida por el usuario para la superficie en la posicion (F,C)." + System.getProperty("line.separator"));
	}

}
