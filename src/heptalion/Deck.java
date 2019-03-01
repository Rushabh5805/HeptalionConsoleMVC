package heptalion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * "Deck" of dominos for Heptalion
 * @author David
 */
public class Deck<E> {
    List<E> deck;

    /**
     * Initialize the deck with every possible pairing of Symbol values
     */
    public Deck() {
        // gets array of all symbols, including blank
        Symbol[] symbols = Symbol.values();
        deck = new ArrayList<>();
        // excluding the blank symbol, which is not valid on a domino
        for (int symIndex = 0; symIndex < symbols.length-1; symIndex++) {
            for (int sym2Index = symIndex; sym2Index < symbols.length-1; sym2Index++) {
                deck.add((E) new Domino(symbols[symIndex], symbols[sym2Index]));
            }
        }
    }

    /**
     * Shuffle the order of the dominoes
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Take the "top" (last) domino from the deck
     * @return the removed domino
     */
    public E draw() {
        int lastIndex = deck.size() - 1;
        E result;
        if (lastIndex < 0) {
            return null;
        }
        result = deck.get(lastIndex);
        deck.remove(lastIndex);
        return result;
    }
}
