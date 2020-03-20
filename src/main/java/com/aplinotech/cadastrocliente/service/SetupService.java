package com.aplinotech.cadastrocliente.service;

import com.aplinotech.cadastrocliente.model.Setup;

import java.util.Date;

public interface SetupService {

    void saveOrUpdate(Setup configuracaoSistema);
 
    Setup find();
    
    boolean sistemaExpirou();

    Date getDataLimiteDeUsoGratis();
 
}
