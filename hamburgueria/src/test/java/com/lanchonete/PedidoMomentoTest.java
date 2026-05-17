package com.lanchonete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lanchonete.entrega.Loja;
import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.pedido.Pedido;
import com.lanchonete.pedido.memento.PedidoCaretaker;
import com.lanchonete.pedido.memento.PedidoMomento;

@DisplayName("Testes de Memento para Pedido")
class PedidoMomentoTest {

    @Test
    @DisplayName("deve capturar e restaurar o estado do pedido")
    void deveSalvarERestaurarMomento() {
        Pedido pedido = new Pedido(List.of(new Hamburguer()), new Loja());
        PedidoMomento momento = pedido.salvarMomento();

        assertNotNull(momento);
        assertEquals("Preparando", momento.getEstado().getDescricao());
        assertEquals(15.00, momento.getTotal(), 0.001);

        pedido.colocarEmRota();
        pedido.entregar();
        pedido.restaurarMomento(momento);

        assertEquals("Preparando", pedido.getEstadoDescricao());
        assertEquals(15.00, pedido.calcularTotal(), 0.001);
    }

    @Test
    @DisplayName("Caretaker deve armazenar e recuperar momentos")
    void deveGerenciarMomentosComCaretaker() {
        Pedido pedido = new Pedido(List.of(new Hamburguer()), new Loja());
        PedidoCaretaker caretaker = new PedidoCaretaker();
        PedidoMomento momento = pedido.salvarMomento();

        caretaker.adicionarMomento(momento);
        PedidoMomento recuperado = caretaker.buscarMomento(0);

        assertNotNull(recuperado);
        assertEquals(15.00, recuperado.getTotal(), 0.001);
    }
}
