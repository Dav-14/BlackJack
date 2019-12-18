package model.play.rule;

import card.Card;
import model.play.action.ActionFactory;
import model.play.action.type.*;
import model.play.BlackJack;
import model.play.action.type.Double;
import model.player.Player;

import java.util.*;

public class Rule{

    public Map<Set<Card>, List<Action>> getAllHandAction(BlackJack game, Player p){

        Map<Set<Card>,List<Action>> handActionList = new HashMap<>();
        game.getPlayerCard(p).stream().forEach(d -> handActionList.put(d,new ArrayList<>()));

        boolean inssurance = actionInssurance(game,p);
        ActionFactory actionFactory = ActionFactory.newInstance(game);

        int bestScore,money,miseInit;
        for (Set<Card> hand: handActionList.keySet()) {
            bestScore = game.getHandBestScore(hand);
            List<Action> actionList = handActionList.get(hand);
            money = game.getMoney(p);
            miseInit = game.getBetzone().getMise(p,hand);
            if (bestScore < 21){
                if(isPair(hand)){
                    actionList.add(actionFactory.createAction(Split.class,p,hand));
                }
                if (inssurance && money >= (miseInit/2)){
                    actionList.add(actionFactory.createAction(Inssurance.class, p, hand));
                }
                if (money >= (2*miseInit)){
                    actionList.add(actionFactory.createAction(Double.class,p,hand));
                }
                actionList.add(actionFactory.createAction(Hit.class, p, hand));
                actionList.add(actionFactory.createAction(Stand.class, p, hand));//Action NE rien faire toujours la Logique !
            }
        }
        return (handActionList.isEmpty()) ? null : handActionList;


    }
    public List<Action> getHandAction(BlackJack game, Player p, Set<Card> hand){

        ActionFactory actionFactory = ActionFactory.newInstance(game);

        boolean inssurance = actionInssurance(game,p);
        int bestScore = game.getHandBestScore(hand);
        List<Action> actionList = new ArrayList();


        int money = game.getMoney(p);
        int miseInit = game.getBetzone().getMise(p,hand);
        if (bestScore < 21){
            if(isPair(hand)){
                actionList.add(actionFactory.createAction(Split.class,p,hand));
            }
            if (inssurance && money >= (miseInit/2)){
                actionList.add(actionFactory.createAction(Inssurance.class, p, hand));
            }
            if (money >= (2*miseInit)){
                actionList.add(actionFactory.createAction(Double.class,p,hand));
            }
            actionList.add(actionFactory.createAction(Hit.class, p, hand));
            actionList.add(actionFactory.createAction(Stand.class, p, hand));//Action NE rien faire toujours la Logique !
        }
        return actionList;
    }

    public static boolean isPair(Set<Card> hand){
        if (hand.size()==2){
            Card card1 = (Card) hand.toArray()[0];
            Card card2 = (Card) hand.toArray()[1];

            if(card1.isPair(card2)){
                return true;
            }

        }
        return false;
    }

    private static boolean actionInssurance(BlackJack game, Player player){
        Set<Card> croupierHAND = game.getPlayerCard(game.getCroupier()).get(0);
        boolean inssurance = false;
        if (croupierHAND.size() <= 2) {
            int bestCroupierfirstCarScore = game.getHandBestScore(new LinkedHashSet<Card>(Arrays.asList((Card) croupierHAND.toArray()[0])));
            if (bestCroupierfirstCarScore == 11){
                inssurance = true;
            }
        }
        return inssurance;
    }

    public Player getWinner(BlackJack game, Player p){
        return null;
    }
}
