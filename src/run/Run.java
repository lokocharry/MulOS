package run;

import java.util.ArrayDeque;

import persistencia.Proceso;
import presentacion.VentanaPrincipal;

/**
 * Clase main de la aplicación
 * @author Jorge Lozano
 *
 */
public class Run {
	/**
	 * Cola de procesos a ejecutar
	 */
	private ArrayDeque<Proceso> procesos;
	/**
	 * Instancia de VentanaPrincipal
	 */
	private VentanaPrincipal ventanaPrincipal;
	
	/**
	 * Singleton de VentanaPrincipal
	 * @return VetanaPrincipal Instanciado
	 */
	public VentanaPrincipal getVentanaPrincipal() {
		if(ventanaPrincipal==null)
			ventanaPrincipal=new VentanaPrincipal(this);
		return ventanaPrincipal;
	}

	/**
	 * Singleton de Procesos
	 * @return Procesos instanciado
	 */
	public ArrayDeque<Proceso> getProcesos() {
		if(procesos==null)
			procesos=new ArrayDeque<>();
		return procesos;
	}
	
	/**
	 * Metot mai de la aplicación sin parametros de configuración
	 * @param args vacio
	 */
	public static void main(String[] args) {
		Run r=new Run();
		r.getVentanaPrincipal();
	}

}
