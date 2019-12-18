import card.Card;
import controller.Controller;
import deck.Deck;
import deck.factory.DeckBuilderType52;
import model.play.BlackJack;
import model.play.action.type.Action;
import model.play.deck.ListenableDeckDeck;
import model.player.Human;
import model.player.Player;
import utils.Couleur;
import utils.Valeur;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }

    /*

        public static Integer[][] valueToTab(Valeur var){
        Integer[][] tab;
        if (var.hasSecondValue) {
            tab = new Integer[1][2];
            tab[0][1] = var.secondValue;
        }else {
            tab = new Integer[1][1];
        }
        tab[0][0] = var.value;
        return tab;
    }

    public static void main(String... agrs) throws Exception {


        //Creation du paquet et d'un paquet de paquet en AUTOMIXED = OFF et AUTORELOAD = OFF
        // = > Utilisation de CurrentSeekDeck => Si deck dispo => le remplace en current sinon false
        // si false => utilisation de reset qui remettra le paquet avec de nouvelle carte
        //TODO : change le reset dans le package DECK pour changer l'origine en une nouvelle copie ! Pour pouvoir continuer les reset a l'infini !
        // FAIT !!
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Human("David") {
            @Override
            public Action chooseMove(BlackJack bj, Set<Card> hand) {
                List<Action> actionList = bj.getHandAction(this,hand);

                if (!actionList.isEmpty()) {

                    actionList.stream().forEach(System.out::println);
                    Scanner scanner = new Scanner(System.in);

                    int str = -1;
                    while (str < 0 && str > actionList.size()) {
                        str = scanner.nextInt();
                    }

                    scanner.close();
                    return actionList.get(0);
                }
                return null;
            }
        });


        DeckBuilderType52 deckb = new DeckBuilderType52();


        Arrays.stream(Couleur.values()).forEach(d -> deckb.addType(d.name));
        Arrays.stream(Valeur.values()).forEach(v -> deckb.addValueStr(v.name));
        Arrays.stream(Valeur.values()).forEach(v -> deckb.addValueInt(valueToTab(v)));
        Deck deck = deckb.build();


        ListenableDeckDeck deckDeck = new ListenableDeckDeck(deck);

        BlackJack game = new BlackJack(deckDeck, playerList);

        Player p = game.getCurrentPlayer();

        Map<Set<Card>,List<Action>> actionMap = game.getAllHandAction(game,p);
        Set<Set<Card>> actionMapKetSet = actionMap.keySet();

        System.out.println(actionMap.get(actionMapKetSet.stream().toArray()[0]));

        }*/
}



