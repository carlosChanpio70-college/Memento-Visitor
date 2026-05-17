package com.lanchonete.entrega;

import com.lanchonete.pedido.Pedido;

public abstract class Entrega {

    public final void processar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo.");
        }
        prepararEntrega(pedido);
        executarLogistica(pedido);
        emitirNotaFiscal(pedido);
    }

    protected void prepararEntrega(Pedido pedido) {
        System.out.println("[ENTREGA] Preparando pedido para envio.");
    }

    protected abstract void executarLogistica(Pedido pedido);

    protected void emitirNotaFiscal(Pedido pedido) {
        System.out.println("[NOTA FISCAL] Nota fiscal emitida para o pedido.");
    }
}
