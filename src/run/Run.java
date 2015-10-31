package run;

import java.util.ArrayDeque;

import persistencia.Proceso;

public class Run {
	
	private ArrayDeque<Proceso> procesos;
	
	public ArrayDeque<Proceso> getProcesos() {
		if(procesos==null)
			procesos=new ArrayDeque<>();
		return procesos;
	}

	public static void main(String[] args) {
		
	}

}
