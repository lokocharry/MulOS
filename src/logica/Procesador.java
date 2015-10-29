package logica;

import java.util.ArrayDeque;
import java.util.Scanner;

import persistencia.Proceso;

public class Procesador implements Runnable {

	private static final class Lock { }
	private final Object lock = new Lock();

	private ArrayDeque<Proceso> procesos;
	private Proceso proceso;

	public Procesador(ArrayDeque<Proceso> procesos) {
		this.procesos = procesos;
	}

	public void bloquearProceso(){
		proceso.bloquear();
	}

	public void desbloquearProceso(){
		proceso.desbloquear();
	}

	@Override
	public void run() {
		while(!procesos.isEmpty()){
			synchronized (lock) {
				proceso=procesos.remove();
				for (int i = 0; i < proceso.getTiempoEjecucion(); i++) {
					System.out.println(proceso.toString());
					try {
						proceso.sumarTiempo(1);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(proceso.isBloqueado()){
						try {
							new Thread(new Bloqueo(proceso, 5)).start();;
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Proceso p1=new Proceso(1, "P1", 10, 100);
		Proceso p2=new Proceso(2, "P2", 10, 100);

		ArrayDeque<Proceso> procesos=new ArrayDeque<>();
		procesos.add(p1);
		procesos.add(p2);

		Procesador procesador=new Procesador(procesos);

		new Thread(procesador).start();

		Scanner s=new Scanner(System.in);
		int opcion=0;
		while(opcion!=2){
			opcion=s.nextInt();
			if(opcion==1){
				procesador.bloquearProceso();
			}
		}
		s.close();
	}
}
