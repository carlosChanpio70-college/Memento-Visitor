package com.lanchonete.pedido.memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lanchonete.entrega.state.EstadoPedido;
import com.lanchonete.lanche.Lanche;

public class PedidoMomento {

    private final List<Lanche> lanches;
    private final EstadoPedido estado;
    private final double total;

    public PedidoMomento(List<Lanche> lanches, EstadoPedido estado, double total) {
        this.lanches = Collections.unmodifiableList(new ArrayList<>(lanches));
        this.estado = estado;
        this.total = total;
    }

    public List<Lanche> getLanches() {
        return lanches;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public double getTotal() {
        return total;
    }
}
