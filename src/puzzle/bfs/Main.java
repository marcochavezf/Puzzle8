package puzzle.bfs;

import java.util.LinkedList;

/**
 *
 * @author Native Lord
 */
public class Main {

   public static void main(String[] args) {
      System.out.println("*****************");
      int[][] inicio = {{0, 4, 3}, {1, 2, 6}, {5, 7, 8}};
      int[][] meta = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

      System.out.println("BFS con Manhattan");
      BfsMan bfsMan = new BfsMan(inicio, meta);
      bfsMan.buscarSolucion();
      LinkedList numeros1 = new LinkedList();
      numeros1 = bfsMan.getTemp();
      bfsMan.imprimirLista(numeros1);
      System.out.println("");

      System.out.println("//////////////////////");
      System.out.println("");
      System.out.println("BFS con Posiciones fuera de lugar");
      BfsMis bfsMis = new BfsMis(inicio, meta);
      bfsMis.buscarSolucion();
      LinkedList numeros2 = new LinkedList();
      numeros2 = bfsMis.getTemp();
      bfsMis.imprimirLista(numeros2);
     
      System.out.println("*****************");

   }
}
