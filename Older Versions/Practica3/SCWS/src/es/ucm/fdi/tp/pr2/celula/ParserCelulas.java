package es.ucm.fdi.tp.pr2.celula;

import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorLecturaFichero;

public class ParserCelulas {
		
	private Celula[] celulasPermitidas;
	
/**
 * Constructor de la clase ParserCelulas que define en cada mundo la limitación de tipos celulares.
 * @param celulas es el array de tipos celulares permitidos para el mundo instancaido en el controlador
 */
	public ParserCelulas(Celula[] celulas){
		this.celulasPermitidas = celulas;
	}
	
/**
 * Se entrega un string con la identificacion de la celula que ha de instanciarse
 * @param datos corresponde al string co nel identificador de la celula que se desea parsear
 * @return una celula que corresponda con el parametro entergado, null para el resto de casos
 */
	public Celula parseaCelula (String datos){
		boolean seguir = true;
		int i = 0;
		Celula celula = null;
	
		while (i < celulasPermitidas.length && seguir){
			if(datos != null){
				celula = celulasPermitidas[i].parsea(datos);
				if(celula != null)
					seguir = false;
				else
					i++;
			}
		}
		return celula;
	}

/**
 * Se entrega un array de Strings con la identificacion de la celula que ha de instanciarse
 * @param datos un array de strings con los valores de los atributos para la celula que se desea instanciar
 * @return celula instanciadad con sus atributos segun los valores del parametro del metodo
 * @throws ErrorFormatoNumerico si alguno de los atributos necesarios para instanciar la celula en los parametros de entrada no fuesen  parseables a un formato no numérico (NumberFormatException)
 * @throws ErrorLecturaFichero si alguna linea del ficehro de carga no corresponde con ningun tipo de celula conocido para el mundo cargado.
 */
	public Celula cargaCelula (String[] datos) throws ErrorFormatoNumerico, ErrorLecturaFichero{
		boolean seguir = true;
		int i = 0;
		Celula celula = null;
	
		while (i < celulasPermitidas.length && seguir){
			if(datos != null){
				celula = celulasPermitidas[i].cargar(datos);
				if(celula != null)
					seguir = false;
				else
					i++;
			}
		}
		if(celula == null)
			throw new ErrorLecturaFichero(" el tipo de celula no es reconocido");
		return celula;
	}
}
