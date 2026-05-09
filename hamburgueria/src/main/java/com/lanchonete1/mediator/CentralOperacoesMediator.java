package com.lanchonete1.mediator;

import com.hamburgueria.entrega.Entrega;
import com.hamburgueria.observer.Observer;
import com.hamburgueria.observer.PedidoSubject;
import com.hamburgueria.pedido.Pedido;

/**
 * Mediador que centraliza operações entre pedidos, observadores e entregas.
 */
public class CentralOperacoesMediator {

    public void registrarObservador(PedidoSubject subject, Observer observador) {
        if (subject == null || observador == null) {
            return;
        }
        subject.attach(observador);
    }

    public void processarEntrega(Pedido pedido, Entrega entrega) {
        if (pedido == null || entrega == null) {
            return;
        }
        entrega.processar(pedido);
    }

    public void notificar(Observer observador, String mensagem) {
        if (observador == null || mensagem == null) {
            return;
        }
        System.out.printf("[MEDIADOR] %s%n", mensagem);
    }
}
