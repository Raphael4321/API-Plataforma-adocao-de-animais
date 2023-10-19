package com.pet.api.services;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.pet.api.security.utils.JwtTokenUtil;
import com.pet.api.services.UsuarioService;
import com.pet.api.entities.Usuario;
import com.pet.api.repositories.UsuarioRepository;
import com.pet.api.utils.ConsistenciaException;
import com.pet.api.utils.SenhaUtils;

@Service
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioRepository UserRp;
	
	@Autowired
   	private HttpServletRequest httpServletRequest;
 
   	@Autowired
   	private JwtTokenUtil jwtTokenUtil;
	
	
	
	
	public Optional<Usuario> buscarPorId(int id) throws ConsistenciaException {
     	
     	log.info("Service: buscando um Usuario com o id: {}", id);
     	
     	Optional<Usuario> user = UserRp.findById(id);
     	
     	if (!user.isPresent()) {

            	log.info("Service: Nenhum usuario com id: {} foi encontrado", id);
            	throw new ConsistenciaException("Nenhum usuario com id: {} foi encontrado", id);

     	}
     	
     	log.info("Service: Usuario encontrado!");
     	
     	return user;
     	
	}
	
	public Optional<Usuario> verificarCredenciais(String email) throws ConsistenciaException {

      	Optional<Usuario> usuario = Optional.ofNullable(UserRp.findByEmailAndAtivo(email, true));

      	if (!usuario.isPresent()) {
             	log.info("Service: Nenhum usuário ativo com email: {} foi encontrado", email);
             	throw new ConsistenciaException("Nenhum usuário ativo com email: {} foi encontrado", email);
      	}

      	return usuario;

	}
	
	
	public Usuario salvar(Usuario user) throws ConsistenciaException {
     	
     	log.info("Service: salvando o usuario: {}", user);
     	
     	if (user.getId() > 0) {
     		Optional<Usuario> usr = buscarPorId(user.getId());
     		user.setSenha(usr.get().getSenha());
     		
     		
     	} else {
     		user.setSenha(SenhaUtils.gerarHash(user.getSenha()));
     	}

     	try {
     		
     			user.setAtivo(true);
     		
            	return UserRp.save(user);
            	
     	} catch (DataIntegrityViolationException e) {
            	
            	log.info("Service: O email: {} já está cadastrado para outro usuario", user.getEmail());
            	throw new ConsistenciaException("O email: {} já está cadastrado para outro usuario", user.getEmail());
            	
     	}
     	
	}
	
	public Usuario Block(Usuario user) throws ConsistenciaException{
		
		if(user.getId() > 0)
			
			buscarPorId(user.getId());
		
			Usuario alterado = UserRp.Bloquear(user.getId());
			
			return alterado;
		
	}
	
	public Usuario alterarSenhaUsuario(String novaSenha, int id) throws ConsistenciaException {
		 
      	log.info("Service: alterando a senha do usuário: {}", id);

      	log.info("Service: Procuraremos um usuario com o id: {}", id);
      	Optional<Usuario> usr = buscarPorId(id);

      	
      	String token = httpServletRequest.getHeader("Authorization");

      	if (token != null && token.startsWith("Bearer ")) {
             	token = token.substring(7);
      	}

      	String username = jwtTokenUtil.getUsernameFromToken(token);

      	if (!usr.get().getEmail().equals(username)) {
      			
             	log.info("Service: Email do token diferente do email do usuário a ser alterado: {}", username);
             	throw new ConsistenciaException("Você não tem permissão para alterar a senha de outro usuário.");

      	}
      	

      	log.info("Service: A senha será alterada agora!");	
      	
      	
      	Usuario alterado = 	new Usuario();
      			
      	
      	
      			
      			String senha = SenhaUtils.gerarHash(novaSenha);
      			
      			UserRp.alterarSenhaUsuario(senha, id);
      			
      			log.info("Service: A senha foi alterada com sucesso!");	
      			
      	return alterado;
	}
	
}