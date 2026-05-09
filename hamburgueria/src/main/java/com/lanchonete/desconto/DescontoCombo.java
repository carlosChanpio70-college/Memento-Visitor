package com.lanchonete.desconto;

import com.lanchonete.pedido.Pedido;

public class DescontoCombo extends DescontoHandler {

    private static final double TAXA_DESCONTO = 0.10;

    public DescontoCombo() {
    }

    @Override
    public double calcularDesconto(Pedido p) {
        if (p.getLanches().size() >= 2) {
            return p.calcularTotal() * TAXA_DESCONTO;
        }
        return proximo != null ? proximo.calcularDesconto(p) : 0.0;
    }
}
