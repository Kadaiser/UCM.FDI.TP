package es.ucm.fdi.tp.pr2.comando;

import es.ucm.fdi.tp.pr2.control.excepciones.ErrorComandoDesconocido;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorFormatoNumerico;
import es.ucm.fdi.tp.pr2.control.excepciones.ErrorIniciarPartida;

public final class ParserComando{
	
	private static Comando[] comandos = {
									new Ayuda(),
									new Vaciar(),
									new Paso(),
									new Iniciar(),
									new Salir(),
									new CrearCelula(0,0),
									new EliminarCelula(0,0),
									new Guardar(null),
									new Cargar(null),
									new Jugar(null)
									};
	/**
	 * Metodo para construir un String que aglomera las descripciones de todos los comandos implementados
	 * @return String a partir de un StringBuilder
	 */
	public static String AyudaComandos(){
		StringBuilder builder = new StringBuilder();
		insertarSeparador(builder);
		for (int i = 0; i < comandos.length; i++)
			builder.append(comandos[i].textoAyuda());
		insertarSeparador(builder);
		return builder.toString();
	}
	
	/**
	 * Funcion que genera una cadena de caracteres de guiones a modo de separador
	 * su uso es recomedable en funciones de salida por pantalla con salto de linea includio. ("printnl")
	 * @return String de 70 caracteres '_'.
	 */
		
		private static void insertarSeparador(StringBuilder builder){
			for(int i = 0; i < 70; i++)
				builder.append('_');
			builder.append('\n');
		}
	
	/**
	 * Metodo que procesa la cadena de caracteres del usuario por consola, instanciando
	 * uno de los posibles comandos si existen las coincidencias correspondientes
	 * @param datos es array de String con comandos de una o varias palabras
	 * @return comando instanciado o NULL si el array de strings no correespondia a ningun comando
	 * @throws ErrorIniciarPartida si alguno de los parametros para el comando jugar no eran correctos
	 * @throws ErrorFormatoNumerico  si alguno de los parametros del comando que se esperaban parseables a enteros no lo era
	 * @throws ErrorComandoDesconocido si no se encuentra ningun comando coincidente con el parametro entregado
	 */
	public static Comando parseaComando (String[] datos)throws ErrorIniciarPartida, ErrorFormatoNumerico, ErrorComandoDesconocido{
		boolean seguir = true;
		int i = 0;
		Comando comando = null;
	
		while (i < comandos.length && seguir){
			if(datos != null && datos.length >= 1){
				comando = comandos[i].parsea(datos);
				if(comando != null)
					seguir = false;
				else
					i++;
			}
		}
		if(comando == null)
			throw new ErrorComandoDesconocido("FAIL: Comando desconocido, escriba " + '"'+ "ayuda" + '"' +" para ver la lista de comandos disponibles");
		return comando;
	}	
}
