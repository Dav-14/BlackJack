package view.gui;

import card.Card;
import model.BlackJackProxy;
import model.play.betzone.BetZone;
import model.play.deck.Hand;
import model.player.Human;
import model.player.Player;
import view.Vue;
import view.gui.panel.LabelAssurance;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * A view of the hand of each player, their bet and insurance.
 */
public class VueHand extends JFrame implements Vue {

    /**
     *
     */

    private JScrollPane background;

    private BlackJackProxy model;

    private BetZone insurance;
    private BetZone betZone;

    public VueHand(BlackJackProxy model) {
        this.setModel(model);
        this.betZone = model.getBetzone();
        this.insurance = model.getInsurance();

        this.setTitle("Mains");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));

        this.actualize();

        this.setLocation(150, 400);
        this.setVisible(true);
    }

    private void actualize() {
        if (this.getComponentCount() > 1) {
            this.removeAll();
            this.validate();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        for (Player p : this.model.getPlayersList()) {
            JLabel playerName = new JLabel(p.getName() + ":");
            JLabel argent = new JLabel("Argent disponible: " + this.model.getMoney(p));
            container.add(playerName, gbc);
            container.add(argent, gbc);
            Hand<Set<Card>> hand = this.model.getPlayerHand(p);
            int count = 1;
            if (p instanceof Human) {
                for (Set<Card> h : hand) {
                    /*
                     * int bt1 = this.insurance.get(p).get(h); int bt2 = this.betZone.get(p).get(h);
                     */
                    int bt1 = 0;
                    int bt2 = 0;
                    JPanel handPanel = new JPanel(new FlowLayout());
                    JLabel h_num = new JLabel("Main numéro :" + count + "  mise : " + bt2);
                    LabelAssurance la = new LabelAssurance(this.model, bt1);
                    la.autoListen();
                    for (Card c : h) {
                        JLabel val = new JLabel(c.getValueStr(), SwingConstants.CENTER);
                        val.setPreferredSize(new Dimension(60, 30));
                        val.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        handPanel.add(val);
                    }
                    container.add(h_num, gbc);
                    container.add(la, gbc);
                    container.add(handPanel, gbc);
                    count++;
                }
            } else {
                for (Set<Card> h : hand) {
                    /*
                     * int bt1 = this.insurance.get(p).get(h); int bt2 = this.betZone.get(p).get(h);
                     */
                    int bt1 = 0;
                    int bt2 = 0;
                    JPanel handPanel = new JPanel();
                    JLabel h_num = new JLabel("Main numéro :" + count +  "  mise : " + bt2);
                    LabelAssurance la = new LabelAssurance(this.model, bt1);
                    la.autoListen();
                    for (int i = 0; i < h.size(); i++) {
                        JLabel val = new JLabel("___", SwingConstants.CENTER);
                        if (i == 0) {
                            val.setText(h.stream().findFirst().get().getValueStr());
                        }
                        val.setPreferredSize(new Dimension(60, 30));
                        val.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        handPanel.add(val);
                    }
                    container.add(h_num, gbc);
                    container.add(la, gbc);
                    container.add(handPanel, gbc);     
                    count++;
                }
            }
            container.add(Box.createVerticalStrut(10));
        }
        for(int i = 0; i < container.getComponentCount(); i++)
        {
            container.getComponent(i).setVisible(true);
        }

        JScrollPane background = new JScrollPane(container);
        background.setVisible(true);
        this.add(background);
        this.revalidate();
        this.repaint();
        this.pack();
    }
    

    private static final long serialVersionUID = 1L;

    @Override
    public void somethingHasChanged(Object source)
    {
        this.actualize();
    }

    @Override
    public void setModel(BlackJackProxy model)
    {
        this.model = model;
    }

}