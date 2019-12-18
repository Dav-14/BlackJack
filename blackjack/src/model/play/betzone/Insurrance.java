package model.play.betzone;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;
import util.ModelListener;

import java.util.HashMap;
import java.util.Set;

public class    Insurrance extends BetZone {
    public Insurrance (BlackJack game){
        super(game);
    }

    @Override
    public void update() {//CETTE METHODE SERA EXECUTE A CHAQUE ACTION DE TYPE INSSURANCE =>
        if(this.game.isOver() && this.game.hasCroupierPlayed()){
            int bestCroupierScore = this.game.getHandBestScore(this.game.getPlayerHand(this.game.getCroupier()).get(0));
            if (bestCroupierScore != 21){
                for (Player p : this.game.getPlayersList()) {
                    Hand<Set<Card>> hand = this.game.getPlayerHand(p);
                    hand.stream().filter((Set<Card> miniHand) -> this.getMise(p, miniHand) > 0).forEach(miniHand -> 
                    {
                        this.setMise(p, miniHand, 0);
                        for(ModelListener m : this.list)
                        {
                            m.somethingHasChanged(this.getMise(p, miniHand) + "");
                        }
                    });

                }
            }
        }

    }

    public boolean isSomeOneAssure(){
        for (Player player: this.keySet()) {
            HashMap<Set<Card>,Integer> mapi = this.get(player);
            for (Set<Card> hand: mapi.keySet()){
                if (this.getMise(player,hand)>0){
                    return true;
                }
            }
        }
        return false;
    }
}
