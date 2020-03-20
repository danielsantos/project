package com.aplinotech.cadastrocliente.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aplinotech.cadastrocliente.model.Baixa;
import com.aplinotech.cadastrocliente.model.ItemBaixa;

@Repository
public interface BaixaRepository extends JpaRepository<Baixa, Long> {
	
    @Query(value="SELECT i FROM ItemBaixa i WHERE baixa.data BETWEEN :dataInicio AND :dataFim")
    List<ItemBaixa> findByDates(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);

}
