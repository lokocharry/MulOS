package logica;

/**
 * Clase supervisora de sincronización de hilos
 * @author Jorge Lozano
 *
 */
public class Lock {
	
	private static final class Bloqueo { }
	/**
	 * Objeto supervisor de sincronización de los hilos de ejecución y bloqueo
	 */
	public static final Object lock = new Bloqueo();

}
