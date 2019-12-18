package model.play.action.type;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Split extends Action {

    public Split(BlackJack game, Player player, Set<Card> hand) {
        super(game, player, hand);
    }

    @Override
    public void execute() {
        //TODO: A FAIRE par rapport a la main donné et VERIFICATION !
        Object[] array = hand.toArray();

        assert (array.length == 2) : "On ne peut pas split une main de 3";

        Card card1 = (Card) array[0];
        Card couple1 = this.game.getDecks().getTopCard();

        Card card2 = (Card) array[1];
        Card couple2 = this.game.getDecks().getTopCard();

        Hand hands = this.game.getPlayerCard(player);

        hands.remove(this.hand);//On suprime la main courante
        hands.status.remove(this.hand);

        LinkedHashSet<Card> hand1 = new LinkedHashSet(Arrays.asList(card1,couple1));
        LinkedHashSet<Card> hand2 = new LinkedHashSet(Arrays.asList(card2,couple2));

        hands.addHand(hand1, Hand.HandStatus.PLAYABLE);
        hands.addHand(hand2, Hand.HandStatus.PLAYABLE);

        int mise1 = this.game.getBetzone().getMise(player,this.hand);
        this.game.getBetzone().get(player).remove(hand);
        this.game.getBetzone().setMise(player,hand1,mise1);
        this.game.getBetzone().setMise(player,hand2,mise1);

        this.game.getInsurance().get(player).remove(this.hand);
        this.game.getInsurance().setMise(player,hand1,0);
        this.game.getInsurance().setMise(player,hand2,0);

        this.game.setMoney(player,this.game.getMoney(player)-mise1);//On retire la mise de SON argent


    }
    @Override
    public String toString() {
        return "Split : " + super.toString();
    }
}
