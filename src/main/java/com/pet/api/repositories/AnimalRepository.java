package com.pet.api.repositories;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pet.api.entities.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
	
	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("SELECT a FROM Animal a WHERE a.usuario.id = :idusuario")
   	Optional<List<Animal>> findByUserId(@Param("idusuario") int idusuario);
	
	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("SELECT a FROM Animal a INNER JOIN Usuario u ON a.usuario.id = u.id WHERE u.ativo = true AND a.ativo = true")
   	Optional<List<Animal>> listar();
   	
	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("UPDATE Animal SET ativo = false WHERE id = :idanimal")
   	Animal adotado(@Param("idanimal") int idanimal);
   	
	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("SELECT a FROM Animal a WHERE tipo = :dado")
   	Optional<List<Animal>> selectTipo(@Param("dado") String dado);
   	 		
	@Transactional
   	@Query("SELECT a FROM Animal a WHERE id = :dado")
   	Optional<Animal> findById(@Param("dado") int dado);
	
	
     	
}
