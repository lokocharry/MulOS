package presentacion;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

import logica.Procesador;

public class PanelSimulacion extends JPanel implements ActionListener {
	
	private JLabel lblEjecucuion;
    private JProgressBar prTiempoejecucion;
    private JLabel lblBloqueado;
    private JLabel lblListo;
    private JLabel lblTerminado;
    private JProgressBar prTiempoBloqueo;
    private JLabel lblTiempoRestante;
    private JLabel lblTiempoBloqueo;
    private JButton btnES;
    private JButton btnErrorES;
    
    private Procesador procesador;

    public PanelSimulacion(Procesador procesador) {
    	this.procesador=procesador;
    	
        //construct components
        lblEjecucuion = new JLabel ("En Ejecución");
        prTiempoejecucion = new JProgressBar();
        lblBloqueado = new JLabel ("Bloqueado");
        lblListo = new JLabel ("Listo");
        lblTerminado = new JLabel ("Terminado");
        prTiempoBloqueo = new JProgressBar();
        lblTiempoRestante = new JLabel ("Tiempo restante: ");
        lblTiempoBloqueo = new JLabel ("Tiempo restante: ");
        btnES = new JButton ("E/S");
        btnES.setActionCommand("E/S");
        btnES.addActionListener(this);
        btnErrorES = new JButton ("Error E/S");
        btnErrorES.setActionCommand("Error E/S");
        btnErrorES.addActionListener(this);

        //set components properties
        prTiempoejecucion.setOrientation (JSlider.HORIZONTAL);
        prTiempoBloqueo.setOrientation (JSlider.HORIZONTAL);
        

        //adjust size and set layout
        setPreferredSize (new Dimension (377, 275));
        setLayout (null);

        //add components
        add (lblEjecucuion);
        add (prTiempoejecucion);
        add (lblBloqueado);
        add (lblListo);
        add (lblTerminado);
        add (prTiempoBloqueo);
        add (lblTiempoRestante);
        add (lblTiempoBloqueo);
        add (btnES);
        add (btnErrorES);

        //set component bounds (only needed by Absolute Positioning)
        lblEjecucuion.setBounds (90, 25, 75, 25);
        prTiempoejecucion.setBounds (75, 45, 110, 15);
        lblBloqueado.setBounds (200, 185, 65, 25);
        lblListo.setBounds (10, 185, 35, 25);
        lblTerminado.setBounds (305, 115, 65, 20);
        prTiempoBloqueo.setBounds (180, 210, 110, 15);
        prTiempoBloqueo.setMinimum(0);
        prTiempoejecucion.setMinimum(0);
        lblTiempoRestante.setBounds (75, 60, 200, 25);
        lblTiempoBloqueo.setBounds (180, 220, 200, 25);
        btnES.setBounds (260, 15, 100, 25);
        btnErrorES.setBounds (260, 40, 100, 25);
    }

    public void actualizarTiempoEjecucion(int tiempoEjecucion, int tiempoRestante){
    	prTiempoejecucion.setMaximum(tiempoEjecucion);
    	prTiempoejecucion.setValue(tiempoRestante);
    	lblTiempoRestante.setText("Tiempo restante: "+tiempoRestante);
    }
    
    public void actualizarTiempoBloqueo(int tiempoBloqueo, int tiempoRestante){
    	prTiempoBloqueo.setMaximum(tiempoBloqueo);
    	prTiempoBloqueo.setValue(tiempoRestante);
    	lblTiempoBloqueo.setText("Tiempo restante: "+tiempoRestante);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		switch (command) {
		case "E/S":
			procesador.bloquearProceso();
			break;
		case "Error E/S":
			procesador.terminarProcesoPorError();
			break;
		}
	}

	public void setProcesador(Procesador procesador) {
		this.procesador = procesador;
	}

}
