package com.lanchonete;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.lanchonete.entrega.Delivery;
import com.lanchonete.entrega.Loja;
import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.lanche.Lanche;
import com.lanchonete.lanche.decorator.ExtraBacon;
import com.lanchonete.lanche.decorator.ExtraQueijo;
import com.lanchonete.pedido.Pedido;

@DisplayName("Testes de Pedido")
class PedidoTest {

    @Nested
    @DisplayName("Construção do Pedido")
    class ConstructorTest {

        @Test
        @DisplayName("deve criar pedido com um lanche")
        void deveCriarPedidoSimples() {
            Lanche l = new Hamburguer();
            Pedido p = new Pedido(List.of(l), new Loja());
            assertNotNull(p);
        }

        @Test
        @DisplayName("deve lançar exceção para lista de lanches nula")
        void deveLancarExcecaoListaNula() {
            assertThrows(IllegalArgumentException.class,
                () -> new Pedido(null, new Loja()));
        }

        @Test
        @DisplayName("deve lançar exceção para lista de lanches vazia")
        void deveLancarExcecaoListaVazia() {
            assertThrows(IllegalArgumentException.class,
                () -> new Pedido(Collections.emptyList(), new Loja()));
        }

        @Test
        @DisplayName("deve lançar exceção para entrega nula")
        void deveLancarExcecaoEntregaNula() {
            assertThrows(IllegalArgumentException.class,
                () -> new Pedido(List.of(new Hamburguer()), null));
        }
    }

    @Nested
    @DisplayName("calcularTotal()")
    class CalcularTotalTest {

        @Test
        @DisplayName("pedido com um hambúrguer deve totalizar R$15,00")
        void totalUmHamburguer() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            assertEquals(15.00, p.calcularTotal(), 0.001);
        }

        @Test
        @DisplayName("pedido com dois hambúrgueres deve totalizar R$30,00")
        void totalDoisHamburgueres() {
            Pedido p = new Pedido(List.of(new Hamburguer(), new Hamburguer()), new Loja());
            assertEquals(30.00, p.calcularTotal(), 0.001);
        }

        @Test
        @DisplayName("pedido com hambúrguer + queijo deve totalizar R$18,00")
        void totalHamburguerComQueijo() {
            Lanche l = new ExtraQueijo(new Hamburguer());
            Pedido p = new Pedido(List.of(l), new Loja());
            assertEquals(18.00, p.calcularTotal(), 0.001);
        }

        @Test
        @DisplayName("pedido com hambúrguer + queijo + bacon deve totalizar R$23,00")
        void totalHamburguerComQueijoEBacon() {
            Lanche l = new ExtraBacon(new ExtraQueijo(new Hamburguer()));
            Pedido p = new Pedido(List.of(l), new Loja());
            assertEquals(23.00, p.calcularTotal(), 0.001);
        }

        @Test
        @DisplayName("pedido misto deve somar corretamente")
        void totalPedidoMisto() {
            Lanche l1 = new Hamburguer();                          // 15.00
            Lanche l2 = new ExtraQueijo(new Hamburguer());         // 18.00
            Lanche l3 = new ExtraBacon(new ExtraQueijo(new Hamburguer())); // 23.00
            Pedido p = new Pedido(List.of(l1, l2, l3), new Loja());
            assertEquals(56.00, p.calcularTotal(), 0.001);
        }
    }

    @Nested
    @DisplayName("descricao()")
    class DescricaoTest {

        @Test
        @DisplayName("pedido simples deve retornar descrição do lanche")
        void descricaoSimples() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            assertEquals("Hambúrguer", p.descricao());
        }

        @Test
        @DisplayName("múltiplos lanches devem ser separados por ' | '")
        void descricaoMultiplos() {
            Lanche l1 = new Hamburguer();
            Lanche l2 = new ExtraQueijo(new Hamburguer());
            Pedido p = new Pedido(List.of(l1, l2), new Loja());
            assertEquals("Hambúrguer | Hambúrguer, Queijo Extra", p.descricao());
        }
    }

    @Nested
    @DisplayName("Getters")
    class GettersTest {

        @Test
        @DisplayName("getLanches deve retornar lista imutável")
        void getLanchesImutavel() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            assertThrows(UnsupportedOperationException.class,
                () -> p.getLanches().add(new Hamburguer()));
        }

        @Test
        @DisplayName("getEntrega deve retornar a estratégia configurada")
        void getEntrega() {
            Loja loja = new Loja();
            Pedido p = new Pedido(List.of(new Hamburguer()), loja);
            assertSame(loja, p.getEntrega());
        }

        @Test
        @DisplayName("getLanches deve conter todos os lanches adicionados")
        void getLanchesConteudo() {
            Lanche l1 = new Hamburguer();
            Lanche l2 = new ExtraBacon(new Hamburguer());
            Pedido p = new Pedido(List.of(l1, l2), new Delivery());
            assertEquals(2, p.getLanches().size());
        }
    }
}
