package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import persistencia.Proceso;

public class VentanaProcesosTerminados extends JInternalFrame {

	private static final long serialVersionUID = 8019368955160401830L;
	
	private JTable tablaProcesos;
	private DefaultTableModel modelo;
	private JScrollPane panel;
	
	public VentanaProcesosTerminados() {
		setTitle("Procesos Terminados");
		modelo=new DefaultTableModel();
		tablaProcesos=new JTable();
		tablaProcesos.setModel(modelo);
		modelo.addColumn("PID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Tamaño");
        modelo.addColumn("Tiempo");
        modelo.addColumn("Tiempo Ejecutado");
        modelo.addColumn("Error E/S");
        
        panel=new JScrollPane();
        panel.setViewportView(tablaProcesos);
        
        add(panel);
        setVisible(true);
	}
	
	public void agregarProceso(Proceso p){
		modelo.addRow(p.toVector());
	}
}
