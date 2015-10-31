package logica;

import java.util.ArrayDeque;
import java.util.Scanner;

import persistencia.Proceso;
import presentacion.PanelSimulacion;

public class Procesador implements Runnable {

	public static final Object lock = Lock.lock;

	private ArrayDeque<Proceso> procesos;
	private Proceso proceso;
	
	private PanelSimulacion panelSimulacion;

	public Procesador(ArrayDeque<Proceso> procesos) {
		this.procesos = procesos;
	}

	public void bloquearProceso(){
		proceso.bloquear();
	}

	public void desbloquearProceso(){
		proceso.desbloquear();
	}
	
	public boolean terminarProcesoPorError(){
		if(proceso.isBloqueado()){
			proceso.setTerminadoPorError(true);
			System.out.println(proceso.toString()+" terminado por error de E/S");
			return true;
		}
		else{
			System.out.println("El "+proceso.toString()+" no fue terminado por error de E/S, este no se encuentra bloqueado");
			return false;
		}
	}

	@Override
	public void run() {
		while(!procesos.isEmpty()){
			synchronized (lock) {
				proceso=procesos.remove();
				while(proceso.getTiempoEjecutado()<proceso.getTiempoEjecucion()&&!proceso.isTerminadoPorError()) {
					System.out.println(proceso.toString());
					try {
						panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
						proceso.sumarTiempoEjecutado(1);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(proceso.isBloqueado()){
						try {
							new Thread(new Bloqueo(proceso, 5, panelSimulacion)).start();;
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
				System.out.println(proceso.toString()+" terminado");
			}
		}
	}

	public void setPanelSimulacion(PanelSimulacion panelSimulacion) {
		this.panelSimulacion = panelSimulacion;
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
		while(opcion!=3){
			opcion=s.nextInt();
			if(opcion==1){
				procesador.bloquearProceso();
			}
			if(opcion==2){
				procesador.terminarProcesoPorError();
			}
		}
		s.close();
	}
}
