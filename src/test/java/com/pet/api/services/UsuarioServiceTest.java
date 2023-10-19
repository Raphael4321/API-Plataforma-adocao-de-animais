package com.pet.api.services;
 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
 
import java.util.Optional;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
 
import com.pet.api.entities.Usuario;
import com.pet.api.repositories.UsuarioRepository;
import com.pet.api.utils.ConsistenciaException;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
 
   	@MockBean
   	private UsuarioRepository userRp;
   	
   	@Autowired
   	private UsuarioService userSr;	

   	@Test
   	public void testSalvarComSucesso() throws ConsistenciaException {  	
         	
         	BDDMockito.given(userRp.save(Mockito.any(Usuario.class)))
                	.willReturn(new Usuario());
         	
         	Usuario resultado = userSr.salvar(new Usuario());
         	
         	assertNotNull(resultado);
         	
   	}
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testSalvarIdNaoEncontrado() throws ConsistenciaException {
         	
         	BDDMockito.given(userRp.findById(Mockito.anyInt()))
         	.willReturn(Optional.empty());
         	
         	Usuario u = new Usuario();
         	u.setId(1);
         	
         	userSr.salvar(u);
 
   	}
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testSalvarEmailDuplicado() throws ConsistenciaException {	
         	
         	BDDMockito.given(userRp.save(Mockito.any(Usuario.class)))
         	.willThrow(new DataIntegrityViolationException(""));
         	
         	userSr.salvar(new Usuario());
 
   	}
   	
   	public void testBuscarPorIdExistente() throws ConsistenciaException { 
     	
     	BDDMockito.given(userRp.findById(Mockito.anyInt()))
            	.willReturn(Optional.of(new Usuario()));
     	
     	Optional<Usuario> resultado = userSr.buscarPorId(1);
     	
     	assertTrue(resultado.isPresent());
     	
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {  	
     	
     	BDDMockito.given(userRp.findById(Mockito.anyInt()))
            	.willReturn(Optional.empty());
     	
     	userSr.buscarPorId(1);
     	
	}
   	
	@Test
   	public void testDesativarUsuario() throws ConsistenciaException {  	
         	
		BDDMockito.given(userRp.Bloquear(Mockito.anyInt()))
    	.willReturn(new Usuario());
         	
         	Usuario resultado = userSr.Block(new Usuario());
         	
         	assertNotNull(resultado);
         	
   	}
	
	
}
