package model.play.action.type;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;

import java.util.LinkedHashSet;
import java.util.Set;

public class Surrender extends Action{
    public Surrender(BlackJack game, Player player, Set<Card> hand) {
        super(game, player, hand);
    }

    @Override
    public void execute() {
        this.game.getPlayerCard(player).setStatus(hand, Hand.HandStatus.SURREND);
        this.game.getBetzone().get(player).remove(hand);
        this.game.getInsurance().get(player).remove(hand);
        this.game.getPlayerCard(player).update(hand);
    }

    @Override
    public String toString() {
        return "Surrender : " + super.toString();
    }
}
