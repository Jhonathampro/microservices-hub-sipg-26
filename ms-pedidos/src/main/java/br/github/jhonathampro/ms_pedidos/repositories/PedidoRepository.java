package br.github.jhonathampro.ms_pedidos.repositories;

import br.github.jhonathampro.ms_pedidos.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
