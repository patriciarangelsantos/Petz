package br.com.petz.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetzTestResource {
	
	@RequestMapping("/")
    public String home(){
        return "Bem vindo ao Petz Cadastro de Clientes e seus Bichinhos!";
    }
}
