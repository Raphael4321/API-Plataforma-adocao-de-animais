package com.pet.api.security;

import com.pet.api.entities.Usuario;

public class JwtUserFactory {
	public static JwtUser create(Usuario usuario) {
	   	
     	return new JwtUser(usuario);	
	
	}

}
