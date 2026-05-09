package com.lanchonete.pedido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.lanchonete.desconto.DescontoCombo;
import com.lanchonete.desconto.DescontoHandler;
import com.lanchonete.desconto.SemDesconto;
import com.lanchonete.entrega.Entrega;
import com.lanchonete.entrega.state.EstadoPedido;
import com.lanchonete.entrega.state.Preparando;
import com.lanchonete.lanche.Lanche;
import com.lanchonete.observer.Observer;
import com.lanchonete.observer.PedidoSubject;

/**
 * Representa um pedido composto por uma lista de lanches e uma estratégia de entrega.
 * Implementa o padrão Observer como Subject.
 */
public class Pedido implements PedidoSubject {
    private final List<Lanche> lanches;
    private final Entrega entrega;
    private EstadoPedido estadoAtual;   // ← NOVO: estado do pedido
    private final DescontoHandler descontoHandler;
    private final List<Observer> observadores;   // ← NOVO: observadores

    public Pedido(List<Lanche> l, Entrega e) {
        if (l == null || l.isEmpty()) {
            throw new IllegalArgumentException("Um pedido deve ter ao menos um lanche.");
        }
        if (e == null) {
            throw new IllegalArgumentException("Uma estratégia de entrega deve ser informada.");
        }
        this.lanches = Collections.unmodifiableList(l);
        this.entrega = e;
        this.estadoAtual = new Preparando();   // ← NOVO: começa sempre em "Preparando"
        DescontoCombo descontoCombo = new DescontoCombo();
        descontoCombo.setProximo(new SemDesconto());
        this.descontoHandler = descontoCombo;
        this.observadores = new ArrayList<>();   // ← NOVO: inicializa observadores
    }

    /**
     * Calcula o total do pedido somando o preço de todos os lanches.
     *
     * @return valor total em reais
     */
    public double calcularTotal() {
        return lanches.stream()
                .mapToDouble(Lanche::preco)
                .sum();
    }

    /**
     * Retorna a descrição consolidada de todos os lanches do pedido.
     *
     * @return string com todos os itens separados por " | "
     */
    public String descricao() {
        return lanches.stream()
                .map(Lanche::descricao)
                .collect(Collectors.joining(" | "));
    }

    /**
     * Processa o pedido usando a estratégia de entrega configurada.
     */
    public void processar() {
        entrega.processar(this);
    }

    public List<Lanche> getLanches() {
        return lanches;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEstado(EstadoPedido estado) {
        this.estadoAtual = estado;
        notifyObservers();
    }

    public void preparar() {
        estadoAtual.preparar(this);
    }

    public void colocarEmRota() {
        estadoAtual.colocarEmRota(this);
    }

    public void entregar() {
        estadoAtual.entregar(this);
    }

    public String getEstadoDescricao() {
        return estadoAtual.getDescricao();
    }

    public double calcularDesconto() {
        return descontoHandler.calcularDesconto(this);
    }

    public double calcularTotalComDesconto() {
        return calcularTotal() - calcularDesconto();
    }

    @Override
    public void attach(Observer o) {
        if (!observadores.contains(o)) {
            observadores.add(o);
        }
    }

    @Override
    public void detach(Observer o) {
        observadores.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observadores) {
            o.update(this);
        }
    }
}