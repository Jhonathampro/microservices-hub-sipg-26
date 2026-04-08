package com.github.jhonathampro.ms_pagamentos.service;


import com.github.jhonathampro.ms_pagamentos.dto.PagamentoDto;
import com.github.jhonathampro.ms_pagamentos.entities.Pagamento;
import com.github.jhonathampro.ms_pagamentos.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Transactional(readOnly = true)
    public List<PagamentoDto> findAllPagamentos() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        return pagamentos.stream().map(PagamentoDto::new).toList();
    }

}