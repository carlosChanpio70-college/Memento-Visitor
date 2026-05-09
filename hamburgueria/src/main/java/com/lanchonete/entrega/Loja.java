package com.lanchonete.entrega;

import com.lanchonete.pedido.Pedido;

public class Loja implements Entrega {

    @Override
    public void processar(Pedido p) {
        System.out.printf(
            "[LOJA] Pedido pronto para retirada no balcão! Total: R$ %.2f%n",
            p.calcularTotal()
        );
        System.out.println("[LOJA] Itens: " + p.descricao());
    }
}
