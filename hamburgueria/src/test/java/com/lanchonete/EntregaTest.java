package com.lanchonete;

import org.junit.jupiter.api.*;

import com.lanchonete.entrega.*;
import com.lanchonete.lanche.decorator.*;
import com.lanchonete.pedido.Pedido;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Estratégias de Entrega")
class EntregaTest {

    // Helpers

    /** Captura System.out durante a execução do bloco. */
    private String capturarSaida(Runnable bloco) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try {
            bloco.run();
        } finally {
            System.setOut(original);
        }
        return baos.toString();
    }

    // Delivery

    @Nested
    @DisplayName("Delivery")
    class DeliveryTest {

        @Test
        @DisplayName("processar deve imprimir tag [DELIVERY]")
        void deveImprimirTagDelivery() {
            Delivery delivery = new Delivery();
            Pedido p = new Pedido(List.of(new Hamburguer()), delivery);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("[DELIVERY]"));
        }

        @Test
        @DisplayName("taxa de entrega deve ser R$8,00")
        void taxaDeEntregaCorreta() {
            assertEquals(8.00, new Delivery().getTaxaEntrega(), 0.001);
        }

        @Test
        @DisplayName("saída deve conter o total do pedido")
        void saidaContemTotal() {
            Delivery delivery = new Delivery();
            Pedido p = new Pedido(List.of(new Hamburguer()), delivery);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("15,00") || saida.contains("15.00"));
        }

        @Test
        @DisplayName("saída deve conter a descrição dos lanches")
        void saidaContemDescricao() {
            Delivery delivery = new Delivery();
            Lanche l = new ExtraQueijo(new Hamburguer());
            Pedido p = new Pedido(List.of(l), delivery);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("Queijo Extra"));
        }
    }

    // DriveThrough

    @Nested
    @DisplayName("DriveThrough")
    class DriveThroughTest {

        @Test
        @DisplayName("processar deve imprimir tag [DRIVE-THROUGH]")
        void deveImprimirTagDriveThrough() {
            DriveThrough dt = new DriveThrough();
            Pedido p = new Pedido(List.of(new Hamburguer()), dt);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("[DRIVE-THROUGH]"));
        }

        @Test
        @DisplayName("saída deve conter o total do pedido")
        void saidaContemTotal() {
            DriveThrough dt = new DriveThrough();
            Lanche l = new ExtraBacon(new Hamburguer());
            Pedido p = new Pedido(List.of(l), dt);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("20,00") || saida.contains("20.00"));
        }

        @Test
        @DisplayName("saída deve conter a descrição do lanche")
        void saidaContemDescricao() {
            DriveThrough dt = new DriveThrough();
            Pedido p = new Pedido(List.of(new Hamburguer()), dt);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("Hambúrguer"));
        }
    }

    // Loja 

    @Nested
    @DisplayName("Loja")
    class LojaTest {

        @Test
        @DisplayName("processar deve imprimir tag [LOJA]")
        void deveImprimirTagLoja() {
            Loja loja = new Loja();
            Pedido p = new Pedido(List.of(new Hamburguer()), loja);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("[LOJA]"));
        }

        @Test
        @DisplayName("saída deve conter o total do pedido")
        void saidaContemTotal() {
            Loja loja = new Loja();
            Pedido p = new Pedido(List.of(new Hamburguer()), loja);
            String saida = capturarSaida(p::processar);
            assertTrue(saida.contains("15,00") || saida.contains("15.00"));
        }
    }

    // Intercâmbio de estratégias (Strategy Pattern)

    @Nested
    @DisplayName("Troca de estratégia em runtime")
    class TrocaEstrategiaTest {

        @Test
        @DisplayName("delivery e loja devem gerar saídas distintas para o mesmo pedido")
        void entregasDistintas() {
            Lanche l = new Hamburguer();

            Pedido pDelivery = new Pedido(List.of(l), new Delivery());
            Pedido pLoja    = new Pedido(List.of(l), new Loja());

            String saidaDelivery = capturarSaida(pDelivery::processar);
            String saidaLoja     = capturarSaida(pLoja::processar);

            assertNotEquals(saidaDelivery, saidaLoja);
        }

        @Test
        @DisplayName("todas as estratégias devem processar sem lançar exceção")
        void todasEstrategiasFuncionam() {
            List<Lanche> lanches = List.of(new Hamburguer());
            assertDoesNotThrow(() -> new Pedido(lanches, new Delivery()).processar());
            assertDoesNotThrow(() -> new Pedido(lanches, new DriveThrough()).processar());
            assertDoesNotThrow(() -> new Pedido(lanches, new Loja()).processar());
        }
    }

    // Transições de Estado com Observer

    @Nested
    @DisplayName("Transições de Estado (Observer Pattern)")
    class EstadoTransicaoTest {

        @Test
        @DisplayName("pedido começa em estado 'Preparando'")
        void pedidoComecaEmPreparando() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            assertEquals("Preparando", p.getEstadoDescricao());
        }

        @Test
        @DisplayName("deve transicionar de Preparando para Em Rota")
        void transicaodePreparandoParaEmRota() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            String saida = capturarSaida(p::colocarEmRota);
            
            assertTrue(saida.contains("🚀") || saida.contains("entrega"));
            assertEquals("Em Rota", p.getEstadoDescricao());
        }

        @Test
        @DisplayName("deve transicionar de Em Rota para Entregue")
        void transicaodeEmRotaParaEntregue() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            p.colocarEmRota();
            
            String saida = capturarSaida(p::entregar);
            
            assertTrue(saida.contains("sucesso") || saida.contains("✅"));
            assertEquals("Entregue", p.getEstadoDescricao());
        }

        @Test
        @DisplayName("não deve permitir ações inválidas no estado")
        void deveRejeitarAcoesInvalidas() {
            Pedido p = new Pedido(List.of(new Hamburguer()), new Loja());
            p.colocarEmRota();
            p.entregar();
            
            String saida = capturarSaida(p::preparar);
            
            assertTrue(saida.contains("❌") || saida.contains("Não é possível"));
        }
    }
}
