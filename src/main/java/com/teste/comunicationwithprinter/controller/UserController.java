package com.teste.comunicationwithprinter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.teste.comunicationwithprinter.domain.Printer;
import com.teste.comunicationwithprinter.domain.PrinterForm;
import com.teste.comunicationwithprinter.service.UserService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;


@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		PrinterForm printerForm = new PrinterForm();

		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		List<Printer> printers = new ArrayList<Printer>();
		for (PrintService service : services) {
			Printer printer = new Printer();
			printer.setPrinterName(service.getName());
			printers.add(printer);
		}
		
		model.addObject("listPrinters", printers);
		model.addObject("printerForm", printerForm);
		model.setViewName("home");
		return model;
	}
	
	 @RequestMapping(value = "/printDirect", method = RequestMethod.POST)
	 public void printDirect(@ModelAttribute("printerForm") PrinterForm printerForm, ModelAndView model) throws IOException, JRException {
		 System.out.println(printerForm.getPrinterName());
		 JasperPrint jasperPrint = null;
		 jasperPrint = userService.exportPdfFile();
		 userService.printReport(jasperPrint, printerForm.getPrinterName());
	 }

}
