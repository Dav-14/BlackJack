package model.play.action.type;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;

import java.util.LinkedHashSet;
import java.util.Set;


public class Hit extends Action {
    public Hit(BlackJack game, Player player, Set<Card> hand) {
        super(game, player, hand);
    }

    @Override
    public void execute() {
        this.game.assignCardPlayer(player,this.game.getDecks().getTopCard(),hand);
        this.game.getPlayerCard(player).setStatus(hand, Hand.HandStatus.PLAYABLE);//SECURITE LA MAIN EST TOUJOURS JOUABLE SI < 21 ALORS l'action unique sera SURREND ET STAND
    }

    @Override
    public String toString() {
        return "Hit" + super.toString();
    }
}
