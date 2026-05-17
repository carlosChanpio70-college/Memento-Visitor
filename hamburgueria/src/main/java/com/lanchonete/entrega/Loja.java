package com.lanchonete.entrega;

import com.lanchonete.pedido.Pedido;

public class Loja extends Entrega {

    @Override
    protected void prepararEntrega(Pedido pedido) {
        System.out.println("[LOJA] Pedido pronto para retirada no balcão.");
    }

    @Override
    protected void executarLogistica(Pedido pedido) {
        System.out.printf("[LOJA] Total do pedido para retirada: R$ %.2f%n", pedido.calcularTotal());
        System.out.println("[LOJA] Itens: " + pedido.descricao());
    }
}
