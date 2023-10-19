package com.pet.api.entities;
 
import java.io.Serializable;
import java.util.List;
 
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "senha", nullable = false, length = 20)
	private String senha;
	
	@Column(name = "email", nullable = false, length = 100, unique = true)
	private String email;
	
	@Column(name = "telefone" , nullable = false, length = 11)
	private String telefone;
	
	@Column(name = "ativo", nullable = false)
	private boolean ativo;
	
	@JsonManagedReference
   	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Animal> animais;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
     	this.id = id;
	}

	public String getNome() {
     	return nome;
	}
	
	public void setNome(String nome) {
     	this.nome = nome;
	}

	public String getSenha() {
     	return senha;
	}
	
	public void setSenha(String senha) {
     	this.senha = senha;
	}
	
	public String getEmail() {
     	return email;
	}
	
	public void setEmail(String email) {
     	this.email = email;
	}
	
	public String getTelefone() {
     	return telefone;
	}
	
	public void setTelefone(String telefone) {
     	this.telefone = telefone;
	}
	
	public boolean getAtivo() {
     	return ativo;
	}
	
	public void setAtivo(boolean ativo) {
     	this.ativo = ativo;
	}
	
	public List<Animal> getAnimais() {
		return animais;
	}
	
	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}

	@Override
   	public String toString() {
         	return "Usuario[" + "id=" + id + ","
                       	+ "nome=" + nome + ","
                       	+ "senha=" + senha + ","
                       	+ "email=" + email + ","
                       	+ "telefone=" + telefone + ","
                       	+ "ativo=" + ativo + "]";
   	}



}
