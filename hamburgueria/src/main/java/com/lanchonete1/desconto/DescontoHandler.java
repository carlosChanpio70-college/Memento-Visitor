package com.lanchonete.desconto;

import com.lanchonete.pedido.Pedido;

public abstract class DescontoHandler {

    protected DescontoHandler proximo;

    public void setProximo(DescontoHandler proximo) {
        this.proximo = proximo;
    }

    public abstract double calcularDesconto(Pedido p);
}
