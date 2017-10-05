package es.ucm.fdi.tp.pr2.control.excepciones;

public class ErrorFueraDeSuperficie extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3099972870213724838L;

	public ErrorFueraDeSuperficie(){
		super();
	}
	
	public ErrorFueraDeSuperficie(String message){
		super(message);
	}

}