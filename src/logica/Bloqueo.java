package logica;

import persistencia.Proceso;

public class Bloqueo implements Runnable {

	private static final class Lock { }
	private final Object lock = new Lock();

	private Proceso proceso;
	private int tiempo;

	public Bloqueo(Proceso proceso, int tiempo) {
		this.proceso = proceso;
		this.tiempo = tiempo;
	}

	@Override
	public void run() {
		synchronized (lock) {
			for (int i = tiempo; i >=0; i--) {
				System.out.println(proceso.toString()+ "Tiempo de bloquoe restante: "+i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			notifyAll();
		}
	}

}
