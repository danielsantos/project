package com.aplinotech.cadastrocliente.service;

import java.util.Date;
import java.util.List;

import com.aplinotech.cadastrocliente.model.Baixa;
import com.aplinotech.cadastrocliente.model.ItemBaixa;
import com.aplinotech.cadastrocliente.model.Usuario;

public interface BaixaService {

    void saveOrUpdate(Baixa baixa);
 
    void deleteLogic(Long id);
    
    Baixa findById(Long id);
 
    List<Baixa> findAll();
    
    List<ItemBaixa> findByDates(Date dataInicio, Date dataFim, Usuario usuario);
 
}
