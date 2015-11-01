package logica;

import java.util.ArrayDeque;
import java.util.Scanner;

import persistencia.Proceso;
import presentacion.PanelSimulacion;
import presentacion.VentanaPrincipal;

public class Procesador implements Runnable {

	public static final Object lock = Lock.lock;

	private ArrayDeque<Proceso> procesos;
	private Proceso proceso;
	private VentanaPrincipal ventanaPrincipal;
	
	private PanelSimulacion panelSimulacion;

	public Procesador(ArrayDeque<Proceso> procesos, VentanaPrincipal ventanaPrincipal) {
		this.procesos = procesos;
		this.ventanaPrincipal=ventanaPrincipal;
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
			proceso.setListo(false);
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
				panelSimulacion.ActualizarProceso(proceso.getNombre());
				panelSimulacion.repaint();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				while(proceso.getTiempoEjecutado()<proceso.getTiempoEjecucion()&&!proceso.isTerminadoPorError()) {
					proceso.setListo(false);
					System.out.println(proceso.toString());
					try {
						panelSimulacion.repaint();
						panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
						Thread.sleep(1000);
						proceso.sumarTiempoEjecutado(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(proceso.isBloqueado()){
						try {
							new Thread(new Bloqueo(proceso, (int)panelSimulacion.getTxtTiempoBloqueo().getValue(), panelSimulacion)).start();
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
				System.out.println(proceso.toString()+" terminado");
				ventanaPrincipal.agregarProcesoTerminado(proceso);
				panelSimulacion.repaint();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				panelSimulacion.repaint();
			}
		}
		panelSimulacion.repaint();
	}

	public void setPanelSimulacion(PanelSimulacion panelSimulacion) {
		this.panelSimulacion = panelSimulacion;
	}

	public Proceso getProceso() {
		return proceso;
	}

}
