package com.lanchonete.observer;

/**
 * Interface PedidoSubject para o padrão Observer.
 * Define o contrato para gerenciar observadores de um pedido.
 */
public interface PedidoSubject {

    /**
     * Adiciona um observador à lista de observadores.
     *
     * @param observer o observador a ser adicionado
     */
    void attach(Observer observer);

    /**
     * Remove um observador da lista de observadores.
     *
     * @param observer o observador a ser removido
     */
    void detach(Observer observer);

    /**
     * Notifica todos os observadores sobre mudanças no pedido.
     */
    void notifyObservers();
}
