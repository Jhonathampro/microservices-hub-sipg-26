package com.github.jhonathampro.ms_pagamentos.service;


import com.github.jhonathampro.ms_pagamentos.dto.PagamentoDto;
import com.github.jhonathampro.ms_pagamentos.entities.Pagamento;
import com.github.jhonathampro.ms_pagamentos.entities.Status;
import com.github.jhonathampro.ms_pagamentos.exceptions.ResourceNotFoundException;
import com.github.jhonathampro.ms_pagamentos.repositories.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Transactional (readOnly = true)
    public PagamentoDto findPagamentoById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Recurso não encontrado. ID: " + id));

        return new PagamentoDto(pagamento);
    }

    @Transactional
    public PagamentoDto savePagamento (PagamentoDto pagamentoDTO) {
        Pagamento pagamento = new Pagamento();
        mapperDtoToPagamento(pagamentoDTO, pagamento);
        pagamento.setStatus (Status.CRIADO);

        pagamento = pagamentoRepository.save(pagamento);
        return new PagamentoDto(pagamento);
    }

    private void mapperDtoToPagamento (PagamentoDto pagamentoDTO, Pagamento pagamento) {
        pagamento.setValor(pagamentoDTO.getValor());
        pagamento.setNome(pagamentoDTO.getNome());
        pagamento.setNumeroCartao (pagamentoDTO.getNumeroCartao());
        pagamento.setValidade(pagamentoDTO.getValidade());
        pagamento.setCodigoSeguranca(pagamentoDTO.getCodigoSeguranca());
        pagamento.setPedidoId(pagamentoDTO.getPedidoId());
    }

    @Transactional
    public PagamentoDto updatePagamento (Long id, PagamentoDto pagamentoDTO) {
        try {
            Pagamento pagamento = pagamentoRepository.getReferenceById(id);
            mapperDtoToPagamento(pagamentoDTO, pagamento);
            pagamento.setStatus(pagamentoDTO.getStatus());
            pagamento = pagamentoRepository.save(pagamento);
            return new PagamentoDto(pagamento);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional
    public void deletePagamentoById(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: + id");
        }
        pagamentoRepository.deleteById(id);
    }




}