package com.pet.api.utils;
 
import java.util.ArrayList;
import java.util.List;

import com.pet.api.dtos.AnimalDto;
import com.pet.api.dtos.UsuarioDto;
import com.pet.api.entities.Animal;
import com.pet.api.entities.Usuario;
 

public class ConversaoUtils {
 

 
   	public static Usuario Converter(UsuarioDto usuarioDto) {
 
         	Usuario usuario = new Usuario();
 
         	if (usuarioDto.getId() != null && usuarioDto.getId() != "")
         		usuario.setId(Integer.parseInt(usuarioDto.getId()));
 
         	usuario.setNome(usuarioDto.getNome());
         	usuario.setEmail(usuarioDto.getEmail());
         	usuario.setTelefone(usuarioDto.getTelefone());
         	usuario.setAtivo(usuarioDto.getAtivo());
         	usuario.setSenha(usuarioDto.getSenha());

         	
         	return usuario;
 
   	}
 
   	public static UsuarioDto Converter(Usuario usuario) {
 
         	UsuarioDto usuarioDto = new UsuarioDto();
 
        	usuarioDto.setId(Integer.toString(usuario.getId()));
 
         	usuarioDto.setNome(usuario.getNome());
         	usuarioDto.setEmail(usuario.getEmail());
         	usuarioDto.setTelefone(usuario.getTelefone());
         	usuarioDto.setAtivo(usuario.getAtivo());

         	
         	return usuarioDto;
 
   	}

   	
   	public static Animal Converter(AnimalDto animDto) {
   	 
     	Animal anim = new Animal();
     	Usuario user = new Usuario();

     	if (animDto.getId() != null && animDto.getId() != "")
     		anim.setId(Integer.parseInt(animDto.getId()));

     	user.setId(Integer.parseInt(animDto.getUsuarioId()));
     	
     	anim.setTipo(animDto.getTipo());
     	anim.setRaca(animDto.getRaca());
     	anim.setPorte(animDto.getPorte());
     	anim.setIdade(animDto.getIdade());
     	anim.setAtivo(animDto.getAtivo());
     	anim.setUsuario(user);


     	return anim;

	}

	public static AnimalDto Converter(Animal anim) {

		AnimalDto animDto = new AnimalDto();
		
		animDto.setId(Integer.toString(anim.getId()));

		animDto.setTipo(anim.getTipo());
		animDto.setRaca(anim.getRaca());
		animDto.setPorte(anim.getPorte());
		animDto.setIdade(anim.getIdade());
		animDto.setAtivo(anim.getAtivo());
		animDto.setUsuarioId(Integer.toString(anim.getUsuario().getId()));

     	return animDto;

	}
   	
   	
        public static List<AnimalDto> ConverterListaAn(List<Animal> lista){
     	
     	List<AnimalDto> lst = new ArrayList<AnimalDto>(lista.size());
     	
     	for (Animal an : lista) {
            	lst.add(Converter(an));
     	}
     	
     	return lst;
     	
	}
 
 
 
}
