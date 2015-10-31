package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import persistencia.Proceso;
import run.Run;

public class DialogoCrearProceso extends JDialog implements ActionListener {

	/**
	 * Serial generado
	 */
	private static final long serialVersionUID = 4851497763659249766L;
	
	private int pID=1;
	
	private JButton btnAgregar;
	private JButton btnCancelar;
	private JLabel lblNombre;
	private JLabel lblTiempo;
	private JLabel lblTamanio;
	private JLabel lblPrioridad;
	private JTextField txtNombre;
	private JSpinner txtTiempo;
	private JSpinner txtTamanio;
	private JSpinner txtPrioridad;
	
	private VentanaPrincipal ventanaPrincipal;
	
	private Run run;

	public DialogoCrearProceso(Run run, VentanaPrincipal ventanaPrincipal) {
		this.run=run;
		this.ventanaPrincipal=ventanaPrincipal;
		setModal(true);
		
		//construct components
		btnAgregar = new JButton ("Agregar");
		btnCancelar = new JButton ("Cancelar");
		lblNombre = new JLabel ("Nombre");
		lblTiempo = new JLabel ("Tiempo de ejecución");
		lblTamanio = new JLabel ("Tamaño");
		lblPrioridad = new JLabel ("Prioridad");
		txtNombre = new JTextField (20);
		
		SpinnerModel sm1 = new SpinnerNumberModel(0, 0, 50, 1);
		SpinnerModel sm2 = new SpinnerNumberModel(0, 0, 50, 1);
		SpinnerModel sm3 = new SpinnerNumberModel(0, 0, 50, 1);
		
		txtTiempo = new JSpinner (sm1);
		txtTamanio = new JSpinner (sm2);
		txtPrioridad = new JSpinner (sm3);

		//set components properties
		txtNombre.setToolTipText ("Nombre del Proceo");
		
		btnAgregar.addActionListener(this);
		btnCancelar.addActionListener(this);

		//adjust size and set layout
		setTitle("Nuevo Proceso");
		setSize(271, 240);
		setResizable(false);
		setLayout (null);
		setLocationRelativeTo(null);

		//add components
		add (btnAgregar);
		add (btnCancelar);
		add (lblNombre);
		add (lblTiempo);
		add (lblTamanio);
		add (lblPrioridad);
		add (txtNombre);
		add (txtTiempo);
		add (txtTamanio);
		add (txtPrioridad);

		//set component bounds (only needed by Absolute Positioning)
		btnAgregar.setBounds (15, 175, 100, 25);
		btnCancelar.setBounds (145, 175, 100, 25);
		lblNombre.setBounds (5, 15, 100, 25);
		lblTiempo.setBounds (5, 50, 135, 25);
		lblTamanio.setBounds (5, 85, 100, 25);
		lblPrioridad.setBounds (5, 120, 100, 25);
		txtNombre.setBounds (155, 15, 100, 25);
		txtTiempo.setBounds (155, 50, 100, 25);
		txtTamanio.setBounds (155, 85, 100, 25);
		txtPrioridad.setBounds (155, 120, 100, 25);
		
		this.getRootPane().registerKeyboardAction(e -> {
			btnCancelar.doClick();
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.getRootPane().registerKeyboardAction(e -> {
		    btnAgregar.doClick();
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boton=(JButton)e.getSource();
		if(boton==btnAgregar){
			Proceso p=new Proceso(pID, txtNombre.getText(), (int)txtTiempo.getValue(), (int)txtTamanio.getValue());
			pID++;
			run.getProcesos().add(p);
			ventanaPrincipal.agregarProcesoATabla(p);
			
			txtNombre.setText("");
			txtTamanio.setValue(0);
			txtTiempo.setValue(0);
			txtPrioridad.setValue(0);
			JOptionPane.showMessageDialog(null, "Proceso Agregado");
		}
		if(boton==btnCancelar){
			txtNombre.setText("");
			txtTamanio.setValue(0);
			txtTiempo.setValue(0);
			txtPrioridad.setValue(0);
			this.setVisible(false);
		}
	}

}
