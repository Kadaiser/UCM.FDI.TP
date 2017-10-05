package es.ucm.fdi.tp.pr2.logica.mundos;

import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.celula.CelulaCompleja;
import es.ucm.fdi.tp.pr2.celula.CelulaSimple;
import es.ucm.fdi.tp.pr2.celula.ParserCelulas;
import es.ucm.fdi.tp.pr2.logica.Mundo;

public class MundoComplejo extends Mundo{
	private static final Celula[] permitidas = {	new CelulaSimple(0,0),
													new CelulaCompleja(0),
													};
	
	private int celulasSimplesIniciales;
	private int celulasComplejasIniciales;
	
/**
 * Constructor de la clase Mundo que inicializa un mundo con una superficie dada a partir de tres valores (filas y columnas)
 * y genera una superficie en base a esos parametros.
 * @param f valor de numero de filas de la superficie del mundo.
 * @param c valor de numero de columnas de la superficie del mundo.
 * @param n numero de celulas simples  a generar al inicio
 * @param m numero de celulas complejas a generar al inicio
 */
	public MundoComplejo(int f, int c, int n, int m){
		super(f, c);
		this.celulasSimplesIniciales = n;
		this.celulasComplejasIniciales = m;
		this.celulas = new ParserCelulas(permitidas);
		this.inicializaMundo();
	}
	
/**
 * Constructor generico de la clase MundoCompleto, se genera un mundo con una superficie de longitud vacia		
 */
	MundoComplejo(int f, int c){
		super(f, c);
		this.celulasSimplesIniciales = 0;
		this.celulasComplejasIniciales = 0;
		this.celulas = new ParserCelulas(permitidas);
	}

@Override
	public void inicializaMundo() {
		this.generarCelulasAleatorias(this.celulasSimplesIniciales, 1);
		this.generarCelulasAleatorias(this.celulasComplejasIniciales, 2);
	}

/**
 * Se generan N celulas en posiciones libres aleatorias de la superficie
 * @param N numero de celulas a generar
 * @param tipoCelular identifica con un entero que tipo de celula se quiere genarar
 */
	private void generarCelulasAleatorias(int N, int tipoCelular){
		int i = 0;
		while(i < N){
			int f = generarPosicion(this.filasMundo());
			int c = generarPosicion(this.columnasMundo());
			
			switch(tipoCelular){
				case 1:
					if(this.superficie.llenarCelda(f, c, new CelulaSimple()))
						i++;
					break;
				case 2:
					if(this.superficie.llenarCelda(f, c, new CelulaCompleja()))
						i++;
					break;
				default:
						i++;
					break;
			}
		}
	}

@Override
public String identidadMundo() {
	return "complejo";
}

@Override
public String celulasDisponibles() {
	return "El mundo complejo aloja celulas SIMPLES[1] y COMPLEJAS[2], escriba el numero deseado: ";
}

public Celula insertarcelula(String solicitud){
	return this.celulas.parseaCelula(solicitud);
}

@Override
public Mundo parsea(String[] identidad) {
		Mundo mundo;
		if(identidad[0].equalsIgnoreCase(this.identidadMundo())){
			int f = Integer.parseInt(identidad[1]);
			int c = Integer.parseInt(identidad[2]);
			mundo = new MundoComplejo(f, c);
		}
		else
			mundo = null;	
	return mundo;
	}

//Fin de la clase
}