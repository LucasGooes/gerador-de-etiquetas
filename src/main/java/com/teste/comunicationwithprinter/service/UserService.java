package com.teste.comunicationwithprinter.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.comunicationwithprinter.dao.UserDaoImpl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class UserService {
	
	@Autowired
	private UserDaoImpl userDao;
	
	public JasperPrint exportPdfFile() throws IOException, JRException {
		return userDao.exportPdfFile();
	}
	
	public void printReport(JasperPrint jasperPrint, String selectedPrinter) throws JRException {
		userDao.printReport(jasperPrint, selectedPrinter);
	}

}
