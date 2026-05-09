package com.lanchonete1.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hamburgueria.pedido.Pedido;
import com.hamburgueria.pedido.builder.PedidoBuilder;
import com.hamburgueria.pedido.builder.PedidoDirectorBuilder;

/**
 * Facade que simplifica o uso do sistema de pedidos.
 */
public class SistemaRestauranteFacade {

    private final PedidoDirectorBuilder diretor;
    private final List<Pedido> pedidos = new ArrayList<>();

    public SistemaRestauranteFacade(PedidoDirectorBuilder diretor) {
        if (diretor == null) {
            throw new IllegalArgumentException("Diretor não pode ser nulo.");
        }
        this.diretor = diretor;
    }

    public void cadastrarPedido(PedidoBuilder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("Builder não pode ser nulo.");
        }
        diretor.construirPedido(builder);
        pedidos.add(builder.getPedido());
    }

    public void executarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo.");
        }
        pedido.processar();
    }

    public void cancelarPedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}
