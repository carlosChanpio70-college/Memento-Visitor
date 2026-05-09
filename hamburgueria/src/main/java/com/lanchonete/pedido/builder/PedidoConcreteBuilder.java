package com.lanchonete.pedido.builder;

import java.util.Arrays;

import com.lanchonete.entrega.Loja;
import com.lanchonete.lanche.Combo;
import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.lanche.Lanche;
import com.lanchonete.pedido.Pedido;

/**
 * Builder concreto que monta diferentes tipos de pedido.
 */
public class PedidoConcreteBuilder implements PedidoBuilder {

    private Pedido pedido;

    public PedidoConcreteBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.pedido = new Pedido(Arrays.asList(new Hamburguer()), new Loja());
    }

    @Override
    public void buildSanduiche1() {
        Lanche hamburguer = new Hamburguer();
        this.pedido = new Pedido(Arrays.asList(hamburguer), new Loja());
    }

    @Override
    public void buildSanduiche2() {
        Lanche combo = new Combo("Combo Especial", 25.00);
        this.pedido = new Pedido(Arrays.asList(combo), new Loja());
    }

    @Override
    public Pedido getPedido() {
        Pedido builtPedido = this.pedido;
        reset();
        return builtPedido;
    }
}
