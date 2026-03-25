package br.github.jhonathampro.ms_pedidos.controller;

import br.github.jhonathampro.ms_pedidos.dto.PedidoDto;
import br.github.jhonathampro.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedidos(){

        List<PedidoDto> list = pedidoService.findAllPedidos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedido(@PathVariable Long id){

        PedidoDto pedidoDTO = pedidoService.findPedidoById(id);
        return ResponseEntity.ok(pedidoDTO);

    }

    @PostMapping
    public ResponseEntity<PedidoDto> createPedido(@RequestBody @Valid PedidoDto pedidoDto) {

        pedidoDto = pedidoService.savePedido(pedidoDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pedidoDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pedidoDto);
    }

}
