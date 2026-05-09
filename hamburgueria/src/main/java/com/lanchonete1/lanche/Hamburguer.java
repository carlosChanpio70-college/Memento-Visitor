package com.lanchonete.lanche;

/**
 * Implementação concreta de Lanche que representa um hambúrguer simples.
 * É o "ConcreteComponent" no padrão Decorator.
 */
public class Hamburguer implements Lanche {

    private static final String DESCRICAO = "Hambúrguer";
    private static final double PRECO_BASE = 15.00;

    @Override
    public String descricao() {
        return DESCRICAO;
    }

    @Override
    public double preco() {
        return PRECO_BASE;
    }
}
