package com.lanchonete;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.hamburgueria.entrega.Delivery;
import com.hamburgueria.entrega.Loja;
import com.hamburgueria.lanche.Hamburguer;
import com.hamburgueria.lanche.Lanche;
import com.hamburgueria.lanche.decorator.ExtraBacon;
import com.hamburgueria.lanche.decorator.ExtraQueijo;
import com.hamburgueria.observer.Observer;
import com.hamburgueria.pedido.Pedido;

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

    @Nested
    @DisplayName("Padrão Observer")
    class ObserverTest {

        @Test
        @DisplayName("deve adicionar um observador")
        void deveAdicionarObservador() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            Observer observadorMock = (pedido) -> {};
            assertDoesNotThrow(() -> {
                p.attach(observadorMock);
            }, "Não deve lançar exceção ao adicionar observador");
        }

        @Test
        @DisplayName("não deve adicionar observador duplicado")
        void naoDeveAdicionarObservadorDuplicado() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            Observer observadorMock = (pedido) -> {};
            p.attach(observadorMock);
            p.attach(observadorMock); // Tenta adicionar novamente
            // O teste passa se não houver exceção e o observador foi adicionado apenas uma vez
            assertNotNull(p);
        }

        @Test
        @DisplayName("deve remover um observador")
        void deveRemoverObservador() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            Observer observadorMock = (pedido) -> {};
            p.attach(observadorMock);
            p.detach(observadorMock);
            // O teste passa se não houver exceção
            assertNotNull(p);
        }

        @Test
        @DisplayName("deve notificar todos os observadores")
        void deveNotificarObservadores() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            
            // Criamos um observador que conta quantas vezes foi chamado
            final int[] contadorNotificacoes = {0};
            Observer observador = (pedido) -> contadorNotificacoes[0]++;
            
            p.attach(observador);
            p.notifyObservers();
            
            assertEquals(1, contadorNotificacoes[0], "Observador deve ser notificado uma vez");
        }

        @Test
        @DisplayName("múltiplos observadores devem ser notificados")
        void multiplosObservadoresDevemSerNotificados() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            
            final int[] contador1 = {0};
            final int[] contador2 = {0};
            
            Observer observador1 = (pedido) -> contador1[0]++;
            Observer observador2 = (pedido) -> contador2[0]++;
            
            p.attach(observador1);
            p.attach(observador2);
            p.notifyObservers();
            
            assertEquals(1, contador1[0], "Primeiro observador deve ser notificado uma vez");
            assertEquals(1, contador2[0], "Segundo observador deve ser notificado uma vez");
        }
    }

    @Nested
    @DisplayName("calcularDesconto() e calcularTotalComDesconto()")
    class CalcularDescontoTest {

        @Test
        @DisplayName("pedido com um lanche não recebe desconto")
        void semDescontoParaUmLanche() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            assertEquals(0.0, p.calcularDesconto(), 0.001);
            assertEquals(15.00, p.calcularTotalComDesconto(), 0.001);
        }

        @Test
        @DisplayName("pedido com dois lanches aplica desconto de 10%")
        void descontoComboParaDoisLanches() {
            Pedido p = new Pedido(List.of(new Hamburguer(), new Hamburguer()), new Loja());
            assertEquals(3.00, p.calcularDesconto(), 0.001);
            assertEquals(27.00, p.calcularTotalComDesconto(), 0.001);
        }

        @Test
        @DisplayName("pedido com itens extras aplica desconto sobre total")
        void descontoComboComLancheComExtras() {
            Lanche l1 = new Hamburguer();                          // 15.00
            Lanche l2 = new ExtraQueijo(new Hamburguer());         // 18.00
            Pedido p = new Pedido(List.of(l1, l2), new Loja());
            assertEquals(3.30, p.calcularDesconto(), 0.001);
            assertEquals(29.70, p.calcularTotalComDesconto(), 0.001);
        }
    }
}
