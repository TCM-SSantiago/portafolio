package com.tcmp.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.core.io.Resource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import com.tcmp.process.Portfolio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;

@Service
@RestController
public class PortfolioController{
	
	@GetMapping(value = "portafolio")
	public ResponseEntity<byte[]> downloadInvoice(@RequestParam String cpty, @RequestParam String fecha) throws JRException, IOException, SQLException, ClassNotFoundException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Connection con = DriverManager.getConnection("jdbc:sqlserver://qro-aleprdsqlfn.dombase.net;databaseName=Kustom;encrypt=true;trustServerCertificate=true","kplus","Me3pulsk#b4nc0.");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://qro-aleuatsqlfn.dombase.net;databaseName=Kustom;encrypt=true;trustServerCertificate=true","kplus","B4s3#Kp1uN");
		
		//String reportPath = System.getProperty("user.home") + "\\Desktop\\Portafolio\\rsc\\reports\\A4_Portafolio.jrxml";
		//String outputPath = String.format("attachment; filename=Portafolio_%s_%s.pdf",cpty,fecha);
		map.put("cpty", cpty);
		map.put("fecha", fecha);
		
		InputStream is = getClass().getResourceAsStream("/static/A4_Portafolio_2.jrxml");
		JasperReport jr = JasperCompileManager.compileReport(is);
		JasperPrint jp = JasperFillManager.fillReport(jr, map, con);
		byte data[] = JasperExportManager.exportReportToPdf(jp);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		String outputPath = String.format("inline; filename=Portafolio_%s_%s.pdf",cpty,fecha);
		headers.add("Content-Disposition", outputPath);

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
		
		
	}
	
	@GetMapping(value = "xls")
	public ResponseEntity<byte[]> xls(@RequestParam String cpty, @RequestParam String fecha) throws JRException, IOException, SQLException, ClassNotFoundException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Connection con = DriverManager.getConnection("jdbc:sqlserver://qro-aleprdsqlfn.dombase.net;databaseName=Kustom;encrypt=true;trustServerCertificate=true","kplus","Me3pulsk#b4nc0.");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://qro-aleuatsqlfn.dombase.net;databaseName=Kustom;encrypt=true;trustServerCertificate=true","kplus","B4s3#Kp1uN");
		
		String outputPath = String.format("attachment; filename=Portafolio_%s_%s.xls",cpty,fecha);
		map.put("cpty", cpty);
		map.put("fecha", fecha);
	    InputStream is = getClass().getResourceAsStream("/static/A4_Portafolio_2.jrxml");
	    JasperReport jr = JasperCompileManager.compileReport(is);
	    JasperPrint jp = JasperFillManager.fillReport(jr, map, con);

	    // Exportar a Excel
	    JExcelApiExporter exporter = new JExcelApiExporter();
	    exporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jp);
	    exporter.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, new ByteArrayOutputStream()); // Puedes ajustar esto según tus necesidades
	    exporter.exportReport();

	    // Configurar la respuesta HTTP
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", outputPath);

	    return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(((ByteArrayOutputStream) exporter.getParameter(JExcelApiExporterParameter.OUTPUT_STREAM)).toByteArray());
	}
	
}
