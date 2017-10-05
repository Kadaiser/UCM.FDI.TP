package es.ucm.fdi.tp.pr2.logica.mundos;

import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.celula.CelulaCompleja;
import es.ucm.fdi.tp.pr2.celula.CelulaSimple;
import es.ucm.fdi.tp.pr2.celula.CelulaSuicida;
import es.ucm.fdi.tp.pr2.celula.ParserCelulas;
import es.ucm.fdi.tp.pr2.celula.Restos;
import es.ucm.fdi.tp.pr2.logica.Mundo;

public class MundoCompleto extends Mundo {
	
	private final Celula[] permitidas = {	new CelulaSimple(),
											new CelulaCompleja(),
											new CelulaSuicida(),
											new Restos()
											};
	
	private int celulasSimplesIniciales;
	private int celulasComplejasIniciales;
	private int celulasSuicidasIniciales;
	
/**
 * Constructor de la clase Mundo que inicializa un mundo con una superficie dada a partir de tres valores (filas y columnas)
 * y genera una superficie en base a esos parametros.
 * @param f valor de numero de filas de la superficie del mundo.
 * @param c valor de numero de columnas de la superficie del mundo.
 * @param simples numero de celulas  a generar al inicio
 * @param complejas numero de celulas  a generar al inicio
 * @param suicidas numero de celulas  a generar al inicio
 */
	public MundoCompleto(int f, int c, int simples, int complejas, int suicidas){
		super(f, c);
		this.celulasSimplesIniciales = simples;
		this.celulasComplejasIniciales = complejas;
		this.celulasSuicidasIniciales = suicidas;
		this.celulas = new ParserCelulas(permitidas);
		this.inicializaMundo();
	}

/**
 * Constructor generico de la clase MundoCompleto, se genera un mundo con una superficie de longitud definida		
 */
	MundoCompleto(int f, int c){
		super(f, c);
		this.celulasSimplesIniciales = 0;
		this.celulasComplejasIniciales = 0;
		this.celulasSuicidasIniciales = 0;
		this.celulas = new ParserCelulas(permitidas);
	}	
	
@Override
	public void inicializaMundo() {
		this.generarCelulasAleatorias(this.celulasSimplesIniciales, 1);
		this.generarCelulasAleatorias(this.celulasComplejasIniciales, 2);
		this.generarCelulasAleatorias(this.celulasSuicidasIniciales, 3);
		
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
				case 3:
					if(this.superficie.llenarCelda(f, c, new CelulaSuicida()))
						i++;
					break;
				default:
						i++;
					break;
			}
		}
	}
	
	@Override
	public String celulasDisponibles() {
		return "El mundo complejo aloja celulas SIMPLES[1], COMPLEJAS[2] y SUICIDAS[3] escriba el numero deseado: ";
	}

	@Override
	public Celula insertarcelula(String solicitud) {
		return this.celulas.parseaCelula(solicitud);
	}

	@Override
	public String identidadMundo() {
		return "completo";
	}

	@Override
	public Mundo parsea(String[] identidad) {
			Mundo mundo;
			if(identidad[0].equalsIgnoreCase(this.identidadMundo())){
				int f = Integer.parseInt(identidad[1]);
				int c = Integer.parseInt(identidad[2]);
				mundo = new MundoCompleto(f, c);
			}
			else
				mundo = null;	
		return mundo;
		}

}
