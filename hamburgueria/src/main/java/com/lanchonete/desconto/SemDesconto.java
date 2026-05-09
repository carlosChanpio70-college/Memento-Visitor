package com.lanchonete.desconto;

import com.lanchonete.pedido.Pedido;

public class SemDesconto extends DescontoHandler {

    @Override
    public double calcularDesconto(Pedido p) {
        return 0.0;
    }
}
