package es.ucm.fdi.tp.pr2.logica.mundos;

import es.ucm.fdi.tp.pr2.logica.Mundo;

public class ParserMundo {
	private static Mundo[] Mundos = {	new MundoSimple(0,0),
										new MundoComplejo(0,0),
										new MundoCompleto(0,0)
										};
	
	/**
	 * Metodo que procesa una cadena de caracteres , instanciando
	 * uno de los posibles mundos si existen las coincidencias correspondientes
	 * @param identidad del mundo
	 * @return mundo instanciado o NULL si el string no correspondia a ningun mundo
	 */
	public static Mundo parseaMundo (String[] identidad){
		boolean seguir = true;
		int i = 0;
		Mundo mundo = null;
	
		while (i < Mundos.length && seguir){
			if(identidad != null){
				mundo = Mundos[i].parsea(identidad);
				if(mundo != null)
					seguir = false;
				else
					i++;
			}
		}
		if(mundo == null)
			throw new NullPointerException();
		return mundo;
	}

}
