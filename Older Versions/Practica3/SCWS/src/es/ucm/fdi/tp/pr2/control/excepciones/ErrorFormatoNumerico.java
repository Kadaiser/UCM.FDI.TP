package es.ucm.fdi.tp.pr2.control.excepciones;

public class ErrorFormatoNumerico extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2388099494966053129L;

	public ErrorFormatoNumerico(){
		super();
	}
	
	public ErrorFormatoNumerico(String message){
		super(message);
	}
}
