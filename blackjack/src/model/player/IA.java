package model.player;

import card.Card;
import model.play.BlackJack;
import model.play.action.type.Action;
import model.play.action.type.Double;
import model.play.action.type.Hit;
import model.play.action.type.Stand;
import model.play.betzone.Insurrance;
import model.play.rule.Rule;

import java.util.List;
import java.util.Set;

public class IA implements Player{

    @Override
    public String getName() {
        return Integer.toString(this.hashCode());
    }

    @Override
    public Action chooseMove(BlackJack bj, Set<Card> hand) {

        List<Action> actionList = bj.getHandAction(this,hand);

        int croupierScore;
        if (((Insurrance)bj.getInsurance()).isSomeOneAssure()){
            croupierScore = bj.getHandBestScore(bj.getPlayerHand(bj.getCroupier()).get(0));
        }else {
            croupierScore = bj.getHiddenCroupierScore();
        }

        int bestScore = bj.getHandBestScore(hand);

        Action act = null;
        if (Rule.isPair(hand)){
            act = actionList.stream().findFirst().filter(d -> d instanceof Double).get();
            if (act != null){
                return act;
            }
        }
        if(bestScore>17){
            act = actionList.stream().findFirst().filter(d -> d instanceof Stand).get();
            assert (act != null && act instanceof Stand) : "Act must be a STAND";
            return act;
        }
        else if (bestScore>=croupierScore /**&& bestScore <= 17**/){
            if (croupierScore <= 11){
                if (bestScore == 10){
                    act = actionList.stream().findFirst().filter(d -> d instanceof Double).get();
                    if (act != null){
                        return act;
                    }
                }else {
                    act = actionList.stream().findFirst().filter(d -> d instanceof Hit).get();
                    if (act != null){
                        return act;
                    }
                }
            }else if (bestScore<13){//==>CroupierBestScore > 12 => 2 Carte => IL VA HIT Donc possible autre carte opu debordement
                act = actionList.stream().findFirst().filter(d -> d instanceof Double).get();
                if (act != null){
                    return act;
                }
            }else {
                act = actionList.stream().findFirst().filter(d -> d instanceof Hit).get();
                if (act != null){
                    return act;
                }
            }

        }else {
            act = actionList.stream().findFirst().filter(d -> d instanceof Hit).get();
            if (act != null){
                return act;
            }
        }
        return act;
    }
}
