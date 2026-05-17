package com.lanchonete.lanche;

import com.lanchonete.lanche.visitor.LancheVisitor;

/**
 * Interface que representa um lanche no sistema.
 * Faz parte do padrão Decorator como o "Component".
 */
public interface Lanche {

    /**
     * Retorna a descrição do lanche.
     *
     * @return descrição textual do lanche
     */
    String descricao();

    /**
     * Retorna o preço do lanche.
     *
     * @return preço em reais
     */
    double preco();

    /**
     * Aceita um visitante para coletar informações do lanche.
     *
     * @param visitor visitante que irá processar o lanche
     */
    void accept(LancheVisitor visitor);
}
