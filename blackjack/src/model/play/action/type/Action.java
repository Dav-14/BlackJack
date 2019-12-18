package model.play.action.type;

import card.Card;
import model.play.BlackJack;
import model.player.Player;

import java.util.Set;

public abstract class Action {

    protected BlackJack game;

    public Player getPlayer() {
        return player;
    }

    protected Player player;


    protected Set<Card> hand;

    public Action(BlackJack game, Player player, Set<Card> hand){
        this.game = game;
        this.player = player;
        this.hand = hand;
    }


    public Set<Card> getHand() {
        return hand;
    }

    public abstract void execute();

    @Override
    public String toString() {
        return "Action{" +
                "player=" + player +
                ", hand=" + hand + '}';
    }
}
