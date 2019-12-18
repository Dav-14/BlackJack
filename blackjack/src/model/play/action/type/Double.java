package model.play.action.type;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;

import java.util.LinkedHashSet;
import java.util.Set;

public class Double extends Action {
    public Double(BlackJack game, Player player, Set<Card> hand) {
        super(game, player, hand);
    }

    @Override
    public void execute() {
        this.game.assignCardPlayer(this.player, this.game.getDecks().getTopCard(), this.hand);
        int mise = this.game.getBetzone().get(player).get(hand);


        this.game.getBetzone().get(player).put(this.hand,2*mise);//On Mise l'argent dans la BetZone
        this.game.getMoney().put(player,this.game.getMoney().get(player) - mise);//On retire l'argent
        this.game.getPlayerCard(player).setStatus(hand, Hand.HandStatus.WAIT);//On dit que l'action est EN ATTENTE DE UPDATE DE BETZONE
    }

    @Override
    public String toString() {
        return "Double : " + super.toString();
    }
}
