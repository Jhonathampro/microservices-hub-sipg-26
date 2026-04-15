package com.github.jhonathampro.ms_pagamentos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jhonathampro.ms_pagamentos.dto.PagamentoDto;
import com.github.jhonathampro.ms_pagamentos.entities.Pagamento;
import com.github.jhonathampro.ms_pagamentos.service.PagamentoService;
import com.github.jhonathampro.ms_pagamentos.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;



@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTest {
        @Autowired
        private MockMvc mockMvc; // para chamar o endpoint
        @Autowired
       // converte para JSON objeto Java e envia na requisição
        private ObjectMapper objectMapper;

    @MockitoBean
    private PagamentoService pagamentoService;
    private Pagamento pagamento;
    private Long existingId;
    private Long nonExistingId;
    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
        pagamento = Factory.createPagamento();
    }

    @Test
    void findAllPagamentosShouldReturnListPagamentoDTO() throws Exception {

        // Arrange
        PagamentoDto inputDto = new PagamentoDto(pagamento);
        List<PagamentoDto> list = List.of(inputDto);
        Mockito.when(pagamentoService.findAllPagamentos()).thenReturn(list);
        // Act + Assert
        ResultActions result = mockMvc.perform(get("/pagamentos")
                .accept(MediaType.APPLICATION_JSON)); // request: Accept
        result.andDo(print());
        result.andExpect(status().isOk());
        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        result.andExpect(jsonPath("$").isArray());
        result.andExpect(jsonPath("$[0].id").value(pagamento.getId()));
        result.andExpect(jsonPath("$[0].valor").value(pagamento.getValor().doubleValue()));
// Verify (comportamento)
        Mockito.verify(pagamentoService).findAllPagamentos();
        Mockito.verifyNoMoreInteractions (pagamentoService);
    }

}
