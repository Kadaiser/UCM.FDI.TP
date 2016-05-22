package es.ucm.fdi.tp.Practica6.server.response;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

public class ErrorResponse implements Response {


	private static final long serialVersionUID = 1L;
	private String msg;

	/**
	 * <b>ErrorResponse</b>
	 * <p>Contructor de la respuesta enviada en el canal de comunicacion</p>
	 * @param msg with the error
	 */
	public ErrorResponse(String msg) {
		this.msg = msg;
	}

	@Override
	public void run(GameObserver o) {
		o.onError(msg);

	}

}
