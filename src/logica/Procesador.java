package logica;

import java.util.ArrayDeque;

import persistencia.Proceso;
import presentacion.PanelSimulacion;
import presentacion.VentanaPrincipal;

/**
 * Clase que realiza la simulaci�n del procesamiento de los procesos
 * @author Jorge Lozano
 *
 */
public class Procesador implements Runnable {

	/**
	 * Supervisor para la sincronizaci�n de hilos
	 */
	public static final Object lock = Lock.lock;

	/**
	 * Cola de procesos a ejecutar
	 */
	private ArrayDeque<Proceso> procesos;
	/**
	 * Proceso que se encuentra en ejecuci�n
	 */
	private Proceso proceso;
	/**
	 * Instancia de VentanaPrincipal
	 */
	private VentanaPrincipal ventanaPrincipal;
	/**
	 * Instancia de PanelSimulaci�n
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
	 * Bloquea el proceso que est� en ejecuci�n
	 */
	public void bloquearProceso(){
		proceso.bloquear();
	}
	/**
	 * Desbloquea el proceso que est� en ejecuci�n
	 */
	public void desbloquearProceso(){
		proceso.desbloquear();
	}
	/**
	 * Termina el proceso que se encuentra en bloqueo por error de E/S
	 * @return true si se termin� o false en caso contrario
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
	 * Ejecuaci�n de los procesos
	 */
	@Override
	public void run() {
		while(true){
			if(!procesos.isEmpty()){
				proceso=procesos.remove(); //Se obtiene el proceso que se encuentra en la cabeza de la cola
				panelSimulacion.ActualizarProceso(proceso.getNombre()); //Actualiza el proceso en el PanelSimulacion
				panelSimulacion.repaint(); //Repintar el panel
				try {
					Thread.sleep(1500); //Esperar 1.5 segundos
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				while(proceso.getTiempoEjecutado()<proceso.getTiempoEjecucion()&&!proceso.isTerminadoPorError()) { //Mientras el proceso tenga timpo de ejecuci�n y no se haya terminado por E/S
					proceso.setListo(false);// Se cambia el estado de listo del proceso
					try {
						panelSimulacion.repaint();
						panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
						Thread.sleep(1000); //Esperar 1 segundo
						proceso.sumarTiempoEjecutado(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(proceso.isBloqueado()){ //Se verifica si el proceso est� bloqueado
						new Thread(new Bloqueo(proceso, (int)panelSimulacion.getTxtTiempoBloqueo().getValue(), panelSimulacion, this)).start(); //Si inicia el hilo de bloqueo
						break;
					}
					panelSimulacion.repaint();
				}
				if(proceso.tiempoRestante()==0||proceso.isTerminadoPorError()){
					panelSimulacion.actualizarTiempoEjecucion(proceso.getTiempoEjecucion(), proceso.tiempoRestante());
					System.out.println(proceso.toString()+" terminado");
					ventanaPrincipal.agregarProcesoTerminado(proceso); //Se agraga el proceso a Procesos Terminados
					ventanaPrincipal.eliminarProcesoATabla(); //Se elimina el proceso de la lista de Procesos
					panelSimulacion.repaint();
				}
			}
		}
	}

	public void setPanelSimulacion(PanelSimulacion panelSimulacion) {
		this.panelSimulacion = panelSimulacion;
	}

	public Proceso getProceso() {
		return proceso;
	}
	public ArrayDeque<Proceso> getProcesos() {
		return procesos;
	}

}
