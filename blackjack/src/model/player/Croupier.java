package model.player;

import card.Card;
import model.play.action.type.Action;
import model.play.BlackJack;
import model.play.action.type.Hit;
import model.play.action.type.Stand;

import java.util.List;
import java.util.Set;



public class Croupier implements Player {

    public enum Rule{
        H17,
        S17
    }

    private Rule rule;

    public Croupier(Rule rule){
        this.rule  = rule;
    }

    public Croupier(){
        this(Rule.H17);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public Action chooseMove(BlackJack bj, Set<Card> hand) {
        int bestScore = bj.getHandBestScore(hand);

        List<Action> actionList = bj.getHandAction(this,hand);

        if (bestScore < 17){
            return actionList.stream().filter(d -> d instanceof Hit).findFirst().get();
        }


        if (this.rule.equals(Rule.H17)){
            return actionList.stream().filter(d -> d instanceof Hit).findFirst().get();
        }else {
            return actionList.stream().filter(d -> d instanceof Stand).findFirst().get();
        }
    }
}
