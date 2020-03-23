package com.aplinotech.cadastrocliente.service;

import java.util.Date;
import java.util.List;

import com.aplinotech.cadastrocliente.model.Entrada;
import com.aplinotech.cadastrocliente.model.Usuario;

public interface EntradaService {

    void saveOrUpdate(Entrada entrada);
 
    List<Entrada> findByDates(Date dataInicio, Date dataFim, Usuario usuario);
 
}
