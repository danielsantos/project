package com.aplinotech.cadastrocliente.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.ItemBaixa;
import com.aplinotech.cadastrocliente.repository.ItemBaixaRepository;
import com.aplinotech.cadastrocliente.service.ItemBaixaService;

@Service
@Transactional
public class ItemBaixaServiceImpl implements ItemBaixaService {
	
	@Autowired
	private ItemBaixaRepository itemBaixaRepository;

	@Override
	public void saveOrUpdate(ItemBaixa itemBaixa) {
		itemBaixaRepository.save(itemBaixa);
	}

	@Override
	public void deleteLogic(Long id) {
		ItemBaixa itemBaixa = itemBaixaRepository.findOne(id);
		//baixa.setStatus("I");
		saveOrUpdate(itemBaixa);
	}

	@Override
	public ItemBaixa findById(Long id) {
		return itemBaixaRepository.findOne(id);
	}

	@Override
	public List<ItemBaixa> findAll() {
		return itemBaixaRepository.findAll();
	}

}