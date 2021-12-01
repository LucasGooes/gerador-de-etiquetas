package com.teste.comunicationwithprinter;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.teste.comunicationwithprinter.domain.Cliente;
import com.teste.comunicationwithprinter.repository.ClienteRepository;

@SpringBootApplication
public class ComunicationWithPrinterApplication implements CommandLineRunner {
	
	@Autowired
	private ClienteRepository repo;
	
	public static void main(String[] args) {
		SpringApplication.run(ComunicationWithPrinterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Cliente cli = new Cliente("Lucas", 5000.00, "Programador");
		Cliente cli2 = new Cliente("Maria", 10000.00, "Gerente");
		Cliente cli3 = new Cliente("Lucia", 2000.00, "Atendente");
		Cliente cli4 = new Cliente("Joao", 1800.00, "Motorista");
	
		repo.saveAll(Arrays.asList(cli, cli2, cli3, cli4));
	}

}
