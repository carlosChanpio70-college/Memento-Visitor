package com.lanchonete.pedido.builder;

/**
 * Diretor do Builder responsável por orquestrar a construção do pedido.
 */
public class PedidoDirectorBuilder {

    public void construirPedido(PedidoBuilder builder) {
        builder.reset();
        builder.buildSanduiche1();
    }

    public void construirPedidoAvancado(PedidoBuilder builder) {
        builder.reset();
        builder.buildSanduiche2();
    }
}
