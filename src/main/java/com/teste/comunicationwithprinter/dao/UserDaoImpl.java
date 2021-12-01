package com.teste.comunicationwithprinter.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.teste.comunicationwithprinter.domain.Cliente;
import com.teste.comunicationwithprinter.service.ClienteService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

@Transactional
@Repository
public class UserDaoImpl {
	
		@Autowired
	private ClienteService service;
	
	public JasperPrint exportPdfFile() throws IOException, JRException {
		File file = ResourceUtils.getFile("classpath:rpt_cliente.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		Cliente cli = service.find(2);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", cli.getId());
		parameters.put("funcao", cli.getFuncao());
		parameters.put("nome", cli.getNome());
		parameters.put("salario", cli.getSalario());
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(print, "path");
		return print;
	}
	
	public void printReport(JasperPrint jasperPrint, String selectedPrinter) throws JRException {
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		MediaPrintableArea area2 = new MediaPrintableArea(0, 0, 74, 27, Size2DSyntax.MM);
		
		printRequestAttributeSet.add(area2);	
		printRequestAttributeSet.add(Chromaticity.MONOCHROME);
		//printRequestAttributeSet.add(new Copies(1));
		printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));
		
		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
		configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
		configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
		configuration.setDisplayPageDialog(false);
		configuration.setDisplayPrintDialog(false);
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setConfiguration(configuration);
		
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		
		
		PrintService selectedService = null;
		if (services.length != 0 || services != null) {
			for (PrintService service: services) {
				String existingPrinter = service.getName();
				if (existingPrinter.equals(selectedPrinter)) {
					selectedService = service;
				}
			}
		}
		
		if(selectedService != null) {
			exporter.exportReport();
		} else {
			System.out.println("You did not set the printer");
		}
		
	}

}
