package com.pet.api.controllers;

import java.util.List;
import java.util.Optional;
 
import javax.validation.Valid;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.pet.api.dtos.AnimalDto;
import com.pet.api.entities.Animal;
import com.pet.api.services.AnimalService;
import com.pet.api.utils.ConsistenciaException;
import com.pet.api.utils.ConversaoUtils;
import com.pet.api.response.Response;
 
@RestController
@RequestMapping("/api/animal")
@CrossOrigin(origins = "*")
public class AnimalController {
 
   	private static final Logger log = LoggerFactory.getLogger(AnimalController.class);
 
   	@Autowired
   	private AnimalService animalService;
 

   	@GetMapping(value = "/usuario/{usuarioId}")
   	public ResponseEntity<Response<List<AnimalDto>>> buscarPorUsuario(@PathVariable("usuarioId") int usuarioId) throws ConsistenciaException {
 
         	Response<List<AnimalDto>> response = new Response<List<AnimalDto>>();
 
         	try {
 
                	log.info("Controller: buscando animais do usuário de ID: {}", usuarioId);
 
                	Optional<List<Animal>> listaAnimais = animalService.ListagemPorUsuario(usuarioId);
 
                	response.setDados(ConversaoUtils.ConverterListaAn(listaAnimais.get()));
 
                	return ResponseEntity.ok(response);
 
         	} catch (Exception e) {
 
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(response);
 
         	} 

     	
 
   	}
   	
   	@GetMapping(value = "/todos_animais")
   	public ResponseEntity<Response<List<AnimalDto>>> ListaTotal() throws ConsistenciaException {
 
         	Response<List<AnimalDto>> response = new Response<List<AnimalDto>>();
 
         	try {
 
                	log.info("Controller: buscando todos os animais cadastrados" );
 
                	Optional<List<Animal>> listaAnimais = animalService.ListagemInicial();
 
                	response.setDados(ConversaoUtils.ConverterListaAn(listaAnimais.get()));
 
                	return ResponseEntity.ok(response);
 
         	} catch (Exception e) {
 
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(response);
 
         	}
 
   	}
   	
	@GetMapping(value = "/tipo/{tipo}")
   	public ResponseEntity<Response<List<AnimalDto>>> buscarPorTipo(@PathVariable("tipo") String tipo) throws ConsistenciaException {
 
		Response<List<AnimalDto>> response = new Response<List<AnimalDto>>();
		 
     	try {

            	log.info("Controller: buscando animais do tipo: {}", tipo);

            	Optional<List<Animal>> listaAnimais = animalService.BuscarTipo(tipo);

            	response.setDados(ConversaoUtils.ConverterListaAn(listaAnimais.get()));

            	return ResponseEntity.ok(response);

     	}catch (ConsistenciaException e) {
     		 
        	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
        	response.adicionarErro(e.getMensagem());
        	return ResponseEntity.badRequest().body(response);

 	} catch (Exception e) {

            	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
            	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
            	return ResponseEntity.status(500).body(response);

     	}
 
   	}
 
 
   	@PostMapping
   	public ResponseEntity<Response<AnimalDto>> salvar(@Valid @RequestBody AnimalDto animalDto, BindingResult result) {
 
         	Response<AnimalDto> response = new Response<AnimalDto>();
 
         	try {
 
                	log.info("Controller: salvando o animal: {}", animalDto.toString());
 
                	if (result.hasErrors()) {
 
                       	for (int i = 0; i < result.getErrorCount(); i++) {
                       	   	response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
                       	}
 
                       	log.info("Controller: Os campos obrigatórios não foram preenchidos");
                       	return ResponseEntity.badRequest().body(response);
 
                	}
         	
                	Animal animal =ConversaoUtils.Converter(animalDto);
                	
                	response.setDados(ConversaoUtils.Converter(this.animalService.salvar(animal)));
 
                	return ResponseEntity.ok(response);
 
         	} catch (ConsistenciaException e) {
 
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	response.adicionarErro(e.getMensagem());
                	return ResponseEntity.badRequest().body(response);
 
         	} catch (Exception e) {
 
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(response);
 
         	}
 
   	}
   	@GetMapping(value = "/adotado/")
   	public ResponseEntity<Response<AnimalDto>> adotado (@Valid @RequestBody AnimalDto animDto, BindingResult result) {
   		
   		Response<AnimalDto> response = new Response<AnimalDto>();
   		

   		try {	
   		animalService.Adopted(ConversaoUtils.Converter(animDto));
   		
   		Animal animal = this.animalService.salvar(ConversaoUtils.Converter(animDto));
    	response.setDados(ConversaoUtils.Converter(animal));
   		
   		return ResponseEntity.ok(response);
   		
   		}catch(ConsistenciaException e) {
   			

        	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
        	response.adicionarErro(e.getMensagem());
        	return ResponseEntity.badRequest().body(response);
   			
   		} catch (Exception e) {
   		 
        	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
        	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
        	return ResponseEntity.status(500).body(response);

 	}
   	}
   	@GetMapping(value = "/uni/{Id}")
   	public ResponseEntity<Response<AnimalDto>> SelecionarIndividual(@PathVariable("Id") int id) throws ConsistenciaException {
 
         	Response<AnimalDto> response = new Response<AnimalDto>();
 
         	try {
 
                	log.info("Controller: buscando animal com ID: {}", id);
 
                	Optional<Animal> animal = animalService.buscarPorId(id);
 
                	response.setDados(ConversaoUtils.Converter(animal.get()));
 
                	return ResponseEntity.ok(response);
 
         	} catch (ConsistenciaException e) {
         		 
            	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
            	response.adicionarErro(e.getMensagem());
            	return ResponseEntity.badRequest().body(response);

     	} catch (Exception e) {
 
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(response);
 
         	} 
 
   	}
		
}