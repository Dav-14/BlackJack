package model.play.action;

import card.Card;
import model.play.BlackJack;
import model.play.action.type.Action;
import model.player.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ActionFactory {

    private BlackJack game;


    private ActionFactory(BlackJack game){
        this.game = game;
    }

    public static ActionFactory newInstance(BlackJack game){
        return new ActionFactory(game);
    }

    public <T extends Action> Action newAction(Class<T> c, Player player, Set<Card> hand) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] types = {BlackJack.class, Player.class, Set.class};
        Constructor constructor = c.getConstructor(types);
        Object[] params = {this.game,player,hand};
        Object action = constructor.newInstance(params);
        return (Action) action;
    }

    public <T extends Action> Action createAction(Class<T> objClass, Player player, Set<Card> hand){
        Action act = null;
        try {
            act = newAction(objClass,player,hand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return act;
    }
}
