package com.aplinotech.cadastrocliente.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.Entrada;
import com.aplinotech.cadastrocliente.repository.EntradaRepository;
import com.aplinotech.cadastrocliente.service.EntradaService;

@Service
@Transactional
public class EntradaServiceImpl implements EntradaService {
	
	@Autowired
	private EntradaRepository entradaRepository;

	@Override
	public void saveOrUpdate(Entrada entrada) {
		entradaRepository.save(entrada);
	}

	@Override
	public List<Entrada> findByDates(Date dataInicio, Date dataFim) {
		return entradaRepository.findByDates(dataInicio, dataFim);
	}

}