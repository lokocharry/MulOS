package persistencia;

public class Proceso {
	
	private int PID;
	private String nombre;
	private int tiempoEjecucion;
	private int tamanio;
	private int tiempoEjecutado;
	
	
	public Proceso() {
		super();
	}


	public Proceso(int pID, String nombre, int tiempoEjecucion, int tamanio) {
		super();
		PID = pID;
		this.nombre = nombre;
		this.tiempoEjecucion = tiempoEjecucion;
		this.tamanio = tamanio;
	}


	public int getPID() {
		return PID;
	}


	public void setPID(int pID) {
		PID = pID;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getTiempoEjecucion() {
		return tiempoEjecucion;
	}


	public void setTiempoEjecucion(int tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}


	public int getTamanio() {
		return tamanio;
	}


	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}


	public int getTiempoEjecutado() {
		return tiempoEjecutado;
	}


	public void setTiempoEjecutado(int tiempoEjecutado) {
		this.tiempoEjecutado = tiempoEjecutado;
	}

}
