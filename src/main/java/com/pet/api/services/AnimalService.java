package com.pet.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.pet.api.entities.Animal;
import com.pet.api.repositories.AnimalRepository;
import com.pet.api.utils.ConsistenciaException;

@Service
public class AnimalService {

	private static final Logger log = LoggerFactory.getLogger(AnimalService.class);
	
	@Autowired
	private AnimalRepository AnimRp;
	
	public Optional<Animal> buscarPorId(int id) throws ConsistenciaException {
     	
     	log.info("Service: buscando um animal com o id: {}", id);
     	
     	Optional<Animal> anim = AnimRp.findById(id);
     	
     	log.info("Service: Encontrado um animal com o id: {}", id);
     	
     	if (!anim.isPresent()) {

            	log.info("Service: Nenhum animal com id: {} foi encontrado", id);
            	throw new ConsistenciaException("Nenhum animal com id: {} foi encontrado", id);

     	}
     	
     	log.info("Service: Será enviado um animal com o id: {}", id);
     	return anim;
     	
	}
	
	@CachePut("cacheAnimaisPorUsuario")
	public Animal salvar(Animal anim) throws ConsistenciaException {
     	
     	log.info("Service: salvando o animal: {}", anim);
     
     	if (anim.getId() > 0) {
     		buscarPorId(anim.getId());
        	return AnimRp.save(anim);
     	}else {	
     		return AnimRp.save(anim);
     	}
	}
	
	public Animal Adopted(Animal anim) throws ConsistenciaException{
		
		if(anim.getId() > 0)
			
			buscarPorId(anim.getId());
		
			AnimRp.adotado(anim.getId());
			
			return anim;
		
	}
	
	public Optional<List<Animal>> BuscarTipo(String dado)
			 throws ConsistenciaException  {
		
		Optional<List<Animal>> lista = Optional.of((new ArrayList<Animal>()));
		
		lista = AnimRp.selectTipo(dado);
		
		
		if (!lista.isPresent() || lista.get().size() < 1) {
			
       	log.info("Service: Nenhum animal encontrado com os seguintes dados: {}", dado);
       	throw new ConsistenciaException("Nenhum animal encontrado com o seguinte dado: {}", dado);
	}
		
		return lista;	
	}
	
	public Optional<List<Animal>> ListagemInicial(){
		
		Optional<List<Animal>> lista = AnimRp.listar();
		
		return lista;
		
	}
	
	public Optional<List<Animal>> ListagemPorUsuario(int id) {
	
		Optional<List<Animal>> lista = Optional.of((new ArrayList<Animal>()));
		
		lista = AnimRp.findByUserId(id);
		
		return lista;
	}
	
	 @CachePut("cacheAnimaisPorUsuario")
	 public void excluirPorId(int id) throws ConsistenciaException {
	 	 log.info("Service: excluíndo o animal de id: {}", id);
	 	 buscarPorId(id);

	 	 AnimRp.deleteById(id);
	 }
	}
