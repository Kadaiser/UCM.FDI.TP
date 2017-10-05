package es.ucm.fdi.tp.pr2.control.excepciones;

public class ErrorComandoDesconocido extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4104034323419736787L;

	public ErrorComandoDesconocido(){
		super();
	}
	
	public ErrorComandoDesconocido(String message){
		super(message);
	}
}
