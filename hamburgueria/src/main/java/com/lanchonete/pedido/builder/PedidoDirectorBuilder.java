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

    public void construirPedidoSimples(PedidoBuilder builder) {
        construirPedido(builder);
    }

    public void construirComboFamilia(PedidoBuilder builder) {
        construirPedidoAvancado(builder);
    }
}
