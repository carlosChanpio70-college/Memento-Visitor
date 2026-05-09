package com.lanchonete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.lanchonete.lanche.Hamburguer;
import com.lanchonete.lanche.Lanche;
import com.lanchonete.lanche.decorator.ExtraBacon;
import com.lanchonete.lanche.decorator.ExtraQueijo;
import com.lanchonete.lanche.decorator.IngredientesExtraDecorator;

@DisplayName("Testes de Lanche e Decorators")
class LancheTest {

    private Lanche hamburguer = new Hamburguer();

    @Nested
    @DisplayName("Hamburguer")
    class HamburguerTest {

        @Test
        @DisplayName("deve retornar descrição correta")
        void deveRetornarDescricaoCorreta() {
            assertEquals("Hambúrguer", hamburguer.descricao());
        }

        @Test
        @DisplayName("deve retornar preço base de R$15,00")
        void deveRetornarPrecoBase() {
            assertEquals(15.00, hamburguer.preco(), 0.001);
        }
    }

    //ExtraQueijo

    @Nested
    @DisplayName("ExtraQueijo")
    class ExtraQueijoTest {

        @Test
        @DisplayName("deve adicionar ', Queijo Extra' à descrição")
        void deveAdicionarDescricao() {
            Lanche comQueijo = new ExtraQueijo(hamburguer);
            assertEquals("Hambúrguer, Queijo Extra", comQueijo.descricao());
        }

        @Test
        @DisplayName("deve somar R$3,00 ao preço base")
        void deveAdicionarPreco() {
            Lanche comQueijo = new ExtraQueijo(hamburguer);
            assertEquals(18.00, comQueijo.preco(), 0.001);
        }

        @Test
        @DisplayName("deve lançar exceção se componente for nulo")
        void deveLancarExcecaoParaComponenteNulo() {
            assertThrows(IllegalArgumentException.class, () -> new ExtraQueijo(null));
        }
    }

    //ExtraBacon

    @Nested
    @DisplayName("ExtraBacon")
    class ExtraBaconTest {

        @Test
        @DisplayName("deve adicionar ', Bacon Extra' à descrição")
        void deveAdicionarDescricao() {
            Lanche comBacon = new ExtraBacon(hamburguer);
            assertEquals("Hambúrguer, Bacon Extra", comBacon.descricao());
        }

        @Test
        @DisplayName("deve somar R$5,00 ao preço base")
        void deveAdicionarPreco() {
            Lanche comBacon = new ExtraBacon(hamburguer);
            assertEquals(20.00, comBacon.preco(), 0.001);
        }

        @Test
        @DisplayName("deve lançar exceção se componente for nulo")
        void deveLancarExcecaoParaComponenteNulo() {
            assertThrows(IllegalArgumentException.class, () -> new ExtraBacon(null));
        }
    }

    //Decorators compostos

    @Nested
    @DisplayName("Decorators compostos (encadeamento)")
    class DecoratorsCompostosTest {

        @Test
        @DisplayName("hamburguer com queijo e bacon deve ter descrição correta")
        void descricaoComQueijoeEBacon() {
            Lanche lanche = new ExtraBacon(new ExtraQueijo(hamburguer));
            assertEquals("Hambúrguer, Queijo Extra, Bacon Extra", lanche.descricao());
        }

        @Test
        @DisplayName("hamburguer com queijo e bacon deve ter preço R$23,00")
        void precoComQueijoEBacon() {
            Lanche lanche = new ExtraBacon(new ExtraQueijo(hamburguer));
            assertEquals(23.00, lanche.preco(), 0.001);
        }

        @Test
        @DisplayName("dois queijos devem acumular preço corretamente")
        void doisQueijosDuplicamOExtra() {
            Lanche lanche = new ExtraQueijo(new ExtraQueijo(hamburguer));
            assertEquals(21.00, lanche.preco(), 0.001);
            assertEquals("Hambúrguer, Queijo Extra, Queijo Extra", lanche.descricao());
        }

        @Test
        @DisplayName("decorator deve ser instância de IngredientesExtraDecorator")
        void deveSerInstanciaDecorator() {
            Lanche lanche = new ExtraQueijo(hamburguer);
            assertInstanceOf(IngredientesExtraDecorator.class, lanche);
        }

        private void assertInstanceOf(Class<IngredientesExtraDecorator> class1, Lanche lanche) {
            assertTrue(class1.isInstance(lanche));
        }
    }
}
