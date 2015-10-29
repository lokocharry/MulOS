package logica;

public class Lock {
	
	private static final class Bloqueo { }
	public static final Object lock = new Bloqueo();

}
