package presentacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import logica.Procesador;

public class PanelSimulacion extends JPanel implements ActionListener {

	private static final long serialVersionUID = 3910820851696640914L;

	private JLabel lblEjecucuion;
	private JProgressBar prTiempoejecucion;
	private JLabel lblBloqueado;
	private JLabel lblListo;
	private JLabel lblTerminado;
	private JProgressBar prTiempoBloqueo;
	private JLabel lblTiempoRestante;
	private JLabel lblTiempoBloqueo;
	private JButton btnES;
	private JSpinner txtTiempoBloqueo;
	private JButton btnErrorES;
	private JLabel lblProceso;
	private Graphics g;

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
		SpinnerModel sm = new SpinnerNumberModel(0, 0, 50, 1);
		txtTiempoBloqueo = new JSpinner (sm);
		lblProceso=new JLabel("Proceso: ");

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
		add(txtTiempoBloqueo);
		add(lblProceso);

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
		btnES.setBounds (260, 15, 65, 25);
		txtTiempoBloqueo.setBounds (325, 15, 35, 25);
		btnErrorES.setBounds (260, 40, 100, 25);
		lblProceso.setBounds(6, 270, 100, 25);

		g=this.getGraphics();
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

	public void ActualizarProceso(String nombre){
		lblProceso.setText("Proceso: "+nombre);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(procesador!=null)
			if(procesador.getProceso()!=null){
				if(procesador.getProceso().isListo())
					pintarListo(g);
				if(!procesador.getProceso().isListo()&&!procesador.getProceso().isBloqueado())
					pintarEjecucion(g);
				if(procesador.getProceso().isBloqueado())
					pintarBloqueado(g);
			}
	}

	private void pintarListo(Graphics g) {
		Color myColour = new Color(0, 255, 102, 128);
		g.setColor(myColour);
		g.fillRect(5, 180, 43, 35);
	}

	private void pintarEjecucion(Graphics g) {
		Color myColour = new Color(0, 0, 255, 128);
		g.setColor(myColour);
		g.fillRect(70, 20, 120, 65);
	}

	private void pintarBloqueado(Graphics g) {
		Color myColour = new Color(255, 0, 0, 128);
		g.setColor(myColour);
		g.fillRect(175, 185, 120, 60);
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
