package com.lanchonete.entrega;

import com.lanchonete.pedido.Pedido;

public class Delivery implements Entrega {

    private static final double TAXA_ENTREGA = 8.00;

    @Override
    public void processar(Pedido p) {
        System.out.printf(
            "[DELIVERY] Pedido em rota de entrega! Total: R$ %.2f + Taxa: R$ %.2f = R$ %.2f%n",
            p.calcularTotal(), TAXA_ENTREGA, p.calcularTotal() + TAXA_ENTREGA
        );
        System.out.println("[DELIVERY] Itens: " + p.descricao());
    }

    public double getTaxaEntrega() {
        return TAXA_ENTREGA;
    }
}
