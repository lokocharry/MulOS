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
	 * Instancia de Procesador
	 */
	private Procesador procesador;

	/**
	 * Contructor con parametros
	 * @param proceso a bloquear
	 * @param tiempo de bloqueo
	 * @param panelSimulacion instancia de PanelSimulacion
	 */
	public Bloqueo(Proceso proceso, int tiempo, PanelSimulacion panelSimulacion, Procesador procesador) {
		this.panelSimulacion=panelSimulacion;
		this.proceso = proceso;
		this.tiempo = tiempo;
		this.procesador=procesador;
	}

	/**
	 * Proceso de bloqueo
	 */
	@Override
	public void run() {

		for (int i = tiempo; i >0; i--) { //Bucle de tiempo de bloqueo
			panelSimulacion.actualizarTiempoBloqueo(tiempo, i, proceso.getNombre()); //Se actualiza el tiempo en el panel 
			panelSimulacion.repaint(); //Repintar el panel
			try {
				Thread.sleep(1000); //Esperar un segundo
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(proceso.isTerminadoPorError()) //Se verifica si el proceso se termina por error de E/S
				break;
		}
		procesador.getProcesos().add(proceso);
		panelSimulacion.actualizarTiempoBloqueo(tiempo, 0, "");
		proceso.desbloquear(); // Se dewbloquea el proceso
		panelSimulacion.repaint();
	}

}
