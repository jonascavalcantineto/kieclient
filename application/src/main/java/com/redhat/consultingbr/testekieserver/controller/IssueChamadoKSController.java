package com.redhat.consultingbr.testekieserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.consultingbr.testekieserver.service.IssueChamadoKSService;

import br.gov.ce.fortaleza.sepog.domain.IssueChamadoKSPessoa;

@RestController
@RequestMapping("/api")
public class IssueChamadoKSController {
	
	@Autowired
	private IssueChamadoKSService issueChamadoKSService;
	
	
	@PostMapping("/runrulesxml")
	public ResponseEntity<IssueChamadoKSPessoa>  processarFolha2(@RequestBody IssueChamadoKSPessoa issueChamadoKSPessoa ) {
		
//		issueChamadoKSPessoa.setName("Jonas");
//		issueChamadoKSPessoa.setIdade();
		
		try {
			return new ResponseEntity<>(issueChamadoKSService.runRules(issueChamadoKSPessoa), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/runrulesjson")
	public ResponseEntity<IssueChamadoKSPessoa> processarFolha(@RequestBody IssueChamadoKSPessoa issueChamadoKSPessoa) {
	
		try {
			return new ResponseEntity<>(issueChamadoKSService.runRules(issueChamadoKSPessoa), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
	}

}
