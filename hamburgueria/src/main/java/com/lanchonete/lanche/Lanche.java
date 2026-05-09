package com.lanchonete.lanche;

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
}
