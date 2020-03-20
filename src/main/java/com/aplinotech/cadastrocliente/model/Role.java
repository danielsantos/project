package com.aplinotech.cadastrocliente.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @Column
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;

    public Role(Long id) {
        super();
        this.id = id;
    }

    public Role() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
