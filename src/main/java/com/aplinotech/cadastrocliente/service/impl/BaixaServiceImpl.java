package com.aplinotech.cadastrocliente.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.Baixa;
import com.aplinotech.cadastrocliente.model.ItemBaixa;
import com.aplinotech.cadastrocliente.repository.BaixaRepository;
import com.aplinotech.cadastrocliente.service.BaixaService;

@Service
@Transactional
public class BaixaServiceImpl implements BaixaService {
	
	@Autowired
	private BaixaRepository baixaRepository;

	@Override
	public void saveOrUpdate(Baixa baixa) {
		baixaRepository.save(baixa);
	}

	@Override
	public void deleteLogic(Long id) {
		Baixa baixa = baixaRepository.findOne(id);
		//baixa.setStatus("I");
		saveOrUpdate(baixa);
	}

	@Override
	public Baixa findById(Long id) {
		return baixaRepository.findOne(id);
	}

	@Override
	public List<Baixa> findAll() {
		return baixaRepository.findAll();
	}
	

	@Override
	public List<ItemBaixa> findByDates(Date dataInicio, Date dataFim) {
		return baixaRepository.findByDates(dataInicio, dataFim);
	}

}