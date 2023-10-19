package com.pet.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pet.api.dtos.UsuarioDto;
import com.pet.api.entities.Usuario;
import com.pet.api.services.UsuarioService;
import com.pet.api.utils.ConsistenciaException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;

	private Usuario CriarUsuarioTestes() {

		Usuario usuario = new Usuario();

		usuario.setId(1);
		usuario.setNome("Fulano");
		usuario.setSenha(null);
		usuario.setEmail("Fulano01@gmail.com");
		usuario.setTelefone("4311111111");
		usuario.setAtivo(true);

		return usuario;

	}

	@Test
	@WithMockUser(roles = "USUARIO")
	public void testBuscarPorIdSucesso() throws Exception {

		Usuario usuario = CriarUsuarioTestes();

		BDDMockito.given(usuarioService.buscarPorId(Mockito.anyInt()))
			.willReturn(Optional.of(usuario));

		mvc.perform(MockMvcRequestBuilders.get("/api/usuario/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.dados.id").value(usuario.getId()))
			.andExpect(jsonPath("$.dados.nome").value(usuario.getNome()))
			.andExpect(jsonPath("$.dados.senha").value(usuario.getSenha()))
			.andExpect(jsonPath("$.dados.email").value(usuario.getEmail()))
			.andExpect(jsonPath("$.dados.telefone").value(usuario.getTelefone()))
			.andExpect(jsonPath("$.erros").isEmpty());

	}

	@Test
	@WithMockUser(roles = "USUARIO")
	public void testBuscarPorIdInconsistencia() throws Exception {

		BDDMockito.given(usuarioService.buscarPorId((Mockito.anyInt())))
			.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/usuario/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}
	
@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarNomeEmBranco() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setSenha("12345");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("4311111111");
	objEntrada.setAtivo(true);

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome não pode ser vazio."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarNomeInsuficiente() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setNome("Fu");
	objEntrada.setSenha("12345");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("4311111111");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome do usuário deve ter no mínimo 5 e no máximo 100 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarNomeExcedente() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setNome("Fuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuulaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaanooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
	objEntrada.setSenha("12345");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("4311111111");


	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome do usuário deve ter no mínimo 5 e no máximo 100 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarSenhaEmBranco() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setNome("Fulano");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("4311111111");


	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha não pode ser vazia."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarSenhaInsuficiente() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setNome("Fulano");
	objEntrada.setSenha("12");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("4311111111");


	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha do usuário deve ter no mínimo 5 e no máximo 20 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarSenhaExcedente() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setNome("Fulano");
	objEntrada.setSenha("1234567891011121314568794501");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("4311111111");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha do usuário deve ter no mínimo 5 e no máximo 20 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarEmailEmBranco() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setNome("Fulano");
	objEntrada.setSenha("12345");
	objEntrada.setTelefone("4311111111");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Email não pode ser vazio."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarTelefoneEmBranco() throws Exception {

    UsuarioDto objEntrada = new UsuarioDto();
	
	objEntrada.setNome("Fulano");
	objEntrada.setSenha("12345");
	objEntrada.setEmail("Fulano01@gmail.com");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Telefone não pode ser vazio."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarTelefoneExcedente() throws Exception {

UsuarioDto objEntrada = new UsuarioDto();
	
	objEntrada.setNome("Fulano");
	objEntrada.setSenha("12345");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("4345 1111 1111");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Telefone do usuário deve ter no mínimo 8 e no máximo 11 caracteres."));

}
@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarTelefoneInsuficiente() throws Exception {

	UsuarioDto objEntrada = new UsuarioDto();

	objEntrada.setNome("Fulano");
	objEntrada.setSenha("12345");
	objEntrada.setEmail("Fulano01@gmail.com");
	objEntrada.setTelefone("431111");


	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/usuario/")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Telefone do usuário deve ter no mínimo 8 e no máximo 11 caracteres."));

}

}