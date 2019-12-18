package controller;

import deck.Deck;
import deck.factory.DeckBuilderType52;
import model.BlackJackProxy;
import model.play.BlackJack;
import model.play.action.type.Action;
import model.play.deck.ListenableDeckDeck;
import model.play.rule.Rule;
import model.player.IA;
import model.player.Player;
import utils.Couleur;
import utils.Valeur;
import view.Vue;
import view.gui.SuperApp;
import view.gui.VueHand;
import view.gui.VueModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class that communicates with both model and views. It sets the model depending on what the view receives.
 */
public class Controller {

    private BlackJack blackJack;
    private ArrayList<Vue> views;
    private SuperApp sp;
    private Rule rule;

    public Controller()
    {
        this.views = new ArrayList<>();
    }

    public void start()
    {
        SuperApp app = new SuperApp(this);
        this.sp = app;

    }

    public void addListenerToModel(Vue view)
    {
        this.blackJack.addModelListener(view);
    }

    public void addAllListenersToModel( ArrayList<Vue> views)
    {
        for(Vue view : views)
        {
            this.addListenerToModel(view);
        }
    }

    public void update(Action action)
    {
        this.blackJack.play(action);
    }

    public void update(ArrayList<Integer> toPass) throws Exception {
        DeckBuilderType52 deckb = new DeckBuilderType52();
            Arrays.stream(Couleur.values()).forEach(d -> deckb.addType(d.name));
            Arrays.stream(Valeur.values()).forEach(v -> deckb.addValueStr(v.name));
            Arrays.stream(Valeur.values()).forEach(v -> deckb.addValueInt(valueToTab(v)));
        Deck deck = deckb.build();
        ListenableDeckDeck deckDeck = new ListenableDeckDeck(deck);

        List<Player> players = new ArrayList<>();

        for(int i = 0; i < toPass.get(0); i++)
        {
            players.add(new IA());
        }


        Collections.shuffle(players); //to randomize the place of the players
        
        this.blackJack = new BlackJack(deckDeck, players);
        this.blackJack.setRule(new Rule());
        BlackJackProxy bjp = new BlackJackProxy(this.blackJack);

        VueHand vueH = new VueHand(bjp);
        VueModel vueM = new VueModel(bjp, this);
        this.views.add(vueM);
        this.views.add(vueH);
        this.blackJack.addBetListener(vueH);
        this.blackJack.addModelListener(vueM);
                

    }

    public void update(String s)
    {
        if(s.equals("start"))
        {
            this.sp.displayParameterChoicePage();
        }
    }


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

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }






    

    
}