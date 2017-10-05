package es.ucm.fdi.tp.pr2.logica.mundos;


import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.celula.CelulaSimple;
import es.ucm.fdi.tp.pr2.celula.ParserCelulas;
import es.ucm.fdi.tp.pr2.logica.Mundo;

public class MundoSimple extends Mundo {
	
	private final Celula[] permitidas = {	new CelulaSimple(0,0)
										};
	private int numCelulasIniciales;
	
/**
 * Constructor de la clase MundoSimple que inicializa un mundo con una superficie dada a partir de dos atributos (filas y columnas)
 * @param f valor de numero de filas de la superficie del mundo.
 * @param c valor de numero de columnas de la superficie del mundo.
 * @param n valor del numero de celulasSimples que se desean instanciar
 */
	public MundoSimple(int f, int c, int n){
		super(f, c);
		this.numCelulasIniciales = n;
		this.celulas = new ParserCelulas(permitidas);
		this.inicializaMundo();
	}
/**
 * Constructor generico de la clase MundoSimple, se genera un mundo con una superficie de longitud definida	 sin celulas	
 * @param f valor de numero de filas de la superficie del mundo.
 * @param c valor de numero de columnas de la superficie del mundo.
 */
	public MundoSimple(int f, int c){
		super(f, c);
		this.numCelulasIniciales = 0;
		this.celulas = new ParserCelulas(permitidas);
	}


@Override
	public void inicializaMundo() {
		int i = 0;
		while(i < this.numCelulasIniciales){
			int f = generarPosicion(filasMundo());
			int c = generarPosicion(columnasMundo());

			if(this.superficie.llenarCelda(f, c, new CelulaSimple()))
				i++;
		}
	}

@Override
	public String celulasDisponibles() {
		return "Este mundo solo genera celulas simples";
	}

@Override
	public Celula insertarcelula(String Solicitud) {
		return this.celulas.parseaCelula("1");
	}

@Override
	public String identidadMundo() {
		return "simple";
	}
@Override
	public Mundo parsea(String[] identidad) {
			Mundo mundo;
			if(identidad[0].equalsIgnoreCase(this.identidadMundo())){
				int f = Integer.parseInt(identidad[1]);
				int c = Integer.parseInt(identidad[2]);
				mundo = new MundoSimple(f, c);
			}
			else
				mundo = null;	
		return mundo;
		}
	
	//Fin de la clase
}
