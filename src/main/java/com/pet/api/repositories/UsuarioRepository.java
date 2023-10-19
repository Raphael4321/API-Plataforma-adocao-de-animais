package com.pet.api.repositories;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
 
import com.pet.api.entities.Usuario;
 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
   	
	@Transactional(readOnly = true)
   	Usuario findByEmailAndAtivo(String email, boolean ativo);
	
	@Transactional(readOnly = true)
   	Optional<Usuario> findById(int id);
   	
	
   	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("UPDATE Usuario SET senha = :novasenha WHERE id = :idusuario")
   	void alterarSenhaUsuario(@Param("novasenha") String novasenha, @Param("idusuario") int idusuario);
   	
   	
   	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("UPDATE Usuario SET ativo = false WHERE id = :idusuario")
   	Usuario Bloquear(@Param("idusuario") int idusuario );
   	

}