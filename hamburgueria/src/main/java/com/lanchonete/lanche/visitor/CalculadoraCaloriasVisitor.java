package com.lanchonete.lanche.visitor;

import com.lanchonete.lanche.Combo;
import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.lanche.decorator.ExtraBacon;
import com.lanchonete.lanche.decorator.ExtraQueijo;

public class CalculadoraCaloriasVisitor implements LancheVisitor {

    private int totalCalorias;

    @Override
    public void visitHamburguer(Hamburguer hamburguer) {
        totalCalorias += 300;
    }

    @Override
    public void visitCombo(Combo combo) {
        totalCalorias += 650;
    }

    @Override
    public void visitExtraQueijo(ExtraQueijo extraQueijo) {
        totalCalorias += 70;
    }

    @Override
    public void visitExtraBacon(ExtraBacon extraBacon) {
        totalCalorias += 90;
    }

    public int getTotalCalorias() {
        return totalCalorias;
    }
}
