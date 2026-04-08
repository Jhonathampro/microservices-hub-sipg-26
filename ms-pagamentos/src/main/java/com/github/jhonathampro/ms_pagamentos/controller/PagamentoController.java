package com.github.jhonathampro.ms_pagamentos.controller;

import com.github.jhonathampro.ms_pagamentos.dto.PagamentoDto;
import com.github.jhonathampro.ms_pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PagamentoDto>> getAll(){
        List<PagamentoDto> pagamentoDTOS = pagamentoService.findAllPagamentos();
        return ResponseEntity.ok(pagamentoDTOS);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> getById(@PathVariable Long id) {
        PagamentoDto pagamentoDTO = pagamentoService.findPagamentoById(id);
        return ResponseEntity.ok(pagamentoDTO);
    }


    private ResponseEntity<PagamentoDto> createPagamento (@RequestBody @Valid PagamentoDto pagamentoDTO) {
        pagamentoDTO = pagamentoService.savePagamento(pagamentoDTO);
        URI uri =
                ServletUriComponentsBuilder
                        .fromCurrentRequestUri()
                        .path("/{id}")
                        .buildAndExpand(pagamentoDTO.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(pagamentoDTO);

    }
}