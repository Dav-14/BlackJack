package model.play.betzone;

import card.Card;
import model.play.BlackJack;
import model.player.Player;
import util.ListenableModel;
import util.ModelListener;

import java.util.*;

// TODO: relier les cartes avec un joueur pour avoir la bet zone d'un joueur selon ses mains
// likde -> HashMap<Player, HashMap<Set<Card>, Integer>> 

public abstract class BetZone extends HashMap<Player, HashMap<Set<Card>, Integer>>  implements ListenableModel {

    protected BlackJack game;
    protected List<ModelListener> list;

    public BetZone(BlackJack game){
        super();
        this.list = new ArrayList<>();
        this.game = game;

        for (Player p : this.game.getPlayersList()) {
            HashMap<Set<Card>,Integer> mapi = new HashMap<>();
            this.game.getPlayerCard(p).stream().forEach(d -> mapi.put(d,0));
            this.put(p,mapi);
        }
    }

    @Override
    public void addModelListener(ModelListener l){
        this.list.add(l);
    }

    @Override
    public void removeModelListener(ModelListener l){
        this.list.remove(l);
    }

    public void setMise(Player player, Set<Card> hand, int mise){
        this.get(player).put(hand,mise);
    }

    public int getMise(Player player, Set<Card> hand){
        return this.get(player).get(hand);
    }

    public abstract void update();
}
