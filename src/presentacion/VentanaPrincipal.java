package presentacion;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import logica.Procesador;
import persistencia.Proceso;
import run.Run;


public class VentanaPrincipal extends JFrame implements ActionListener {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5608573059964824778L;
	
	private JMenuBar menubar;
    private DialogoCrearProceso crearProceso;
    private JScrollPane panelProcesos;
    private PanelSimulacion panelSimulacion;
    private JTable tablaProcesos;
    private VentanaProcesosTerminados ventanaProcesosTerminados;
    
    private Run run;
    
    private Procesador procesador;

    public VentanaPrincipal(Run run) {
    	this.run=run;
    	crearProceso=new DialogoCrearProceso(this.run, this);
    	ventanaProcesosTerminados=new VentanaProcesosTerminados();
    	
        //construct preComponents
        JMenu fileMenu = new JMenu ("Archivo");
        JMenuItem agregar_procesosItem = new JMenuItem ("Agregar procesos");
        agregar_procesosItem.setActionCommand("agregarProcesos");
        agregar_procesosItem.setMnemonic(KeyEvent.VK_A);
        agregar_procesosItem.addActionListener(this);
        fileMenu.add (agregar_procesosItem);
        JMenuItem iniciarItem = new JMenuItem ("Iniciar simulación");
        iniciarItem.setActionCommand("iniciar");
        iniciarItem.addActionListener(this);
        fileMenu.add (iniciarItem);
        JMenuItem salirItem = new JMenuItem ("Salir");
        salirItem.setActionCommand("salir");
        salirItem.addActionListener(this);
        fileMenu.add (salirItem);
        JMenu helpMenu = new JMenu ("Ayuda");
        JMenuItem aboutItem = new JMenuItem ("About");
        aboutItem.setActionCommand("about");
        aboutItem.addActionListener(this);
        helpMenu.add (aboutItem);

        //construct components
        menubar = new JMenuBar();
        menubar.add (fileMenu);
        menubar.add (helpMenu);
        
        panelProcesos=new JScrollPane();
        panelProcesos.setBorder(BorderFactory.createTitledBorder("Procesos"));
        DefaultTableModel model= new DefaultTableModel();
        tablaProcesos = new JTable();
        tablaProcesos.setModel(model);
        model.addColumn("PID");
        model.addColumn("Nombre");
        model.addColumn("Tamaño");
        model.addColumn("Tiempo");
        tablaProcesos.setEnabled(false);
        tablaProcesos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        panelProcesos.setViewportView(tablaProcesos);
        
        panelSimulacion=new PanelSimulacion(procesador);
        panelSimulacion.setBorder(BorderFactory.createTitledBorder("Simulación"));

        //adjust size and set layout
        setSize(1170, 366);
        setLayout (new GridLayout());
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //add components
        setJMenuBar(menubar);
        add(panelProcesos);
        add(panelSimulacion);
        add(ventanaProcesosTerminados);

        //set component bounds (only needed by Absolute Positioning)
        menubar.setBounds (0, 0, 670, 25);
      
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void agregarProcesoATabla(Proceso p){
    	DefaultTableModel model=(DefaultTableModel) tablaProcesos.getModel();
    	model.addRow(p.toVector());
    }
    
    public void agregarProcesoTerminado(Proceso p){
    	ventanaProcesosTerminados.agregarProceso(p);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		switch (command) {
		case "agregarProcesos":
			crearProceso.setVisible(true);
			break;
		case "iniciar":
			procesador=new Procesador(run.getProcesos(), this);
			procesador.setPanelSimulacion(panelSimulacion);
			panelSimulacion.setProcesador(procesador);
			new Thread(procesador).start();
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

	public PanelSimulacion getPanelSimulacion() {
		return panelSimulacion;
	}
}

