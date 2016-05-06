package es.ucm.fdi.tp.Practica6.server.response;

import java.io.Serializable;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

public interface Response extends Serializable{
	public void rum(GameObserver o);
	

}
