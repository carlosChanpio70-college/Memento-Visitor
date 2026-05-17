package com.lanchonete.lanche.decorator;

import com.lanchonete.lanche.Lanche;
import com.lanchonete.lanche.visitor.LancheVisitor;

/**
 * Decorator concreto que adiciona queijo extra ao lanche.
 */
public class ExtraQueijo extends IngredientesExtraDecorator {

    private static final String DESCRICAO_EXTRA = ", Queijo Extra";
    private static final double PRECO_EXTRA = 3.00;

    public ExtraQueijo(Lanche c) {
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
        visitor.visitExtraQueijo(this);
        componente.accept(visitor);
    }
}
