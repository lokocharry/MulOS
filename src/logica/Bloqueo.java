package logica;

import persistencia.Proceso;
import presentacion.PanelSimulacion;

/**
 * Clase que implementa el bloqueo de procesos
 * @author Jorge Lozano
 *
 */
public class Bloqueo implements Runnable {

	/**
	 * Supervisor para la sincronización de hilos
	 */
	public static final Object lock = Lock.lock;

	/**
	 * Proceso que está bloqueado
	 */
	private Proceso proceso;
	
	/**
	 * Tiempo que dura el bloqueo
	 */
	private int tiempo;
	
	/**
	 * Instancia de PanelSimulacion para actualizar la simulación
	 */
	private PanelSimulacion panelSimulacion;
	
	/**
	 * Contructor con parametros
	 * @param proceso a bloquear
	 * @param tiempo de bloqueo
	 * @param panelSimulacion instancia de PanelSimulacion
	 */
	public Bloqueo(Proceso proceso, int tiempo, PanelSimulacion panelSimulacion) {
		this.panelSimulacion=panelSimulacion;
		this.proceso = proceso;
		this.tiempo = tiempo;
	}

	/**
	 * Proceso de bloqueo
	 */
	@Override
	public void run() {
		synchronized (lock) {
			for (int i = tiempo; i >0; i--) { //Bucle de tiempo de bloqueo
				panelSimulacion.actualizarTiempoBloqueo(tiempo, i); //Se actualiza el tiempo en el panel 
				panelSimulacion.repaint(); //Repintar el panel
				try {
					Thread.sleep(1000); //Esperar un segundo
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(proceso.isTerminadoPorError()) //Se verifica si el proceso se termina por error de E/S
					break;
			}
			panelSimulacion.actualizarTiempoBloqueo(tiempo, 0);
			proceso.desbloquear(); // Se dewbloquea el proceso
			panelSimulacion.repaint();
			lock.notify(); //Notificar al hilo de ejecución que continue
		}
	}

}
