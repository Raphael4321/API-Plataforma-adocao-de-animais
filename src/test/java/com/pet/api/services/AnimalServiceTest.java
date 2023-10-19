package com.pet.api.services;
 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
 
import com.pet.api.entities.Animal;
import com.pet.api.entities.Usuario;
import com.pet.api.repositories.AnimalRepository;
import com.pet.api.utils.ConsistenciaException;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AnimalServiceTest {
 
   	@MockBean
   	private AnimalRepository aniRp;
   	
   	@Autowired
   	private AnimalService animSr;	

   	private Animal CriarAnimalTestes() {

		Animal animal = new Animal();
		
		Usuario user = new Usuario();
		
		user = CriarUsuarioTestes();

		animal.setId(1);
		animal.setTipo("gato");
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
   	public void testSalvarComSucesso() throws ConsistenciaException {  	
         	
         	BDDMockito.given(aniRp.save(Mockito.any(Animal.class)))
                	.willReturn(new Animal());
         	
         	Animal resultado = animSr.salvar(new Animal());
         	
         	
         	assertNotNull(resultado);
         	
   	}
   	
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testSalvarIdNaoEncontrado() throws ConsistenciaException {
         	
         	BDDMockito.given(aniRp.findById(Mockito.anyInt()))
         	.willReturn(Optional.empty());
         	
         	Animal a = new Animal();
         	a.setId(1);
         	
         	animSr.salvar(a);
 
   	}
   	
   	@Test
   	public void testBuscarPorIdExistente() throws ConsistenciaException { 
     	
     	BDDMockito.given(aniRp.findById(Mockito.anyInt()))
            	.willReturn(Optional.of(new Animal()));
     	
     	Optional<Animal> resultado = animSr.buscarPorId(1);
     	
     	assertTrue(resultado.isPresent());
     	
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {  	
     	
     	BDDMockito.given(aniRp.findById(Mockito.anyInt()))
            	.willReturn(Optional.empty());
     	
     	animSr.buscarPorId(1);
     	
	}
   	
	@Test
 	public void testBuscarPorTipoExistente() throws ConsistenciaException { 
     	
		
		List<Animal> lista = new ArrayList<>();
		
		lista = CriarListaAnimalTestes();
		
     	BDDMockito.given(aniRp.selectTipo(Mockito.anyString()))
            	.willReturn(Optional.of(lista));
     	
     	
     	Optional<List<Animal>> resultado = Optional.of(new ArrayList<Animal>());
     	
     	
     	resultado = animSr.BuscarTipo("gato");
     	

     	assertTrue(resultado.isPresent());
     	
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorTipoNaoExistente() throws ConsistenciaException {  	
     	
     	BDDMockito.given(aniRp.selectTipo(Mockito.anyString()))
            	.willReturn(Optional.empty());
     	
     	animSr.BuscarTipo("gato");
     	
    	
	}
	
	
	@Test
   	public void testDesativarAnimal() throws ConsistenciaException {  	
         	
		BDDMockito.given(aniRp.adotado(Mockito.anyInt()))
    	.willReturn(new Animal());
         	
         	Animal resultado = animSr.Adopted(new Animal());
         	
         	assertNotNull(resultado);
         	
   	}
	
	@Test
	public void testListagem() throws ConsistenciaException { 
     	
     	BDDMockito.given(aniRp.listar())
		.willReturn(Optional.of(new ArrayList<Animal>()));
     	
     	Optional<List<Animal>> resultado = animSr.ListagemInicial();
     	
     	assertTrue(resultado.isPresent());
     	
	}
	
	
}
