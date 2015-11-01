package run;

import java.util.ArrayDeque;

import persistencia.Proceso;
import presentacion.VentanaPrincipal;

public class Run {
	
	private ArrayDeque<Proceso> procesos;
	
	private VentanaPrincipal ventanaPrincipal;
	
	public VentanaPrincipal getVentanaPrincipal() {
		if(ventanaPrincipal==null)
			ventanaPrincipal=new VentanaPrincipal(this);
		return ventanaPrincipal;
	}

	public ArrayDeque<Proceso> getProcesos() {
		if(procesos==null)
			procesos=new ArrayDeque<>();
		return procesos;
	}

	public static void main(String[] args) {
		Run r=new Run();
		r.getVentanaPrincipal();
	}

}
