package pl.uo.lambdy;

/**
 * Reguła cenowa: zamienia cenę na nową cenę. Interfejs z wykładu – nie zmieniaj go.
 *
 * Uwaga: w ćwiczeniu dla prostoty używamy double. W prawdziwym kodzie kwot pieniężnych
 * używa się BigDecimal, bo double nie przechowuje dokładnie wartości takich jak 0,1.
 */
@FunctionalInterface
public interface PriceRule {

    double apply(double price);

    /** Zwraca regułę, która najpierw stosuje tę regułę, a potem regułę next. */
    default PriceRule then(PriceRule next) {
        return price -> next.apply(apply(price));
    }

    /** Reguła, która nie zmienia ceny – punkt startowy przy składaniu reguł. */
    static PriceRule identity() {
        return price -> price;
    }
}
