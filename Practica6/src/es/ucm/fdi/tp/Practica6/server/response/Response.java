package es.ucm.fdi.tp.Practica6.server.response;

import java.io.Serializable;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

/**
 * @author Kadaiser
 *
 */
public interface Response extends Serializable{
	public void run(GameObserver o);
	

}
