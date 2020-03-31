package com.aplinotech.cadastrocliente.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(length = 100)
	private String nome;
	
	@Column
	private String username;

	@Column(length = 80)
	private String email;
	
	@Transient
	private String confirmeEmail;

	@Column
	private String password;

	@Column
	private String status;

	@Column
	private Date dataCadastro;

	@Transient
	private String passwordConfirm;

	private String token;

	private Date dataAtivacao;

	@ManyToMany
	@JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

}
