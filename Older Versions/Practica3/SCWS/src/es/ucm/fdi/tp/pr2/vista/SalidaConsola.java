package es.ucm.fdi.tp.pr2.vista;

public final class SalidaConsola {
	/* SOLO PARA CONSOLAS UNIX
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	*/
	
	
	public static void consolaEstandar(String mensaje){
		System.out.println(mensaje);
		
	}
	
	public static void consolaError (String mensaje){
		System.out.println(mensaje);
	}

}
