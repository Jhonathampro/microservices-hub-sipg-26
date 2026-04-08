package com.github.jhonathampro.ms_pagamentos.service;

import com.github.jhonathampro.ms_pagamentos.exceptions.ResourceNotFoundException;
import com.github.jhonathampro.ms_pagamentos.repositories.PagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PagamentosServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Long existingId;
    private Long nonexistingId;

    @BeforeEach
    void setUp(){

        existingId = 1l;
        nonexistingId = Long.MAX_VALUE;
    }

    @Test
    void deletePagamentoByIdShoulDeleteWhenIdExists(){

        Mockito.when(pagamentoRepository.existsById(existingId)).thenReturn(true);

        pagamentoService.deletePagamentoById(existingId);

        Mockito.verify(pagamentoRepository).existsById(existingId);

        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    @DisplayName("deletePagamentoById deveria lançar ResorceNotFoundExpection quando o id não existir")
    void deletePagamentoByIdShouldThroeResoucesNotFoundExpectionWhenIdDoesnotExist(){

        Mockito.when(pagamentoRepository.existsById(nonexistingId)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->{
            pagamentoService.deletePagamentoById(nonexistingId);
                });

        Mockito.verify(pagamentoRepository).existsById(nonexistingId);

        Mockito.verify(pagamentoRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}
