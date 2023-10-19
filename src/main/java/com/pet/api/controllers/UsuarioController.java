package com.pet.api.controllers;
 
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
 
import com.pet.api.dtos.SenhaDto;
import com.pet.api.dtos.UsuarioDto;
import com.pet.api.entities.Usuario;
import com.pet.api.response.Response;
import com.pet.api.services.UsuarioService;
import com.pet.api.utils.ConsistenciaException;
import com.pet.api.utils.ConversaoUtils;
 
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
 
   	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
 
   	@Autowired
   	private UsuarioService usuarioService;
 
   	@GetMapping(value = "/{id}")
   	public ResponseEntity<Response<UsuarioDto>> buscarPorId(@PathVariable("id") int id) {
 
         	Response<UsuarioDto> response = new Response<UsuarioDto>();
 
         	try {
 
                	log.info("Controller: buscando usuario com id: {}", id);
 
                	Optional<Usuario> usuario = usuarioService.buscarPorId(id);
 
                	response.setDados(ConversaoUtils.Converter(usuario.get()));
 
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
 
  
   	@PostMapping
   	public ResponseEntity<Response<UsuarioDto>> salvar(@Valid @RequestBody UsuarioDto usuarioDto,
                	BindingResult result) {
 
         	Response<UsuarioDto> response = new Response<UsuarioDto>();
 
         	try {
 
                	log.info("Controller: salvando o usuario: {}", usuarioDto.toString());
 
                	
                	if (result.hasErrors()) {
 
                       	for (int i = 0; i < result.getErrorCount(); i++) {
                       	   	response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
                       	}
 
                       	log.info("Controller: Os campos obrigatórios não foram preenchidos");
                       	return ResponseEntity.badRequest().body(response);
 
                	}
 
                	Usuario usuario = ConversaoUtils.Converter(usuarioDto);
 
         	   		response.setDados(ConversaoUtils.Converter(this.usuarioService.salvar(usuario)));
                	
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
 
 
   	@PostMapping(value = "/senha")
   	public ResponseEntity<Response<SenhaDto>> alterarSenhaUsuario(@Valid @RequestBody SenhaDto senhaDto,
                	BindingResult result) {
 
         	Response<SenhaDto> response = new Response<SenhaDto>();
 
         	try {
 
                	log.info("Controller: alterando a senha do usuário: {}", senhaDto.getIdUsuario());
 
                	
                	if (result.hasErrors()) {
 
                       	for (int i = 0; i < result.getErrorCount(); i++) {
                       	   	response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
                       	}
 
                       	log.info("Controller: Os campos obrigatórios não foram preenchidos");
                       	return ResponseEntity.badRequest().body(response);
 
                	}
 
                	
                	this.usuarioService.alterarSenhaUsuario(senhaDto.getNovaSenha(),
                              	Integer.parseInt(senhaDto.getIdUsuario()));
                		
                	
                	senhaDto.setNovaSenha(null);
                	
                	response.setDados(senhaDto);               	
                        
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
