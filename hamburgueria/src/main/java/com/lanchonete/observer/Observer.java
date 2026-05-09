package com.lanchonete.observer;

import com.lanchonete.pedido.Pedido;

/**
 * Interface Observer para o padrão Observer.
 * Implementadores serão notificados quando um pedido sofrer alterações.
 */
public interface Observer {

    /**
     * Chamado quando o pedido é atualizado.
     *
     * @param p o pedido que foi atualizado
     */
    void update(Pedido p);
}
