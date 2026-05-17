package com.lanchonete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.lanche.Lanche;
import com.lanchonete.lanche.decorator.ExtraBacon;
import com.lanchonete.lanche.decorator.ExtraQueijo;
import com.lanchonete.lanche.visitor.CalculadoraCaloriasVisitor;
import com.lanchonete.lanche.visitor.RelatorioCozinhaVisitor;

@DisplayName("Testes de Visitor para Lanches")
class LancheVisitorTest {

    @Test
    @DisplayName("Calculadora de calorias deve somar corretamente um hambúrguer simples")
    void deveCalcularCaloriasHamburguerSimples() {
        Lanche hamburguer = new Hamburguer();
        CalculadoraCaloriasVisitor visitor = new CalculadoraCaloriasVisitor();

        hamburguer.accept(visitor);

        assertEquals(300, visitor.getTotalCalorias());
    }

    @Test
    @DisplayName("Calculadora de calorias deve somar extras em decoração composta")
    void deveCalcularCaloriasDeDecoratorComExtras() {
        Lanche lanche = new ExtraBacon(new ExtraQueijo(new Hamburguer()));
        CalculadoraCaloriasVisitor visitor = new CalculadoraCaloriasVisitor();

        lanche.accept(visitor);

        assertEquals(460, visitor.getTotalCalorias());
    }

    @Test
    @DisplayName("Relatório de cozinha deve descrever o lanche montado")
    void deveGerarRelatorioCozinhaComDescricaoCompleta() {
        Lanche lanche = new ExtraQueijo(new Hamburguer());
        RelatorioCozinhaVisitor visitor = new RelatorioCozinhaVisitor();

        lanche.accept(visitor);

        String relatorio = visitor.getRelatorio();
        assertTrue(relatorio.contains("Hambúrguer, Queijo Extra"));
    }
}
