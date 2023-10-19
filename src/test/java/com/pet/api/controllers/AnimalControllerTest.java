package com.pet.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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

import com.pet.api.dtos.AnimalDto;
import com.pet.api.entities.Animal;
import com.pet.api.entities.Usuario;
import com.pet.api.services.AnimalService;
import com.pet.api.utils.ConsistenciaException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AnimalControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AnimalService animalService;

	private Animal CriarAnimalTestes() {

		Animal animal = new Animal();
		
		Usuario user = CriarUsuarioTestes();

		animal.setId(1);
		animal.setTipo("Cachorro");
		animal.setRaca("Poodle");
		animal.setPorte("Médio");
		animal.setIdade("10");
		animal.setAtivo(true);
		animal.setUsuario(user);
		

		return animal;

	}
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
   	
   	private List<Animal> CriarListaAnimalTestes() {

		Animal animal = new Animal();
	
		animal = CriarAnimalTestes();
		
		List<Animal> lista = new ArrayList<>();
		
		lista.add(animal);

		return lista;

	}

	@Test
	@WithMockUser(roles = "USUARIO")
	public void testBuscarPorIdSucesso() throws Exception {

		Animal animal = CriarAnimalTestes();

		BDDMockito.given(animalService.buscarPorId(Mockito.anyInt()))
			.willReturn(Optional.of(animal));

		mvc.perform(MockMvcRequestBuilders.get("/api/animal/uni/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.dados.id").value(animal.getId()))
			.andExpect(jsonPath("$.dados.tipo").value(animal.getTipo()))
			.andExpect(jsonPath("$.dados.raca").value(animal.getRaca()))
			.andExpect(jsonPath("$.dados.porte").value(animal.getPorte()))
			.andExpect(jsonPath("$.dados.idade").value(animal.getIdade()))
			.andExpect(jsonPath("$.erros").isEmpty());
		
		

	}

	@Test
	@WithMockUser(roles = "USUARIO")
	public void testBuscarPorIdInconsistencia() throws Exception {

		BDDMockito.given(animalService.buscarPorId((Mockito.anyInt())))
			.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/animal/uni/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}
	
	@Test
	@WithMockUser(roles = "USUARIO")
	public void testBuscarPorTipoSucesso() throws Exception {

		
		List<Animal> lista = new ArrayList<>();
		
		lista = CriarListaAnimalTestes();

		BDDMockito.given(animalService.BuscarTipo(Mockito.anyString()))
		.willReturn(Optional.of(lista));

		mvc.perform(MockMvcRequestBuilders.get("/api/animal/tipo/Cachorro")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.dados.[0].id").value(lista.get(0).getId()))
			.andExpect(jsonPath("$.dados.[0].tipo").value(lista.get(0).getTipo()))
			.andExpect(jsonPath("$.dados.[0].raca").value(lista.get(0).getRaca()))
			.andExpect(jsonPath("$.dados.[0].porte").value(lista.get(0).getPorte()))
			.andExpect(jsonPath("$.dados.[0].idade").value(lista.get(0).getIdade()))
			.andExpect(jsonPath("$.erros").isEmpty());

	}
	
	@Test
	@WithMockUser(roles = "USUARIO")
	public void testBuscarPorTipoInconsistencia() throws Exception {

		BDDMockito.given(animalService.BuscarTipo(Mockito.anyString()))
		.willThrow(new ConsistenciaException("Teste inconsistência"));
		
		mvc.perform(MockMvcRequestBuilders.get("/api/animal/tipo/Gato")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}
	

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarTipoEmBranco() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setRaca("Poodle");
	objEntrada.setPorte("Médio");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Tipo não pode ser vazio."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarTipoInsuficiente() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Ca");
	objEntrada.setRaca("Poodle");
	objEntrada.setPorte("Médio");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");
	

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Tipo deve ter no mínimo 3 e no máximo 20 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarTipoExcedente() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Caaaaaaaaaaaaaaaaachorrrrrroooooooo");
	objEntrada.setRaca("Poodle");
	objEntrada.setPorte("Médio");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");
	
	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Tipo deve ter no mínimo 3 e no máximo 20 caracteres."));

}



@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarRacaEmBranco() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Cachorro");
	objEntrada.setPorte("Médio");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Raça não pode ser vazio."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarRacaInsuficiente() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Cachorro");
	objEntrada.setRaca("Po");
	objEntrada.setPorte("Médio");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Raça deve ter no mínimo 3 e no máximo 20 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarRacaExcedente() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Cachorro");
	objEntrada.setRaca("Poooooooooooooodddddddddddddddddddleeeeeeeeeeeeeeeeeeeeeeeee");
	objEntrada.setPorte("Médio");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Raça deve ter no mínimo 3 e no máximo 20 caracteres."));

}


@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarPorteEmBranco() throws Exception {

	AnimalDto objEntrada = new AnimalDto();
    
	objEntrada.setTipo("Cachorro");
	objEntrada.setRaca("Poodle");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Porte não pode ser vazio."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarPorteInsuficiente() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Cachorro");
	objEntrada.setRaca("Poodle");
	objEntrada.setPorte("Pe");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Porte deve ter no mínimo 5 e no máximo 7 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarPorteExcedente() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Cachorro");
	objEntrada.setRaca("Poodle");
	objEntrada.setPorte("Méeeeeeeeeeeeeeeeeeeeeedioooooooooooooooooooooooo");
	objEntrada.setIdade("10");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Porte deve ter no mínimo 5 e no máximo 7 caracteres."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarIdadeEmBranco() throws Exception {

	AnimalDto objEntrada = new AnimalDto();
    
	objEntrada.setTipo("Cachorro");
	objEntrada.setRaca("Poodle");
	objEntrada.setPorte("Médio");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Idade não pode ser vazia."));

}

@Test
@WithMockUser(roles = "USUARIO")
public void testSalvarIdadeExcedente() throws Exception {

	AnimalDto objEntrada = new AnimalDto();

	objEntrada.setTipo("Cachorro");
	objEntrada.setRaca("Poodle");
	objEntrada.setPorte("Médio");
	objEntrada.setIdade("50000000");
	objEntrada.setUsuarioId("1");

	String json = new ObjectMapper().writeValueAsString(objEntrada);

	mvc.perform(MockMvcRequestBuilders.post("/api/animal")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Idade deve ter no mínimo 1 e no máximo 2 caracteres numéricos"));

}

}