package logica;

import persistencia.Proceso;
import presentacion.PanelSimulacion;

public class Bloqueo implements Runnable {

	public static final Object lock = Lock.lock;

	private Proceso proceso;
	private int tiempo;
	
	private PanelSimulacion panelSimulacion;

	public Bloqueo(Proceso proceso, int tiempo, PanelSimulacion panelSimulacion) {
		this.panelSimulacion=panelSimulacion;
		this.proceso = proceso;
		this.tiempo = tiempo;
	}

	@Override
	public void run() {
		synchronized (lock) {
			for (int i = tiempo; i >0; i--) {
				panelSimulacion.actualizarTiempoBloqueo(tiempo, i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(proceso.isTerminadoPorError())
					break;
			}
			panelSimulacion.actualizarTiempoBloqueo(tiempo, 0);
			proceso.desbloquear();
			lock.notify();
		}
	}

}
