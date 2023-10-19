package com.pet.api.security.dtos;
 
import javax.validation.constraints.NotEmpty;
 
import org.hibernate.validator.constraints.Length;
 
public class JwtAuthenticationDto {
 
   	@NotEmpty(message = "Email não pode ser vazio.")
   	private String email;
   	
   	@NotEmpty(message = "Senha não pode ser vazia.")
   	@Length(min = 5, max = 20,
   	message = "Senha atual deve conter entre 8 e 20 caracteres.")
   	private String senha;
   	
   	public String getEmail() {
         	return email;
   	}
   	
   	public void setEmail(String email) {
         	this.email = email;
   	}
 
   	public String getSenha() {
         	return senha;
   	}
   	
   	public void setSenha(String senha) {
         	this.senha = senha;
   	}
 
   	@Override
   	public String toString() {
         	return "JwtAuthenticationRequestDto[email=" + email + ", senha=" + senha + "]";
   	}
   	
}
