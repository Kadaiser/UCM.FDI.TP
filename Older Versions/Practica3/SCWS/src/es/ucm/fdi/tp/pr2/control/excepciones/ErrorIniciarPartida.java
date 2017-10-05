package es.ucm.fdi.tp.pr2.control.excepciones;


/* Esta anotación se utiliza para evitar un error en tiempo de compilación al implementar 
 * la interfaz java.io.Serializable y no tiene el campo llamado serialVersionUI.
 */
public class ErrorIniciarPartida extends Exception{
	
	/**
	 * Si un clase serializable no explícitamente declarar un serialVersionUID, 
	 * entonces el la serialización de tiempo de ejecución se calculará un por defecto serialVersionUID 
	 * valor para que clase basada sobre los diversos aspectos de la clase, como se describe en el Java(TM) 
	 * de la Serialización de Objetos La especificación. Sin embargo, se recomienda que todos los serializable clases 
	 * de declarar de forma explícita serialVersionUID los valores, ya que la por defecto serialVersionUID computación 
	 * es altamente sensible a los detalles de clase que puede variar dependiendo del compilador las implementaciones, 
	 * y por lo tanto puede resultar en inesperados InvalidClassExceptions durante la deserialización
	 */
	private static final long serialVersionUID = 3039026077601842017L;

	public ErrorIniciarPartida(){
		super();
	}
	
	public ErrorIniciarPartida(String message){
		super(message);
	}

}
