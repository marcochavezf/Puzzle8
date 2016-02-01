package puzzle.bfs;
/**
 *
 * @author Native Lord
 */

public class OrdenarEstado implements Comparable {

   private int prioridadEstado;
   private String descripcionEstado;

   public OrdenarEstado(int prioridad, String descripcion) {
      this.prioridadEstado = prioridad;
      this.descripcionEstado = descripcion;
   }
@Override
   public String toString() {
      return descripcionEstado;
   }
   public int compareTo(Object obj) {
      OrdenarEstado otro = (OrdenarEstado) obj;

      if (prioridadEstado < otro.prioridadEstado) {
         return -1;
      }
      if (prioridadEstado > otro.prioridadEstado) {
         return 1;
      }
      return 0;
   }
}