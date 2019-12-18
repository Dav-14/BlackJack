package model.play.action.type;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;

import java.util.LinkedHashSet;
import java.util.Set;

public class Inssurance extends Action{
    public Inssurance(BlackJack game, Player player, Set<Card> hand) {
        super(game, player, hand);
    }

    @Override
    public void execute() {
        int lastBet = this.game.getBetzone().get(player).get(hand);
        lastBet = lastBet/2;
        this.game.getInsurance().setMise(player,hand,lastBet);
        this.game.getMoney().put(player,this.game.getMoney().get(player) - lastBet);
        this.game.getPlayerCard(player).setStatus(hand, Hand.HandStatus.WAIT);
    }

    @Override
    public String toString() {
        return "Inssurance : " + super.toString();
    }
}
