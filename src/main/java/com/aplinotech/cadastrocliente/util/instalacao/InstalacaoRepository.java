package com.aplinotech.cadastrocliente.util.instalacao;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aplinotech.cadastrocliente.model.Setup;

@Repository
public interface InstalacaoRepository extends JpaRepository<Setup, Long> {
	
	@Modifying
	@Query(value="INSERT INTO SETUP ( ID , DATA_PRIMEIRO_ACESSO , DATA_EXPIRACAO ) "
			   + " VALUES ( 1 , CURRENT_TIMESTAMP , ?1 )", nativeQuery = true)
	@Transactional
    void insereSetup(Date dataExpiracao);
	
	@Modifying
	@Query(value="INSERT INTO USUARIO ( ID , DATA_CADASTRO , EMAIL , NOME , PASSWORD , USERNAME ) "
			   + " VALUES ( 1 , CURRENT_TIMESTAMP , 'admin' , 'admin' , 'admin' , 'admin' )", nativeQuery = true)
	@Transactional
    void insereUsuario();
	
	@Modifying
	@Query(value="INSERT INTO ROLE ( ID , NOME ) VALUES ( 1 , 'ADMIN' )", nativeQuery = true)
	@Transactional
    void insereRole();
	
	@Modifying
	@Query(value="INSERT INTO USUARIO_ROLE ( USUARIO_ID , ROLE_ID ) VALUES ( 1 , 1 )", nativeQuery = true)
	@Transactional
    void insereUsuarioRole();

}
