package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import run.Run;


public class VentanaPrincipal extends JFrame implements ActionListener {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5608573059964824778L;
	
	private JMenuBar menubar;
    private DialogoCrearProceso crearProceso;
    
    private Run run;

    public VentanaPrincipal(Run run) {
    	this.run=run;
    	crearProceso=new DialogoCrearProceso(this.run, this);
    	
        //construct preComponents
        JMenu fileMenu = new JMenu ("Archivo");
        JMenuItem agregar_procesosItem = new JMenuItem ("Agregar procesos");
        agregar_procesosItem.setActionCommand("agregarProcesos");
        fileMenu.add (agregar_procesosItem);
        JMenuItem salirItem = new JMenuItem ("Salir");
        salirItem.setActionCommand("salir");
        fileMenu.add (salirItem);
        JMenu helpMenu = new JMenu ("Ayuda");
        JMenuItem aboutItem = new JMenuItem ("About");
        aboutItem.setActionCommand("about");
        helpMenu.add (aboutItem);

        //construct components
        menubar = new JMenuBar();
        menubar.add (fileMenu);
        menubar.add (helpMenu);

        //adjust size and set layout
        setSize(667, 366);
        setLayout (null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        //add components
        add (menubar);

        //set component bounds (only needed by Absolute Positioning)
        menubar.setBounds (0, 0, 670, 25);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		switch (command) {
		case "agregarProcesos":
			crearProceso.setVisible(true);
			break;
		case "salir":
			System.exit(0);
			break;
		case "about":
			System.out.println("Acerca de...");
			break;
		default:
			break;
		}
	}
}

