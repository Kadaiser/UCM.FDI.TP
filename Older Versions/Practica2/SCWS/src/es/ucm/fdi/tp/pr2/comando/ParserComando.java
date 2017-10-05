package es.ucm.fdi.tp.pr2.comando;

public final class ParserComando{
	
	private static Comando[] comandos = {
									new Ayuda(),
									new Vaciar(),
									new Paso(),
									new Iniciar(),
									new Salir(),
									new CrearCelulaSimple(0,0),
									new CrearCelulaCompleja(0,0),
									new EliminarCelula(0,0),
									new CrearCelulaSuicida(0,0)
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
	 */
	public static Comando parseaComando (String[] datos){
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
		return comando;
	}	
}
