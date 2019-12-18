package model.play.deck;

import card.Card;
import util.ListenableModel;
import util.ModelListener;

import java.util.*;

public class ListenableDeck extends deck.Deck implements ListenableModel {

    private List<ModelListener> list = new ArrayList<>();

    public ListenableDeck(LinkedHashSet<String> value, Deque<String> types, LinkedList<Integer[]> valueInt) {
        super(value, types, valueInt);
    }

    public ListenableDeck(ArrayList<Card> deck) {
        super(deck);
    }

    @Override
    public void addModelListener(ModelListener l) {
        this.list.add(l);
    }

    @Override
    public void removeModelListener(ModelListener l) {
        this.list.remove(l);
    }

    @Override
    public ListenableDeck clone(){
        return new ListenableDeck(this.getDecks());
    }
}
