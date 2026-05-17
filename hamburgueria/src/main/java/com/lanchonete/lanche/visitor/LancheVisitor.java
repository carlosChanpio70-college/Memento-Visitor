package com.lanchonete.lanche.visitor;

import com.lanchonete.lanche.Combo;
import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.lanche.decorator.ExtraBacon;
import com.lanchonete.lanche.decorator.ExtraQueijo;

public interface LancheVisitor {
    void visitHamburguer(Hamburguer hamburguer);

    void visitCombo(Combo combo);

    void visitExtraQueijo(ExtraQueijo extraQueijo);

    void visitExtraBacon(ExtraBacon extraBacon);
}
