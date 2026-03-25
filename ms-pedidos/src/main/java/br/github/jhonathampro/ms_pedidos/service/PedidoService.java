package br.github.jhonathampro.ms_pedidos.service;

import br.github.jhonathampro.ms_pedidos.dto.ItemDoPedidoDto;
import br.github.jhonathampro.ms_pedidos.dto.PedidoDto;
import br.github.jhonathampro.ms_pedidos.entities.ItemDoPedido;
import br.github.jhonathampro.ms_pedidos.entities.Pedido;
import br.github.jhonathampro.ms_pedidos.entities.Status;
import br.github.jhonathampro.ms_pedidos.exceptions.ResourceNotFoundException;
import br.github.jhonathampro.ms_pedidos.repositories.ItemDoPedidoRepository;
import br.github.jhonathampro.ms_pedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemDoPedidoRepository itemDoPedidoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDto> findAllPedidos(){

        return pedidoRepository.findAll().stream().map(PedidoDto::new).toList();
    }

    @Transactional(readOnly = true)
    public PedidoDto findPedidoById(Long id){

        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recuso não encontrado. ID: " + id)

        );
        return new PedidoDto(pedido);
    }

    @Transactional
    public PedidoDto savePedido(PedidoDto pedidoDto) {

        Pedido pedido = new Pedido();
        pedido.setData(LocalDate.now());
        pedido.setStatus(Status.CRIADO);
        mapDtoToPedido(pedidoDto, pedido);
        pedido.calcularValorTotalDoPedido();
        pedido = pedidoRepository.save(pedido);
        return new PedidoDto(pedido);
    }


    private void mapDtoToPedido(PedidoDto pedidoDTO, Pedido pedido) {

        pedido.setNome(pedidoDTO.getNome());
        pedido.setCpf(pedidoDTO.getCpf());

        for(ItemDoPedidoDto itemDTO : pedidoDTO.getItens()) {
            ItemDoPedido itemPedido = new ItemDoPedido();
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setDescricao(itemDTO.getDescricao());
            itemPedido.setPrecoUnitario(itemDTO.getPrecoUnitario());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }
    }
}
