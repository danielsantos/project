package com.aplinotech.cadastrocliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(length = 100)
	private String nome;
	
	private String username;
	private String password;
	private String status;
	private Date dataCadastro;
	private String token;
	private Date dataAtivacao;

	@Column(length = 80)
	private String email;
	
	@Transient
	private String confirmeEmail;

	@Transient
	private String passwordConfirm;

	@ManyToMany
	@JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

}
