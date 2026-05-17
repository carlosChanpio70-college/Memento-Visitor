package com.lanchonete.lanche.decorator;

import com.lanchonete.lanche.Lanche;
import com.lanchonete.lanche.visitor.LancheVisitor;

/**
 * Decorator concreto que adiciona bacon extra ao lanche.
 */
public class ExtraBacon extends IngredientesExtraDecorator {

    private static final String DESCRICAO_EXTRA = ", Bacon Extra";
    private static final double PRECO_EXTRA = 5.00;

    public ExtraBacon(Lanche c) {
        super(c);
    }

    @Override
    public String descricao() {
        return componente.descricao() + DESCRICAO_EXTRA;
    }

    @Override
    public double preco() {
        return componente.preco() + PRECO_EXTRA;
    }

    @Override
    public void accept(LancheVisitor visitor) {
        visitor.visitExtraBacon(this);
        componente.accept(visitor);
    }
}
