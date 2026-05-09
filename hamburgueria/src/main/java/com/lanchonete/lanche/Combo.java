package com.lanchonete.lanche;

/**
 * Representa um combo de lanche composto por descrição e preço fixo.
 */
public class Combo implements Lanche {

    private final String descricao;
    private final double preco;

    public Combo(String descricao, double preco) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do combo não pode ser vazia.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço do combo não pode ser negativo.");
        }
        this.descricao = descricao;
        this.preco = preco;
    }

    @Override
    public String descricao() {
        return descricao;
    }

    @Override
    public double preco() {
        return preco;
    }
}
