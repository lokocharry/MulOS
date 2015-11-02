package persistencia;

import java.util.Vector;

/**
 * Representación de un Proceso con sus atributos
 * @author Jorge Lozano
 *
 */
public class Proceso {
	
	private int PID;
	private String nombre;
	private int tiempoEjecucion;
	private int tamanio;
	private int tiempoEjecutado;
	private boolean listo=true;
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
	 * Constructor con parametros de inicialización de un proceso
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
	 * Cambia el estado de bloqueo del proceso a false
	 */
	public void desbloquear(){
		bloqueado=false;
		listo=true;
	}
	
	/**
	 * Convierte la instancia del Proceso a un objeto Vector 
	 * @return Bojeto Vector del Proceso
	 */
	public Vector<Object> toVector(){
		Vector<Object> vector=new Vector<>();
		vector.add(PID);
		vector.add(nombre);
		vector.add(tamanio);
		vector.add(tiempoEjecucion);
		vector.add(tiempoEjecutado);
		vector.add(terminadoPorError);
		return vector;
	}
	
	/**
	 * 
	 * @return Identificador de proceso
	 */
	public int getPID() {
		return PID;
	}

	/**
	 * Asignar PID
	 * @param pID
	 */
	public void setPID(int pID) {
		PID = pID;
	}

	/**
	 * 
	 * @return Nombre del Proceso
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asignar Nombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return Tiempo de ejecución
	 */
	public int getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	/**
	 * Asirnar Timpo de ejecución
	 * @param tiempoEjecucion
	 */
	public void setTiempoEjecucion(int tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	/**
	 * 
	 * @return Tamaño del proceso
	 */
	public int getTamanio() {
		return tamanio;
	}

	/**
	 * Asignar tamaño
	 * @param tamanio
	 */
	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	/**
	 * 
	 * @return Obtener tiempo ejecutado
	 */
	public int getTiempoEjecutado() {
		return tiempoEjecutado;
	}

	/**
	 * Asirnar tiempo ejecutado
	 * @param tiempoEjecutado
	 */
	public void setTiempoEjecutado(int tiempoEjecutado) {
		this.tiempoEjecutado = tiempoEjecutado;
	}

	/**
	 * 
	 * @return True o False
	 */
	public boolean isBloqueado() {
		return bloqueado;
	}

	/**
	 * Asignar estado de bloqueo
	 * @param bloqueado
	 */
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	/**
	 * 
	 * @return True o False
	 */
	public boolean isTerminadoPorError() {
		return terminadoPorError;
	}
	
	/**
	 * Terminar proceso por error de E/S
	 * @param terminadoPorError
	 */
	public void setTerminadoPorError(boolean terminadoPorError) {
		this.terminadoPorError = terminadoPorError;
	}

	/**
	 * 
	 * @return True o False
	 */
	public boolean isListo() {
		return listo;
	}

	/**
	 * Asirnar estado de Listo al Proceso
	 * @param listo
	 */
	public void setListo(boolean listo) {
		this.listo = listo;
	}

	/**
	 * Representación del objeto Proceso
	 */
	@Override
	public String toString() {
		return "Proceso [PID=" + PID + ", nombre=" + nombre + ", tiempoRestante=" + tiempoRestante() + "]";
	}

}
