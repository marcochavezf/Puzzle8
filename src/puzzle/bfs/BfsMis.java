package puzzle.bfs;
/**
 *
 * @author Native Lord
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class BfsMis {

   String estadoInicial = "";                    // estado inicial
   String estadoFinal = "";                      //estado final
   String nodoActual;
   LinkedList<Integer> numeros = new LinkedList<Integer>();
   LinkedList<Integer> temporal = new LinkedList<Integer>();
   PriorityQueue<OrdenarEstado> colaAbiertos;
   PriorityQueue<String> colaCerrados;
   Map<String, Integer> nivelProfundidad_map;    // contador de profundidad
   Map<String, String> registroAlPadre_map;
   int nodosGenerados = 0;                       //contador para nodos generados
   int nodosSinRepeticion = -1;                  //contador para estado unico
   int profundidad;                              //contador para limite de profundidad
   int heuristica;                               //valor de la heuristica
   boolean solucion = false;

   public BfsMis(int arrInicio[][], int arrMeta[][]) {
      colaAbiertos = new PriorityQueue<OrdenarEstado>();
      colaCerrados = new PriorityQueue<String>();
      nivelProfundidad_map = new HashMap<String, Integer>();
      registroAlPadre_map = new HashMap<String, String>();
      String inicio = matrizAString(arrInicio);
      String meta = matrizAString(arrMeta);
      this.estadoInicial = inicio;
      this.estadoFinal = meta;
      agregarNodoACola(inicio, null);
   }//fin del constructor
   public void buscarSolucion() {
      long tiempoActual = System.currentTimeMillis();
      while (!colaAbiertos.isEmpty()) {
         nodoActual = colaAbiertos.poll().toString();      // recuperamos el primero nodo
         if (nodoActual.equals(estadoFinal)) {             // verificamos si estado actual es igual al final
            solucion = true;
            imprimirSolucion(nodoActual);                  // imprimimos la solucion
            break;
         } else
            crearHijos(nodoActual);
      }
      if (solucion) {
         System.out.println("----------------------");
         System.out.println("Existe Solucion");
      } else {
         System.out.println("No se encontro la Solucion");
      }
      long tiempoFinal = System.currentTimeMillis() - tiempoActual;
      System.out.println("Tiempo de ejecucion " + tiempoFinal*0.001 + " milisegundos");
   }// fin de hacerBusqueda
   private void agregarNodoACola(String nodoHijo, String nodoPadre) {
      if (!nivelProfundidad_map.containsKey(nodoHijo)) {             // Verificamos que no haya nodos repetidos
         profundidad = (nodoPadre == null)
                 ? 0
                 : nivelProfundidad_map.get(nodoPadre) + 1;          // incrementamos en 1 la profundidad
         nodosSinRepeticion++;
         nivelProfundidad_map.put(nodoHijo, profundidad);
         //heuristica = calcularManhattan(nodoHijo, estadoFinal)+profundidad;      // calculamos la heuristica para el nuevo estado
         heuristica = fueraDePosicion(nodoHijo, estadoFinal);
         colaAbiertos.add(new OrdenarEstado(heuristica, nodoHijo));  //agregamos a la cola de prioridades // revisar como ordenar
         registroAlPadre_map.put(nodoHijo, nodoPadre);
      }
   }//fin de agregarNodoACola
   public void crearHijos(String nodoPadre){
      int cero = nodoPadre.indexOf("0");
      //String nodoHijo;
      //Movimiento a la izquierda
      while (cero != 2 && cero != 5 && cero != 8) {
         String nodoHijo =
            nodoPadre.substring(0, cero)
            + nodoPadre.substring(cero + 1, cero + 2) //numero en movimiento
            + "0"
            + nodoPadre.substring(cero + 2);
         if(nodoPadre.compareTo(nodoHijo) != 0){
            agregarNodoACola(nodoHijo, nodoPadre);
            nodosGenerados++;
         }
         break;
      }
      // movimiento a la derecha
      while (cero != 0 && cero != 3 && cero != 6) {
         String nodoHijo =
            nodoPadre.substring(0, cero - 1)
            + "0"
            + nodoPadre.substring(cero - 1, cero) //numero en movimiento
            + nodoPadre.substring(cero + 1);
         if(nodoPadre.compareTo(nodoHijo) != 0){
            agregarNodoACola(nodoHijo, nodoPadre);
            nodosGenerados++;
         }
         break;
      }
      //Movimiento arriba
      while (cero != 6 && cero != 7 && cero != 8) {
         String nodoHijo =
            nodoPadre.substring(0, cero)
            + nodoPadre.substring(cero + 3, cero + 4) //numero en movimiento
            + nodoPadre.substring(cero + 1, cero + 3)
            + "0"
            + nodoPadre.substring(cero + 4);
         if(nodoPadre.compareTo(nodoHijo) != 0){
            agregarNodoACola(nodoHijo, nodoPadre);
            nodosGenerados++;
         }
         break;
      }
      // movimiento abajo
      while (cero != 0 && cero != 1 && cero != 2) {
         String nodoHijo =
            nodoPadre.substring(0, cero - 3)
            + "0"
            + nodoPadre.substring(cero - 2, cero)
            + nodoPadre.substring(cero - 3, cero - 2) //numero en movimiento
            + nodoPadre.substring(cero + 1);
         if(nodoPadre.compareTo(nodoHijo) != 0){
            agregarNodoACola(nodoHijo, nodoPadre);
            nodosGenerados++;
         }
         break;
      }
   }
   public void imprimirSolucion(String nodoActual) {

	   String salida = "";

		if (solucion) {
			salida += "Se encontro la solucion en "
					+ nivelProfundidad_map.get(nodoActual) + " pasos \n";
			salida += "Nodos Generados: " + nodosGenerados + "\n";
			salida += "Nodos visitados: " + nodosSinRepeticion + "\n";
		} else {
			salida += "No se encontro la solucion!\n";
			salida += "Nodos Generados: " + nodosGenerados + "\n";
			salida += "Nodo visitados: " + nodosSinRepeticion + "\n";
		}

      String trazarCamino = nodoActual;

      while (trazarCamino != null) {
         System.out.println("********************************");
         System.out.println(trazarCamino + " en " + nivelProfundidad_map.get(trazarCamino));
         int cero = trazarCamino.indexOf("0");
         System.out.println("el cero esta en la posicion " + cero);
         System.out.println("Valor Heuristico: "+fueraDePosicion(trazarCamino,estadoFinal));
         for (int i = 0; i < 9; i++) {
            System.out.print(String.valueOf(trazarCamino.charAt(i)) + " "); // convertimos de Integer a String
            if ((i + 1) % 3 == 0) {
               System.out.println("");//salto de linea para visualizar string como matriz
            }
         }
         trazarCamino = registroAlPadre_map.get(trazarCamino);

         if (trazarCamino != null) {
            int xy = Integer.parseInt(String.valueOf(trazarCamino.charAt(cero)));
            System.out.println("se movio el numero " + xy);
            temporal.add(xy);
         }
      }
      numeros = temporal;
   // fin de imprimir Solucion
      System.out.println(salida);
   }
   public void imprimirLista(LinkedList<Integer> temp){
      Iterator<Integer> miIte = temp.iterator();
      while(miIte.hasNext()){
         System.out.println(miIte.next());
      }
   }
   public int fueraDePosicion (String siguienteNodo, String estadoMeta){
      int noCoincide = 0;
      for (int i=1;i<9;i++){
         if (siguienteNodo.indexOf(String.valueOf(i))!= estadoMeta.indexOf(String.valueOf(i))){
            noCoincide++;
         }
      }
      return noCoincide;
   }
   private String matrizAString(int arr[][]){
      StringBuilder cadena = new StringBuilder();
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr.length; j++) {
            cadena = cadena.append(arr[i][j]);
         }
      }
      return cadena.toString();
   }
   public LinkedList<Integer> getTemp() {
      return numeros;
   }
}