package br.github.jhonathampro.ms_pedidos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 11)
    private String cpf;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Status status;
    // valor calculado
    private BigDecimal valorTotal;

    //relacionamento
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemDoPedido> itens = new ArrayList<>();
    // Posso acessar os items pelos pedidos de forma bidirecional, ou seja permite navegar tanto
    // do pedido para os itens quanto do item para o pedido.

    // No banco de dados a relação é unidimencional, ou seja O Item conhece o Pedido
    // mas o Pedido não precisa conhecer os itens no banco.

    public void calcularValorTotalDoPedido(){
        this.valorTotal = this.itens.stream().map(i -> i.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(i.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
