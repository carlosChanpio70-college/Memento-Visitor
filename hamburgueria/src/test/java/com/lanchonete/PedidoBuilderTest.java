package com.lanchonete;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hamburgueria.facade.SistemaRestauranteFacade;
import com.hamburgueria.mediator.CentralOperacoesMediator;
import com.hamburgueria.pedido.Pedido;
import com.hamburgueria.pedido.builder.PedidoBuilder;
import com.hamburgueria.pedido.builder.PedidoConcreteBuilder;
import com.hamburgueria.pedido.builder.PedidoDirectorBuilder;

@DisplayName("Testes de Builder e Facade")
class PedidoBuilderTest {

    @Test
    @DisplayName("PedidoConcreteBuilder deve construir pedido simples")
    void deveConstruirPedidoSimples() {
        PedidoBuilder builder = new PedidoConcreteBuilder();
        builder.buildSanduiche1();
        Pedido pedido = builder.getPedido();

        assertNotNull(pedido);
        assertEquals(1, pedido.getLanches().size());
    }

    @Test
    @DisplayName("PedidoDirectorBuilder deve construir pedido avançado")
    void deveConstruirPedidoAvancado() {
        PedidoDirectorBuilder diretor = new PedidoDirectorBuilder();
        PedidoConcreteBuilder builder = new PedidoConcreteBuilder();

        diretor.construirPedidoAvancado(builder);
        Pedido pedido = builder.getPedido();

        assertNotNull(pedido);
        assertEquals(1, pedido.getLanches().size());
    }

    @Test
    @DisplayName("SistemaRestauranteFacade deve cadastrar e gerenciar pedidos")
    void facadeDeveCadastrarEExecutarPedido() {
        PedidoDirectorBuilder diretor = new PedidoDirectorBuilder();
        SistemaRestauranteFacade facade = new SistemaRestauranteFacade(diretor);
        PedidoConcreteBuilder builder = new PedidoConcreteBuilder();

        facade.cadastrarPedido(builder);

        assertEquals(1, facade.getPedidos().size());
        assertNotNull(facade.getPedidos().get(0));
    }

    @Test
    @DisplayName("CentralOperacoesMediator deve processar entregas")
    void mediatorDeveProcessarEntrega() {
        PedidoDirectorBuilder diretor = new PedidoDirectorBuilder();
        PedidoConcreteBuilder builder = new PedidoConcreteBuilder();
        diretor.construirPedido(builder);

        Pedido pedido = builder.getPedido();
        CentralOperacoesMediator mediator = new CentralOperacoesMediator();

        assertDoesNotThrow(() -> mediator.processarEntrega(pedido, pedido.getEntrega()));
    }
}
