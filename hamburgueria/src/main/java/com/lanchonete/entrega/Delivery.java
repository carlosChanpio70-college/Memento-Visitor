package com.lanchonete.entrega;

import com.lanchonete.pedido.Pedido;

public class Delivery extends Entrega {

    private static final double TAXA_ENTREGA = 8.00;

    @Override
    protected void prepararEntrega(Pedido pedido) {
        System.out.println("[DELIVERY] Pedido recebido para entrega.");
    }

    @Override
    protected void executarLogistica(Pedido pedido) {
        System.out.printf(
            "[DELIVERY] Pedido em rota de entrega! Total: R$ %.2f + Taxa: R$ %.2f = R$ %.2f%n",
            pedido.calcularTotal(), TAXA_ENTREGA, pedido.calcularTotal() + TAXA_ENTREGA
        );
        System.out.println("[DELIVERY] Itens: " + pedido.descricao());
    }

    @Override
    protected void emitirNotaFiscal(Pedido pedido) {
        super.emitirNotaFiscal(pedido);
        System.out.printf("[DELIVERY] Taxa de entrega aplicada: R$ %.2f%n", TAXA_ENTREGA);
    }

    public double getTaxaEntrega() {
        return TAXA_ENTREGA;
    }
}
