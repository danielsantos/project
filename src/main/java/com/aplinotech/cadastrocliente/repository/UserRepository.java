package com.aplinotech.cadastrocliente.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aplinotech.cadastrocliente.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    @Query(value="SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario findByUsernameAndActive(@Param("username") String username);
	
	List<Usuario> findByNome(String nome);
	
	@Modifying
	@Query(value="INSERT INTO ROLE ( ID , NOME ) VALUES ( 1 , 'ADMIN')", nativeQuery = true)
	@Transactional
    void insereRole();
	
	@Modifying
	@Query(value="INSERT INTO USUARIO_ROLE ( USUARIO_ID , ROLE_ID ) VALUES ( 1 , 1 )", nativeQuery = true)
	@Transactional
    void insereUsuarioRole();
	
}
