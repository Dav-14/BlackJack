package model;

import model.play.action.type.Action;
import model.play.betzone.BetZone;
import model.player.Player;

import java.util.List;

public interface BlackJackInterfaceGame {
    /**
     * Returns a list of all the Players.
     * @return a List of Players.
     */
    List<Player> getPlayersList();

    /**
     * Returns the betzone.
     * @return a betzone
     */
    BetZone getBetzone();

    /**
     * Returns the insurance.
     * @return a betzone
     */
    BetZone getInsurance();

    /**
     * Gets the current player.
     * @return a Player.
     */
    Player getCurrentPlayer();

    void play(Action action);

}
