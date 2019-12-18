package model.play.betzone;

import card.Card;
import model.play.BlackJack;
import model.play.deck.Hand;
import model.player.Player;
import util.ModelListener;

import java.util.Set;

public class Bet extends BetZone {
    public Bet(BlackJack game){
        super(game);
    }

    @Override
    public void update() {//UPDATE A LA FIN DU TOUR UNIQUEMENT
        double fact = 3/2;
        if(this.game.isOver() && this.game.hasCroupierPlayed()){
            int bestCroupierScore = this.game.getHandBestScore(this.game.getPlayerHand(this.game.getCroupier()).get(0));
            if (bestCroupierScore == 21){
                int score;
                for (Player p : this.game.getPlayersList()) {
                    Hand<Set<Card>> hand = this.game.getPlayerHand(p);
                    for (Set<Card> miniHand : hand) {
                        score = this.game.getHandBestScore(miniHand);
                        if (score == 21){
                            this.game.setMoney(p,this.game.getMoney(p) + this.getMise(p,miniHand));
                        }
                        this.setMise(p,miniHand,0);
                    }
                }
            }else {
                for (Player p : this.game.getPlayersList()) {
                    Hand<Set<Card>> hand = this.game.getPlayerHand(p);
                    int score;
                    for (Set<Card> miniHand : hand) {
                        score = this.game.getHandBestScore(miniHand);
                        if (score==21 || score > bestCroupierScore){//=> BESTSCORECROUPIER<21 OBLIGATOIRE
                            this.game.setMoney(p, (int) (this.game.getMoney(p) + fact*this.getMise(p,miniHand)));
                        }
                        this.setMise(p,miniHand,0);
                    }
                }
            }
        }
        for(ModelListener m : this.list)
        {
            m.somethingHasChanged(null);
        }
    }
}
