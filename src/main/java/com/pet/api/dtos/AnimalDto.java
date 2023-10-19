package com.pet.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class AnimalDto {
	
private String id;
   	
   	@NotEmpty(message = "Tipo não pode ser vazio.")
   	@Length(min = 3, max = 20,
   	message = "Tipo deve ter no mínimo 3 e no máximo 20 caracteres.")
   	private String tipo;
   	
   	@NotEmpty(message = "Raça não pode ser vazio.")
   	@Length(min = 3, max = 20,
   	message = "Raça deve ter no mínimo 3 e no máximo 20 caracteres.")
   	private String raca;
   	
   	@NotEmpty(message = "Porte não pode ser vazio.")
	@Length(min = 5, max = 7,
   	message = "Porte deve ter no mínimo 5 e no máximo 7 caracteres.")
   	private String porte;
   	
   	@NotEmpty(message = "Idade não pode ser vazia.")
   	@Length(min = 1, max = 2,
   	message = "Idade deve ter no mínimo 1 e no máximo 2 caracteres numéricos")
   	private String idade;
   	
   	private boolean ativo;
   	
   	@NotEmpty(message = "O ID do usuario não pode ser vazio.")
    private String usuarioId;
   	
   	public String getId() {
         	return id;
   	}
   	
   	public void setId(String id) {
         	this.id = id;
   	}
   	
   	public String getTipo() {
         	return tipo;
   	}
   	
   	public void setTipo(String tipo) {
         	this.tipo = tipo;
   	}
   	
   	public String getRaca() {
         	return raca;
   	}
   	
   	public void setRaca(String raca) {
         	this.raca = raca;
   	}
   	
   	
   	public String getPorte() {
         	return porte;
   	}
   	
   	public void setPorte(String porte) {
         	this.porte = porte;
   	}
	
     public String getIdade() {
		    return idade;
		
	}
	public void setIdade(String idade) {
		    this.idade = idade;
		
	}
	
       public boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
		
	}
	
	public String getUsuarioId() {
		 return usuarioId;
		 }

		 public void setUsuarioId(String usuarioId) {
		 this.usuarioId = usuarioId;
		 }
   	
	@Override
   	public String toString() {
         	return "Animal[" + "id=" + id + ","
                       	+ "tipo=" + tipo + ","
                       	+ "raça=" + raca + ","
                       	+ "porte=" + porte + ","
                       	+ "idade=" + idade + ","
                       	+ "ativo=" + ativo + ","
                       	+ "usuarioId=" + usuarioId + "]";
   	}

 
   	
}
