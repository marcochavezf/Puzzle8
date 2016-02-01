package puzzle.hill_climbing;
import java.util.Queue;


public class Main {
	
	public static void imprimirSol(Queue<Pasos> solucion){
		Pasos p=null;
		int i=1;
		System.out.println("imprimiendo solucion\n");
		while(solucion.size()>0){
			p=solucion.poll();
			System.out.println("paso "+i+":");
			i++;
			System.out.print("numero "+p.numero+" movimiento "+p.movimiento+"\n");
		}
	}
/*
 * en el main esta la matriz inicial, como empieza el juego ese.
 * das tambien la matriz final. el arbolito es de donde salen los
 * 2 metodos heuristicos esos. uno es resolver y el otro es resolver2.
 * resolver es el metodo que ya teniamos y todo... ahora hace todo,
 * solo tuve un leve backfire... por corregir ciertos errores en el
 * final, se crearon 10 a 20 nodos extras... no es taaaaaan malo...
 * sigue furulando.
 * el resolver2 es el metodo que trabaja con otra heuristica rara...
 * como no me dijeron que onda... vilmente hice una heuristica que 
 * suma las heuristica de todos los numeros. digase es la sumatoria de
 * nuestra heuristica para su pocision final... pero este no llega u.u
 * me temo que estropee eso... por el lado bueno, ya son 2 heuristicas
 * no?
 * en fin... ya quedo mi parte... hablenme cualquier cosa... aunque ya
 * esta completo creo... 2 heuristicas... una no sirve muy bien y la 
 * otra sirve bien. todo en hillclimbing... en fin... ya quedo creo.
 * dududu, espero sirva todo de maravilla XD
 */
	public static void main(String[]args){
		int[][] inicial = {{2, 3, 7},{4, 6, 5},{1, 8, 0}};
		int[][] fin = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		Arbolito dududu = new Arbolito(inicial, fin);
		//Queue<Pasos> solucion=dududu.resolver(dududu.inicio);
		Queue<Pasos> solucion=dududu.resolver2(dududu.inicio);
		imprimirSol(solucion);
	}
	
}
