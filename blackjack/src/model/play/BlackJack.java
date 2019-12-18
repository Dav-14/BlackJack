package model.play;

import card.Card;
import deck.DeckDeck;
import deck.DeckManipulation;
import model.BlackJackInterfaceGame;
import model.play.action.type.Action;
import model.play.action.type.Inssurance;
import model.play.betzone.Bet;
import model.play.betzone.BetZone;
import model.play.betzone.Insurrance;
import model.play.deck.Hand;
import model.play.deck.ListenableDeckDeck;
import model.play.rule.Rule;
import model.player.Croupier;
import model.player.IA;
import model.player.Player;
import util.AbstractListenableModel;
import util.ModelListener;

import java.util.*;


//IMPLEMENTATION DE LA VERSION AMERICAINE
        // NE CHANGE QUE L'ORDRE DES ACTION POSSIBLE
        // POSSIBILITE DE CREER UN CLASSE RULE pour deleguer la creation des actions

public class BlackJack extends AbstractListenableModel implements Cloneable, BlackJackInterfaceGame {

    private int nbPlayers;
    private List<Player> players;
    private DeckManipulation decks; // a fixer (paquet de paquet)
    private BetZone insurance, betzone;
    private HashMap<Player, Hand<Set<Card>>> playersCard;


    private HashMap<Player,Integer> money;
    private Player currentPlayer;
    private Croupier croupier;
    private int currentPlayerIndex;

    public Rule getRule() {
        return rule;
    }

    private Rule rule;

    private ArrayList<ModelListener> insuranceListeners;
    private ArrayList<ModelListener> modelListeners;
    private ArrayList<ModelListener> betListeners;

    public Croupier getCroupier() {
        return croupier;
    }

    public BlackJack(ListenableDeckDeck decks, List<Player> players) throws Exception {
        super();

        assert (players.size() >= 1 && players.size() <= 7) : "You can't play with no one or more than 7 !"; //Implementé une BD en JSon pour plusieurs LANGUES !!
        assert (!decks.isEmpty()) : "DECKS VIDE ... = > Creer un DECKS PLEIN avant de creer un BLACKJACK";

        this.rule = new Rule();
        this.decks = decks;
        this.nbPlayers = players.size();
        this.players = players;

        this.currentPlayer = players.get(0);
        this.currentPlayerIndex = 0;

        this.playersCard = new HashMap<>();
        this.croupier = new Croupier();

        //NE PAS CHANGER L'ORDRE DE CE BLOCK
        //----------------------------------------
        //On init les PLAYERS en PREMIER
        this.players.stream().forEach(p -> initPlayerCards(p));
        this.money = new HashMap<>();//LE CROUPIER N'est DONC PAS DANS LA MAP MONEY -> Ce que nous voulons, puisque les betzones representant le croupier s'autogere et gere la map money
        this.players.stream().forEach(p -> this.money.put(p,200));//ON DONNE 200 A CHAQUE JOUEUR
        this.insurance = new Insurrance(this); this.betzone = new Bet(this);


        Hand<Set<Card>> hand = new Hand(new LinkedHashSet());
        this.playersCard.put((Player) this.croupier,hand);
        assignCardPlayer(this.croupier,this.decks.getTopCard(), 0);
        assignCardPlayer(this.croupier,this.decks.getTopCard(), 0);
        hand.setStatus(hand.get(0),Hand.HandStatus.PLAYABLE);
        //--------------------------------------

        this.insuranceListeners = new ArrayList<>();
        this.betListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
    }

    public boolean hasCroupierPlayed(){

        //TODO: METTRE UNE METHODE GET STATUS, C'est HORRIBLE LA ....
        if (this.getPlayerHand(this.croupier).status.get(this.getPlayerCard(this.croupier).get(0)).equals(Hand.HandStatus.PLAYABLE)){
            return false;
        }
        return true;
    }
    public int getHiddenCroupierScore(){
        Set<Card> croupierhand = this.getPlayerHand(this.croupier).get(0);
        Card c = null;
        for (Card card: croupierhand) {
            if(card != null){
                c = card;
                break;
            }
        }
        return this.getHandBestScore(new HashSet<>(Arrays.asList(c)));
    }

    public int getMoney(Player player) {
        return money.get(player);
    }

    public HashMap<Player, Integer> getMoney() {
        return money;
    }


    public void setMoney(Player p, int money){
        this.money.put(p,money);
    }


    private void initPlayerCards(Player player){
            Hand<Set<Card>> hand = new Hand();
            this.playersCard.put(player,hand);
            assignCardPlayer(player,this.decks.getTopCard(), 0);
            assignCardPlayer(player,this.decks.getTopCard(), 0);
            hand.setStatus(hand.get(0),Hand.HandStatus.PLAYABLE);
    }

    @Override
    public List<Player> getPlayersList() {
        return players;
    }

    public void assignCardPlayer(Player player, Card card, int indexHand){
        this.playersCard.get(player).addToHand(this.playersCard.get(player).get(indexHand),card);
    }

    public void assignCardPlayer(Player player, Card card, Set<Card> hand){
        int index = this.playersCard.get(player).indexOf(hand);
        assignCardPlayer(player,card,index);
    }

    /**
     * createAction(actionFactory, Hit.class, player, hand, 0)
     * @param actionFactory actionFactory Builder
     * @param objClass .class Object who need to be created
     * @param player player who need to play
     * @param hand player'hand selected to play => Need to be PLAYABLE
     * @param mise as a Default value in each Action && can be set
     * @param <T> instance of Action
     * @return Anonymous Action determinable by ToString();
     */
    /**
    public Map<Set<Card>,List<Action>> getActionPlayerStatus(Player player){

        //TODO : Quels Actions action sont disponibles a CE MOMENT du jeu pour le joueur PLAYER ?

        ActionFactory actionFactory = ActionFactory.newInstance(this);

        Map<Set<Card>,List<Action>> handActionList = new HashMap<>();
        this.getPlayerCard(player).stream().forEach(d -> handActionList.put(d,new ArrayList<>()));

        Set<Card> croupierHAND = this.getPlayerCard(this.croupier).get(0);

        boolean inssurance = false;
        if (croupierHAND.size() <= 2) {
            int bestCroupierfirstCarScore = this.getHandBestScore(new LinkedHashSet<Card>(Arrays.asList((Card) croupierHAND.toArray()[0])));
            if (bestCroupierfirstCarScore == 11){
                inssurance = true;
            }
        }

        int bestScore;
        for (Set<Card> hand: handActionList.keySet()) {
            bestScore = this.getHandBestScore(hand);
            List actionList = handActionList.get(hand);


            if (inssurance){
                actionList.add(createAction(actionFactory, Inssurance.class, player, hand));
            }
            //TODO : suite de IF qui check la main du croupier

            if (bestScore < 21){
                actionList.add(createAction(actionFactory, Hit.class, player, hand));
                actionList.add(createAction(actionFactory, Double.class, player, hand));




                actionList.add(createAction(actionFactory, Stand.class, player, hand));//Action NE rien faire toujours la Logique !
            }
        }

        return handActionList;
    }**/

    private void executeAction(Action action){
        action.execute();
        if (action instanceof Inssurance){
            this.insurance.update();
        }
        this.getPlayerHand(action.getPlayer()).update(action.getHand());
    }

    public Hand<Set<Card>> getPlayerCard(Player player){
        return this.playersCard.get(player);
    }

    /**
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }**/


    public ArrayList<Integer> getCardScoreList(Set<Card> hand){

        ArrayList<Integer> scores = new ArrayList<>();
        Iterator<Card> iterCard = hand.iterator();
        scores.add(0);
        while (iterCard.hasNext()){
            Card c = iterCard.next();
            int size = scores.size();
            for (int i=0; i<size; i++){
                if (c.isHasSecondValue()){
                    scores.add(scores.get(i) + c.getValueInt()[1]);
                }
                scores.set(i,scores.get(i) + c.getValueInt()[0]);
            }
        }
        return scores;
    }

    //TODO : ATTENTION A QUE CETTE METHODE fonctionne bien avec une LinkedHasSet en guise de MAIN qui est supposé garder l'ordre;
    public int getHandBestScore(Set<Card> hand){
        ArrayList<Integer> res = getCardScoreList(hand);
        return res.get(res.size()-1);
    }

    public ArrayList<Integer> getCardScoreList(Player player){

        ArrayList<Integer> scores = new ArrayList();
        this.playersCard.get(player).stream().forEach(d -> scores.addAll(this.getCardScoreList(d)));
        return scores;
    }

    public int getNbPlayers() {return nbPlayers;}
    public int getCurrentPLayerIndex() {return this.currentPlayerIndex;}
    public BetZone getBetzone(){return this.betzone;}
    public BetZone getInsurance(){return this.insurance;}
    public Player getCurrentPlayer(){return this.currentPlayer;}
    public Hand<Set<Card>> getPlayerHand(Player p){return this.playersCard.get(p);}
    public void setDecks(DeckDeck decks) {this.decks = decks;}
    public void setNbPlayers(int nbPlayers) {this.nbPlayers = nbPlayers;}
    public void setCurrentPlayerIndex(){this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.nbPlayers;}
    public void setCurrentPlayer(Player player){this.currentPlayer = player;}
    public void setBetzone(BetZone bz){this.betzone = bz;}
    public void setInsurrance(BetZone ins){this.insurance = ins;}
    public void setRule(Rule r){this.rule = r;}

    public void addModelListener(ModelListener m){this.modelListeners.add(m);}
    public void addInsuranceListener(ModelListener m){this.insurance.addModelListener(m);}
    public void addBetListener(ModelListener m){this.betzone.addModelListener(m);}

    public void removeModelListener(ModelListener m){this.modelListeners.remove(m);}
    public void removeInsuranceListener(ModelListener m){this.insurance.removeModelListener(m);}
    public void removeBetListener(ModelListener m){this.betzone.removeModelListener(m);}


    public DeckManipulation getDecks() {
        return decks;
    }

    public Player getWinner(BlackJack game, Player p) {
        return this.rule.getWinner(game,p);
    }


    public Map<Set<Card>, List<Action>> getAllHandAction(BlackJack game, Player p) {
        return this.rule.getAllHandAction(this,p);
    }

    public List<Action> getHandAction(Player p, Set<Card> hand) {
        return this.rule.getHandAction(this,p,hand);
    }

    public boolean isOver(){
        for (Player p: this.players) {
            if (!this.getPlayerCard(p).status.values().stream().findFirst().filter(d -> d.equals(Hand.HandStatus.PLAYABLE)).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public Player nextPlayer(Player p){
        int i = this.getPlayersList().indexOf(p);

        if (i+1 == this.getPlayersList().size()){
            return this.getPlayersList().get(0);
        }else {
            return this.getPlayersList().get(i+1);
        }
    }

    public boolean hasPlayedAll(Player player){
        Hand<Set<Card>> hand = this.getPlayerHand(player);
        for (Set<Card> miniHand: hand) {
            if (!hand.getStatus(miniHand).equals(Hand.HandStatus.WAIT)){
                return false;
            }
        }
        return true;
    }

    public void play(Action action){//SERA TOURJOUR LANCER AVEC UNE ACTION GENERE PAR UN HUMAN
        boolean isOver = isOver();

        if (!isOver){
            this.executeAction(action);
            if (hasPlayedAll(action.getPlayer())){
                this.currentPlayer = nextPlayer(action.getPlayer());
            }
            if (this.currentPlayer instanceof IA){
                playNonHumanMoves(this.currentPlayer);
            }
        }

        if(!this.hasCroupierPlayed() && isOver){
            playNonHumanMoves(this.croupier);
        }
        if(isOver && this.hasCroupierPlayed()){
            this.getBetzone().update();
        }
    }

    private void playNonHumanMoves(Player p){
        if (p instanceof Croupier || p instanceof IA){

            Hand<Set<Card>> handi = this.getPlayerHand(p);

            boolean bool = true;
            while (bool){
                for (Set<Card> miniHand: handi) {
                    if (!handi.getStatus(miniHand).equals(Hand.HandStatus.WAIT)){
                        this.executeAction(p.chooseMove(this,miniHand));
                    }
                }
                if (handi.status.values().contains(Hand.HandStatus.PLAYABLE)){
                    bool = true;
                 }else {
                    bool = false;
                }

            }
        }
    }

}