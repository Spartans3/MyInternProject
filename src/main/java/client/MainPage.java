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

public class MainPage {

	private JFrame frmMainPage;

	public MainPage() {
		initialize();

	}

	private void initialize() {
		frmMainPage = new JFrame();
		frmMainPage.setSize(618, 396);
		frmMainPage.setTitle("Main Page");
		frmMainPage.setVisible(true);
		frmMainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainPage.setLocationRelativeTo(null);
		frmMainPage.getContentPane().setLayout(null);

		JButton btnPatient = new JButton("Patient");
		btnPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMainPage.setVisible(false);

				try {
					PatientPage a = new PatientPage();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnPatient.setBounds(61, 68, 114, 25);
		frmMainPage.getContentPane().add(btnPatient);

		JButton btnNewButton = new JButton("Discharge");
		btnNewButton.setBounds(236, 68, 114, 25);
		frmMainPage.getContentPane().add(btnNewButton);

		JButton btnReport = new JButton("Report");
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn = null;
				try {
					conn = (Connection) MySQLConnUtils.getMySQLConnection();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {

					String report = "C:\\Users\\LENOVO\\Desktop\\report_files\\reportDeneme2.jrxml";
					JasperReport jr = JasperCompileManager.compileReport(report);
					Map<String, Object> parameters = new HashMap<String, Object>();
					JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
					JasperViewer.viewReport(jp);

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

			}
		});
		btnReport.setBounds(414, 68, 114, 25);
		frmMainPage.getContentPane().add(btnReport);

		JButton btnNewButton_1 = new JButton("Medicines");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMainPage.setVisible(false);
				MedicinePage a = new MedicinePage();

			}
		});
		btnNewButton_1.setBounds(61, 159, 114, 25);
		frmMainPage.getContentPane().add(btnNewButton_1);

	}
}
