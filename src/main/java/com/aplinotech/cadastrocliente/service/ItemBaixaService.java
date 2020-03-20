package com.aplinotech.cadastrocliente.service;

import java.util.List;

import com.aplinotech.cadastrocliente.model.ItemBaixa;

public interface ItemBaixaService {

    void saveOrUpdate(ItemBaixa itemBaixa);
 
    void deleteLogic(Long id);
    
    ItemBaixa findById(Long id);
 
    List<ItemBaixa> findAll();
 
}
