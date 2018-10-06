package client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.mysql.jdbc.Connection;

import dbConnection.DbUtil;
import dbConnection.MySQLConnUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

public class MainPage extends JFrame {

	private JFrame frmMainPage;

	public MainPage() {
		initialize();

	}

	private void initialize() {
		new JFrame();
		setSize(5000, 5000);
		setTitle("Main Page");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		JButton btnNewButton_1 = new JButton("Medicines");
		btnNewButton_1.setForeground(new Color(204, 51, 255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				MedicinePage a = new MedicinePage();

			}
		});
		btnNewButton_1.setBounds(1036, 405, 184, 151);
		getContentPane().add(btnNewButton_1);
		JButton btnPatient = new JButton("Patient");
		btnPatient.setForeground(new Color(153, 51, 255));
		btnPatient.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);

				try {
					PatientPage a = new PatientPage();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnPatient.setBounds(751, 405, 184, 151);
		getContentPane().add(btnPatient);

	}
}
