package model;

import card.Card;
import model.play.BlackJack;
import model.play.action.type.Action;
import model.play.betzone.BetZone;
import model.play.deck.Hand;
import model.player.Human;
import model.player.Player;
import util.ModelListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * We can use this class as a proxy for the view.
 */
public class BlackJackProxy implements Cloneable, BlackJackInterfaceGame {

    private BlackJack game;
    private Player human;

    public BlackJackProxy(BlackJack play) {
        this.game = play;
        human = null;
    }
    /*
     * public BlackJack getPlay() { return game; }
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayersList() {
        return this.game.getPlayersList();
    }

    public Player getHumanPlayer()
    {
        if(this.human == null)
        {
            for(Player p : getPlayersList())
            {
                if(p instanceof Human)
                {
                    this.human = p;
                    return p;
                }
            }
        }
         return this.human;
    }


    public void addModelListener(ModelListener m) {this.game.addModelListener(m);}

    public void addBetListener(ModelListener m) {this.game.addBetListener(m);}

    public void addInsuranceListener(ModelListener m) {this.game.addInsuranceListener(m);}

    public void removeModelListener(ModelListener m) {this.game.removeModelListener(m);}

    public void removeInsuranceListener(ModelListener m) {this.game.removeInsuranceListener(m);}

    public void removeBetListener(ModelListener m) {this.game.removeBetListener(m);}

    /**
     * {@inheritDoc}
     */
    @Override
    public BetZone getBetzone() {
        return this.game.getBetzone();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public BetZone getInsurance() {
        return this.game.getInsurance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {return this.game.getCurrentPlayer();}

    @Override
    public void play(Action action) {
        this.game.play(action);
    }


    public Hand<Set<Card>> getPlayerHand(Player p){return this.game.getPlayerHand(p);}

    public int getMoney(Player p){return 0; //this.game.getMoney(p);
    }

    public Map<Set<Card>, List<Action>> getAllHandAction(Player p)
    {
        return this.game.getAllHandAction(this.game, p);
    }


}