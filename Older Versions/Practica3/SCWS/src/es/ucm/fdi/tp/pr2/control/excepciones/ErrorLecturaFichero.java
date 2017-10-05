package es.ucm.fdi.tp.pr2.control.excepciones;

public class ErrorLecturaFichero extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4597445639735249291L;
	
	private int numLinea;
	private String mensaje;

	
	public ErrorLecturaFichero(int numLinea){
		this.numLinea = numLinea;
	}
	
	public ErrorLecturaFichero(int numLinea, String message){
		this.numLinea = numLinea;
		this.mensaje = message;
	}
	
	public ErrorLecturaFichero(String message){
		this.mensaje = message;
	}
	
	public int getNumLinea(){
		return this.numLinea;
	}
	public String getMensaje(){
		return this.mensaje;
	}

}
