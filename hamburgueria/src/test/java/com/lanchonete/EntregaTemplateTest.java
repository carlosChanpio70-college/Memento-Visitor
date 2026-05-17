package com.lanchonete;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lanchonete.entrega.Delivery;
import com.lanchonete.entrega.DriveThrough;
import com.lanchonete.entrega.Loja;
import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.pedido.Pedido;

@DisplayName("Testes de Template Method para entrega")
class EntregaTemplateTest {

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

    @Test
    @DisplayName("Delivery deve seguir o template e emitir nota fiscal")
    void deliveryDeveEmitirNotaFiscal() {
        Delivery delivery = new Delivery();
        Pedido pedido = new Pedido(List.of(new Hamburguer()), delivery);

        String saida = capturarSaida(pedido::processar);

        assertTrue(saida.contains("[NOTA FISCAL]"));
        assertTrue(saida.contains("[DELIVERY]"));
    }

    @Test
    @DisplayName("Loja deve seguir o template e exibir informações de retirada")
    void lojaDeveExibirRetirada() {
        Loja loja = new Loja();
        Pedido pedido = new Pedido(List.of(new Hamburguer()), loja);

        String saida = capturarSaida(pedido::processar);

        assertTrue(saida.contains("[LOJA]"));
        assertTrue(saida.contains("Itens:"));
    }

    @Test
    @DisplayName("DriveThrough deve seguir o template e exibir o processo de retirada")
    void driveThroughDeveExibirRetirada() {
        DriveThrough driveThrough = new DriveThrough();
        Pedido pedido = new Pedido(List.of(new Hamburguer()), driveThrough);

        String saida = capturarSaida(pedido::processar);

        assertTrue(saida.contains("[DRIVE-THROUGH]"));
        assertTrue(saida.contains("[NOTA FISCAL]"));
    }
}
