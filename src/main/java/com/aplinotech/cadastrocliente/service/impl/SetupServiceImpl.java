package com.aplinotech.cadastrocliente.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.aplinotech.cadastrocliente.util.instalacao.InstalacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.Setup;
import com.aplinotech.cadastrocliente.repository.SetupRepository;
import com.aplinotech.cadastrocliente.service.SetupService;

@Service
@Transactional
public class SetupServiceImpl implements SetupService {
	
	private static final String CODE_ACTIVE = "890531";
	
	@Autowired
	private SetupRepository configuracaoSistemaRepository;
	@Autowired
	private InstalacaoRepository instalacaoRepository;



	@Override
	public void saveOrUpdate(Setup configuracaoSistema) {
		configuracaoSistemaRepository.save(configuracaoSistema);
	}

	@Override
	public Setup find() {
		List<Setup> list = configuracaoSistemaRepository.findAll();
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	@Override
	public boolean sistemaExpirou() {
		
		Setup setup = find();

		if ( setup == null ) {
			instalacaoRepository.insereSetup(getDataLimiteDeUsoGratis());
			return false;
		} else {

			if (setup.getDataExpiracao().before(new Date())) {

				if (setup.getCodigoAtivacao() == null) {
					return true;
				} else if (!setup.getCodigoAtivacao().equals(CODE_ACTIVE)) {
					return true;
				}

			}

			return false;

		}

	}

	@Override
	public Date getDataLimiteDeUsoGratis() {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, 15);
		return cal.getTime();
	}

}