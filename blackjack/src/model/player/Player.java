package model.player;

import card.Card;
import model.play.action.type.Action;
import model.play.BlackJack;

import java.util.Set;

public interface Player {
    String getName();
    Action chooseMove(BlackJack bj, Set<Card> hand);
}
