package com.redhat.consultingbr.testekieserver.service;

import java.util.ArrayList;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.KieServiceResponse.ResponseType;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.RuleServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.gov.ce.fortaleza.sepog.domain.IssueChamadoKSPessoa;


@Service
public class IssueChamadoKSService {
	@Value("${redhat.decision-manager.url.folha}")
	private String url;	
	@Value("${redhat.decision-manager.username.folha}")
	private String username;	
	@Value("${redhat.decision-manager.password.folha}")
	private String password;	
	@Value("${redhat.decision-manager.containerIdFolha}")
	private String containerId;
	@Value("${redhat.decision-manager.session.folha}")
	private String session;
	
	@Autowired
	private KieServerService kieServerService;
	
	public IssueChamadoKSPessoa runRules(IssueChamadoKSPessoa issueChamadoKSPessoa) {
		
		try {

			System.out.println("url:"+url);
			System.out.println("username:"+username);
			System.out.println("password:"+password);
			System.out.println("containerId:"+containerId);
			System.out.println("session:"+session);
			List<Command<?>> commands = new ArrayList<Command<?>>();
			
			commands.add((Command<?>) KieServices.Factory.get().getCommands()
					.newInsert(issueChamadoKSPessoa, "IssueChamadoKSPessoa"));

			//commands.add(new FireAllRulesCommand("myFireCommand"));

			RuleServicesClient ruleClient = kieServerService.criarConexaoKieServer(url, username, password);
			 
			//BatchExecutionHelper.newJSonMarshaller().fromXML(commands.toString());
			
			BatchExecutionCommand batchCommand = KieServices.Factory.get().getCommands().newBatchExecution(commands,
					session);
			
			ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(containerId, batchCommand);

			if (response.getType() == ResponseType.SUCCESS) {
				System.out.println("Commands executed with success! Response: ");
				System.out.println(response.getResult().toString());
				System.out.println(((IssueChamadoKSPessoa) response.getResult().getValue("IssueChamadoKSPessoa")).getName());
			} else {
				System.out.println("Error executing rules. Message: ");
				System.out.println(response.getMsg());
			}
			
			//return (ArrayList<Funcionario>) response.getResult().getValue("Funcionarios");
			
			return (IssueChamadoKSPessoa) response.getResult().getValue("IssueChamadoKSPessoa");
			
			//issueChamadoKSPessoa.setIdade(101);

			//return issueChamadoKSPessoa;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
