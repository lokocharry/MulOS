package logica;

import persistencia.Proceso;

public class Bloqueo implements Runnable {

	public static final Object lock = Lock.lock;

	private Proceso proceso;
	private int tiempo;

	public Bloqueo(Proceso proceso, int tiempo) {
		this.proceso = proceso;
		this.tiempo = tiempo;
	}

	@Override
	public void run() {
		synchronized (lock) {
			for (int i = tiempo; i >0; i--) {
				System.out.println(proceso.toString()+ "Tiempo de bloqueo restante: "+i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(proceso.isTerminadoPorError())
					break;
			}
			proceso.desbloquear();
			lock.notify();
		}
	}

}
