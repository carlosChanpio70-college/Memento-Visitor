package com.lanchonete.entrega.state;

import com.lanchonete.pedido.Pedido;

public class EmRota implements EstadoPedido {
    @Override
    public void preparar(Pedido p) {
        System.out.println("❌ Não é possível: pedido já está em rota.");
    }

    @Override
    public void colocarEmRota(Pedido p) {
        System.out.println("✅ Pedido já está em rota.");
    }

    @Override
    public void entregar(Pedido p) {
        System.out.println("✅ Pedido entregue com sucesso!");
        p.setEstado(new Entregue());
        p.notifyObservers();
    }

    @Override
    public String getDescricao() {
        return "Em Rota";
    }

}