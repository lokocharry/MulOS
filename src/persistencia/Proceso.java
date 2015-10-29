package persistencia;

public class Proceso {
	
	private int PID;
	private String nombre;
	private int tiempoEjecucion;
	private int tamanio;
	private int tiempoEjecutado;
	private boolean bloqueado;
	private boolean terminadoPorError;
	
	/**
	 * Constructor por defecto
	 */
	public Proceso() {
		super();
		bloqueado=false;
	}

	/**
	 * Contrustor con parametros de inicialización de un proceso
	 * @param pID Identificador unico del proceso
	 * @param nombre Nombre del Proceso
	 * @param tiempoEjecucion Tiempo de ejecución del proceso
	 * @param tamanio Tamaño del proceso
	 */
	public Proceso(int pID, String nombre, int tiempoEjecucion, int tamanio) {
		PID = pID;
		this.nombre = nombre;
		this.tiempoEjecucion = tiempoEjecucion;
		this.tamanio = tamanio;
		bloqueado=false;
	}

	/**
	 * Añade un intervalo de tiempo al tiempo que lleva ejecutado el proceso
	 * @param tiempo
	 */
	public void sumarTiempoEjecutado(int tiempo){
		tiempoEjecutado+=tiempo;
	}
	
	/**
	 * Devuelve el tiempo que resta para le terminación del proceso
	 * @return Tiempo que resta para que el proceso termine
	 */
	public int tiempoRestante(){
		return tiempoEjecucion-tiempoEjecutado;
	}
	
	/**
	 * Vambia el estado de bloqueo del proceso a true
	 */
	public void bloquear(){
		bloqueado=true;
	}
	
	/**
	 * Vambia el estado de bloqueo del proceso a false
	 */
	public void desbloquear(){
		bloqueado=false;
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

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public boolean isTerminadoPorError() {
		return terminadoPorError;
	}

	public void setTerminadoPorError(boolean terminadoPorError) {
		this.terminadoPorError = terminadoPorError;
	}

	@Override
	public String toString() {
		return "Proceso [PID=" + PID + ", nombre=" + nombre + ", tiempoRestante=" + tiempoRestante() + "]";
	}

}
