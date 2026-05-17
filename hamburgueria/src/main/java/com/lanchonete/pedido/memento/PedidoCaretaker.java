package com.lanchonete.pedido.memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedidoCaretaker {

    private final List<PedidoMomento> momentos = new ArrayList<>();

    public void adicionarMomento(PedidoMomento momento) {
        if (momento == null) {
            throw new IllegalArgumentException("Momento não pode ser nulo.");
        }
        momentos.add(momento);
    }

    public PedidoMomento buscarMomento(int indice) {
        if (indice < 0 || indice >= momentos.size()) {
            throw new IndexOutOfBoundsException("Índice de momento inválido.");
        }
        return momentos.get(indice);
    }

    public List<PedidoMomento> getMomentos() {
        return Collections.unmodifiableList(momentos);
    }
}
