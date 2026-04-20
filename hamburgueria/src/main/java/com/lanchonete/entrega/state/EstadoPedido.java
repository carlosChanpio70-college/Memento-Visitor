package com.lanchonete.entrega.state;

import com.lanchonete.pedido.Pedido;

public interface EstadoPedido {
    void preparar(Pedido p);
    void colocarEmRota(Pedido p);
    void entregar(Pedido p);
    String getDescricao();
}