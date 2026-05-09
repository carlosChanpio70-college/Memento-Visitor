package com.lanchonete.pedido.builder;

import com.lanchonete.pedido.Pedido;

/**
 * Define o contrato do Builder para construir pedidos.
 */
public interface PedidoBuilder {

    void reset();

    void buildSanduiche1();

    void buildSanduiche2();

    Pedido getPedido();
}
