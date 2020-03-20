package com.aplinotech.cadastrocliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplinotech.cadastrocliente.model.Setup;

@Repository
public interface SetupRepository extends JpaRepository<Setup, Long>{

}
