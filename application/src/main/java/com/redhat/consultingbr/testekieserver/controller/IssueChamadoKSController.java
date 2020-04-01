package com.redhat.consultingbr.testekieserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.fortaleza.sepog.domain.IssueChamadoKSPessoa;

import com.redhat.consultingbr.testekieserver.service.IssueChamadoKSService;
import com.redhat.consultingbr.testekieserver.domain.Greeting;


import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class IssueChamadoKSController {
	
	@Autowired
	private IssueChamadoKSService issueChamadoKSService;
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@PostMapping("/runrules")
	public IssueChamadoKSPessoa processarFolha(@RequestBody IssueChamadoKSPessoa issueChamadoKSPessoa) {
		//curl -d '{"name":"Joseph","idade":5}' -H "Content-Type: application/json" -X POST http://localhost:8080/runrules
		
		return issueChamadoKSService.runRules(issueChamadoKSPessoa);
	}

	@RequestMapping("/")
	public IssueChamadoKSPessoa getRules() {
		System.out.println("PASSOU AQUI");

		IssueChamadoKSPessoa issueChamadoKSPessoa = new IssueChamadoKSPessoa();
		issueChamadoKSPessoa.setIdade(98);
		return issueChamadoKSPessoa;
	}

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

}
