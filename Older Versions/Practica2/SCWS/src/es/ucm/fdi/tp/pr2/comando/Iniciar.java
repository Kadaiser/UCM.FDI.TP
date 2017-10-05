package es.ucm.fdi.tp.pr2.comando;

import java.util.Random;

import es.ucm.fdi.tp.pr2.celula.Celula;
import es.ucm.fdi.tp.pr2.celula.CelulaCompleja;
import es.ucm.fdi.tp.pr2.celula.CelulaSimple;
import es.ucm.fdi.tp.pr2.logica.Mundo;


public class Iniciar implements ComandoSimple{
	
	/*
	 *Hasta que llegue la implementacion, se consideran ordenados desde 1 a n, los distintos tipos de celulas
	 *Por ello, la inclusion de nuevos tipos, en caso de que se quieran autogenerar en la superficie, seran
	 *incluidos en la lista (o eliminados en caso de querer descartarlos), y se ampliara el metodo "generarCelulasAleatorias" para sus correspondientes casos.
	 */
	private static Celula[] tipoCelular = { new CelulaSimple(),
											new CelulaCompleja()
											};
	
	private static final int NUM_CELULAS_SIMPLES = 6;
	private static final int NUM_CELULAS_COMPLEJAS = 2;
	
	@Override
	public String ejecuta(Mundo mundo){
		mundo.vaciarMundo();
		
		this.generarCelulasAleatorias(NUM_CELULAS_SIMPLES, mundo, 1);
		this.generarCelulasAleatorias(NUM_CELULAS_COMPLEJAS, mundo, 2);
		return "Iniciando el mundo...";
	}

/**
* Genera un random a modulo n;
* @param n es un valor entero positivo
* @return un valor entero positivo aleatorio a modulo con el parametro n.
*/
	private static int generarPosicion(int n){
		Random aleatorio = new Random();
		return aleatorio.nextInt(n);
	}
	
/**
 * Se generan N celulas en posiciones libres aleatorias de la superficie
 * @param N numero de celulas a generar
 * @param mundo sobre el que se van  a generaar esas celulas
 * @param tipoCelular identifica con un entero que tipo de celula se quiere genarar
 */
	public void generarCelulasAleatorias(int N, Mundo mundo, int tipoCelular){
		int i = 0;
		do{
			int f = generarPosicion(mundo.filasMundo());
			int c = generarPosicion(mundo.columnasMundo());
			
			switch(tipoCelular){
				case 1:
					if(mundo.crearCelula(f, c, new CelulaSimple()))
						i++;
					break;
				case 2:
					if(mundo.crearCelula(f, c, new CelulaCompleja()))
						i++;
					break;
				default:
						i++;
					break;
			}
		}while(i < N);
	}

	
	@Override
	public Comando parsea(String[] datos) {
		Comando comando;
		if(datos[0].equalsIgnoreCase("iniciar") && (datos.length == 1))
			comando = new Iniciar();
		else
			comando = null;	
	return comando;
	}

	@Override
	public String textoAyuda() {
		return ("INICIAR: Elimina las celulas de la superficie e introduce nuevas celulas aleatorias." + '\n');
	}

}
