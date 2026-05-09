package com.lanchonete1.pedido.builder;

import com.hamburgueria.pedido.Pedido;

/**
 * Define o contrato do Builder para construir pedidos.
 */
public interface PedidoBuilder {

    void reset();

    void buildSanduiche1();

    void buildSanduiche2();

    Pedido getPedido();
}
