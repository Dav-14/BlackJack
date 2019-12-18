package model.play.deck;

import deck.Deck;
import deck.DeckDeck;
import util.ListenableModel;
import util.ModelListener;

import java.util.ArrayList;
import java.util.List;

public class ListenableDeckDeck extends DeckDeck implements ListenableModel{

    private List<ModelListener> list = new ArrayList<>();

    public ListenableDeckDeck(Deck deck, boolean autoMixed, boolean autoDeckChange) {
        super(deck, autoMixed, autoDeckChange);
    }

    public ListenableDeckDeck(Deck deck) {
        this(deck,true,true);
    }

    @Override
    public void addModelListener(ModelListener l) {
        this.list.add(l);
    }

    @Override
    public void removeModelListener(ModelListener l) {
        this.list.remove(l);
    }

    public ListenableDeckDeck clone(){
        ListenableDeckDeck clone = (ListenableDeckDeck) super.clone();
        clone.list = new ArrayList<>(this.list);
        return clone;
    }
}
