package com.tcmp.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Portfolio {

	public static void process(String cpty, String fecha, String route){
			
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection("jdbc:sqlserver://qro-aleuatsqlfn.dombase.net;databaseName=Kustom;encrypt=true;trustServerCertificate=true","kplus","B4s3#Kp1uN");
			
			System.out.println(cpty);
			System.out.println(fecha);
			//Connection con = DriverManager.getConnection("jdbc:sqlserver://172.24.19.102;instanceName=KPLUS_DBO;databaseName=kplus;encrypt=true;trustServerCertificate=true","abenitez","Finastra_123");
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("cpty", cpty);
			map.put("fecha", fecha);
			map.put("route", route);
			
			String outputPath = String.format("%s\\Portafolio_%s_%s.pdf",route,cpty,fecha);
			String reportPath = "C:\\INTERFACES\\Portafolio\\rsc\\reports\\A4_Portafolio.jrxml";
			JasperReport jr = JasperCompileManager.compileReport(reportPath);
			JasperPrint jp = JasperFillManager.fillReport(jr, map, con);
			JasperViewer.viewReport(jp, false);
			JasperPrintManager.printReport(jp, false);
			JasperExportManager.exportReportToPdfFile(jp,outputPath);
			
			con.close();
	       
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
