package com.lanchonete.lanche.decorator;

import com.lanchonete.lanche.Lanche;
import com.lanchonete.lanche.visitor.LancheVisitor;

/**
 * Classe abstrata base do padrão Decorator para ingredientes extras.
 * Envolve um Lanche existente e delega para ele por padrão.
 */
public abstract class IngredientesExtraDecorator implements Lanche {

    protected final Lanche componente;

    public IngredientesExtraDecorator(Lanche c) {
        if (c == null) {
            throw new IllegalArgumentException("Componente não pode ser nulo.");
        }
        this.componente = c;
    }

    @Override
    public String descricao() {
        return componente.descricao();
    }

    @Override
    public double preco() {
        return componente.preco();
    }

    @Override
    public void accept(LancheVisitor visitor) {
        componente.accept(visitor);
    }
}
