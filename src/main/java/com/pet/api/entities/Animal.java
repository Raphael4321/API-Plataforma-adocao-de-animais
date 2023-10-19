package com.pet.api.entities;
 
import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "animal")
public class Animal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "tipo", nullable = false, length = 100)
	private String tipo;
	
	@Column(name = "raca", nullable = false, length = 100)
	private String raca;
	
	@Column(name = "porte", nullable = false, length = 100)
	private String porte;
	
	@Column(name = "idade" , nullable = false, length = 100)
	private String idade;
	
	@Column(name = "ativo", nullable = false)
	private boolean ativo;
	
	@JsonManagedReference
   	@ManyToOne(fetch = FetchType.EAGER)
   	private Usuario usuario;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	public Usuario getUsuario() {
		 return usuario;
		 }

		 public void setUsuario(Usuario usuario) {
		 this.usuario = usuario;
		 }
	
public boolean getAtivo() {
		
		return ativo;
		
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
		
	}

	@Override
   	public String toString() {
         	return "Animal[" + "id=" + id + ","
                       	+ "tipo=" + tipo + ","
                       	+ "raça=" + raca + ","
                       	+ "porte=" + porte + ","
                       	+ "idade=" + idade + ","
                       	+ "ativo=" + ativo + ","
                       	+ "usuario=" + usuario + "]";
   	}



}