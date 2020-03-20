package com.aplinotech.cadastrocliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplinotech.cadastrocliente.model.ItemBaixa;

@Repository
public interface ItemBaixaRepository extends JpaRepository<ItemBaixa, Long> {

}
