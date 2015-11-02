package logica;

import java.util.ArrayDeque;

import persistencia.Proceso;
import presentacion.PanelSimulacion;
import presentacion.VentanaPrincipal;

/**
 * Clase que realiza la simulación del procesamiento de los procesos
 * @author Jorge Lozano
 *
 */
public class Procesador implements Runnable {

	/**
	 * Supervisor para la sincronización de hilos
	 */
	public static final Object lock = Lock.lock;

	/**
	 * Cola de procesos a ejecutar
	 */
	private ArrayDeque<Proceso> procesos;
	/**
	 * Proceso que se encuentra en ejecución
	 */
	private Proceso proceso;
	/**
	 * Instancia de VentanaPrincipal
	 */
	private VentanaPrincipal ventanaPrincipal;
	/**
	 * Instancia de PanelSimulación
	 */
	private PanelSimulacion panelSimulacion;

	/**
	 * Constructor con parametros
	 * @param procesos Cola de procesos a ejecutar
	 * @param ventanaPrincipal Instancia de VentanaPrincipal
	 */
	public Procesador(ArrayDeque<Proceso> procesos, VentanaPrincipal ventanaPrincipal) {
		this.procesos = procesos;
		this.ventanaPrincipal=ventanaPrincipal;
	}
	/**
	 * Bloquea el proceso que está en ejecución
	 */
	public void bloquearProceso(){
		proceso.bloquear();
	}
	/**
	 * Desbloquea el proceso que está en ejecución
	 */
	public void desbloquearProceso(){
		proceso.desbloquear();
	}
	/**
	 * Termina el proceso que se encuentra en bloqueo por error de E/S
	 * @return true si se terminó o false en caso contrario
	 */
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

	/**
	 * Ejecuación de los procesos
	 */
	@Override
	public void run() {
		while(!procesos.isEmpty()){
			synchronized (lock) {
				proceso=procesos.remove(); //Se obtiene el proceso que se encuentra en la cabeza de la cola
				panelSimulacion.ActualizarProceso(proceso.getNombre()); //Actualiza el proceso en el PanelSimulacion
				panelSimulacion.repaint(); //Repintar el anel
				try {
					Thread.sleep(1500); //Esperar 1.5 segundos
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				while(proceso.getTiempoEjecutado()<proceso.getTiempoEjecucion()&&!proceso.isTerminadoPorError()) { //Mientras el proceso tenga timpo de ejecución y no se haya terminado por E/S
					proceso.setListo(false);// Se cambia el estado de listo del proceso
					try {
						panelSimulacion.repaint();
						panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
						Thread.sleep(1000); //Esperar 1 segundo
						proceso.sumarTiempoEjecutado(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(proceso.isBloqueado()){ //Se verifica si el proceso está bloqueado
						try {
							new Thread(new Bloqueo(proceso, (int)panelSimulacion.getTxtTiempoBloqueo().getValue(), panelSimulacion)).start(); //Si inicia el hilo de bloqueo
							lock.wait(); //Se pausa el hilo actual
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
				System.out.println(proceso.toString()+" terminado");
				ventanaPrincipal.agregarProcesoTerminado(proceso); //Se agraga el proceso a Procesos Terminados
				ventanaPrincipal.eliminarProcesoATabla(); //Se elimina el proceso de la lista de Procesos
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
