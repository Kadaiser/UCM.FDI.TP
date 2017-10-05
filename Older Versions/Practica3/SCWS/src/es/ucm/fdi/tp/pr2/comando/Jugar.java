package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.control.Controlador;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorIniciarPartida;
import es.ucm.fdi.tp.pr2.logica.Mundo;
import es.ucm.fdi.tp.pr2.logica.mundos.MundoComplejo;
import es.ucm.fdi.tp.pr2.logica.mundos.MundoCompleto;
import es.ucm.fdi.tp.pr2.logica.mundos.MundoSimple;

public class Jugar implements Comando {
	private Mundo mundo;
	
	public Jugar(Mundo mundo){
		this.mundo = mundo;
	}
	
	@Override
	public String ejecuta(Controlador controlador) {
		controlador.juega(this.mundo);
		return "Se ha generado un nuevo mundo";
	}

	@Override
	/*Seria interesante valorar el parseo de mundos en funcion de datos[1] de tal manera que puedan implementarse
	 * los distintos mundos, y ser estos los que procesan el array de string con el correspondiente mundo instanciado
	 *  Dicho parseo quizas podria ofrecer nuevos esquemas de diseño que generen un programa mas escalable en el cual
	 *  adherir nuevas funciones y comportamientos haga mas indipendientes a las distitnas clases del funcionamiento
	 *  general del programa
	 */
	public Comando parsea(String[] datos) throws ErrorFormatoNumerico, ErrorIniciarPartida{
		Comando comando = null;
		if(datos[0].equalsIgnoreCase("jugar")){
			try{
				int[] atributos = this.validarAtributos(datos);
				
				if(datos[1].equalsIgnoreCase("simple") && (datos.length == 5)){
					comando = new Jugar(new MundoSimple(atributos[0], atributos[1], atributos[2]));
				}
				else if(datos[1].equalsIgnoreCase("complejo") && (datos.length == 6)){
					comando = new Jugar(new MundoComplejo(atributos[0], atributos[1], atributos[2], atributos[3]));
				}
				else if(datos[1].equalsIgnoreCase("completo") && (datos.length == 7)){
					comando = new Jugar(new MundoCompleto(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4]));
				}
				else{
					throw new ErrorIniciarPartida("FAIL: el tipo de mundo solicitado no existe o el numero de atributos no es correcto");
				}

			}catch(NumberFormatException e){
				throw new ErrorFormatoNumerico("FAIL: Algunos de los parametros numericos del comando no son numeros");
			}
		}
	return comando;
	}

	
/**
 * Se procesa el string de datos para verificar los distintos atributos numericos y la coherencia que se espera de los mismos
 * 
 * @param datos
 * @return un array de enteros con los valores verificados necesarios para construir un mundo
 * @throws NumberFormatException
 * @throws ErrorIniciarPartida
 * @throws ErrorFormatoNumerico 
 */
	private int[] validarAtributos(String[] datos) throws ErrorIniciarPartida, ErrorFormatoNumerico{
		int[] atributos = new int[datos.length - 2]; // descartamos las dos primeras palabras "jugar" y "tipomundo"
		int totalCelulas = 0;
		int parametro = 0;
		
		try{
			atributos[0] = Integer.parseInt(datos[2]);
			atributos[1] = Integer.parseInt(datos[3]);
			int maxCelulas = atributos[0] * atributos[1];
			for(int i = 4; i < datos.length; i++){ // empezamos en el indice 4 ya que las anteriores son "jugar"[0], "mundo"[1], "filasMundo"[2], "ColumnasMundo"[3]
				parametro =  Integer.parseInt(datos[i]);
				if(parametro < 0){
					parametro = 0;
					//throw new ErrorIniciarPartida("FAIL: alguno de los parametros es negativo, se instanciara a 0 automaticamentne")
				}
				atributos[i-2] = Integer.parseInt(datos[i]);
				totalCelulas += parametro;
			}
			if(maxCelulas < totalCelulas)
				throw new ErrorIniciarPartida("FAIL: el numero de celulas indicadas no cabe en esta superficie");	
			
		}catch (NumberFormatException e){
			throw new ErrorFormatoNumerico("FAIL: Algunos de los parametros numericos del comando no son correctos");
		}catch (ArrayIndexOutOfBoundsException e){
			throw new ErrorIniciarPartida("FAIL: El comando no posee suficientes parametros para iniciarse");
		}
	return atributos;
	}

	
	
	@Override
	public String textoAyuda() {
		return "JUGAR SIMPLE [NUM_FILAS] [NUM_COLUMNAS] [NUM_SIMPLES]" + System.getProperty("line.separator")
				+ "JUGAR COMPLEJO [NUM_FILAS] [NUM_COLUMNAS] [NUM_SIMPLES] [NUM_COMPLEJAS]" + System.getProperty("line.separator")
				+ "JUGAR COMLETO [NUM_FILAS] [NUM_COLUMNAS] [NUM_SIMPLES] [NUM_COMPLEJAS] [NUM_SUICIDAS]" + System.getProperty("line.separator")
				+ "Se cambia a una nueva partida de un mundo definido por el usuario" + System.getProperty("line.separator");
	}

}
