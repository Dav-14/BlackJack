package model.play.action.type;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;

import java.util.LinkedHashSet;
import java.util.Set;

public class Stand extends Action {
    public Stand(BlackJack game, Player player, Set<Card> hand) {
        super(game, player, hand);
    }

    @Override
    public void execute() {
        assert (true) : "do nothing";
        this.game.getPlayerCard(player).setStatus(hand, Hand.HandStatus.WAIT);
    }
    @Override
    public String toString() {
        return "Stand : " + super.toString();
    }
}
