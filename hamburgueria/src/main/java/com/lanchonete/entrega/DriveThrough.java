package com.lanchonete.entrega;

import com.lanchonete.pedido.Pedido;

public class DriveThrough extends Entrega {

    @Override
    protected void prepararEntrega(Pedido pedido) {
        System.out.println("[DRIVE-THROUGH] Pedido liberado para retirada na janela.");
    }

    @Override
    protected void executarLogistica(Pedido pedido) {
        System.out.printf(
            "[DRIVE-THROUGH] Pedido pronto para retirada na janela! Total: R$ %.2f%n",
            pedido.calcularTotal()
        );
        System.out.println("[DRIVE-THROUGH] Itens: " + pedido.descricao());
    }
}
