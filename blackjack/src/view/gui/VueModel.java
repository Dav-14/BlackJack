package view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;

import card.Card;
import controller.Controller;
import model.BlackJackProxy;
import model.play.action.type.Action;
import model.play.deck.Hand;
import model.player.Human;
import model.player.Player;
import view.Vue;
import view.gui.panel.ColorGradientPanel;

public class VueModel extends JFrame implements Vue, ActionListener {

    private BlackJackProxy model;
    private JPanel background;
    private Controller controller;

    public VueModel(BlackJackProxy model, Controller controller) {
        this.model = model;
        this.controller = controller;
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("BlackJack");
        this.background = new ColorGradientPanel(Color.BLACK, ColorGradientPanel.DARKGREEN);
        this.actualize(true);

        setVisible(true);
        pack();
    }

    private boolean isItMyTurn()
    {
        return this.model.getCurrentPlayer() instanceof Human;
    }

    public void actualize(boolean anyway) {
    
        if(isItMyTurn() || anyway)
        {
            if (this.getComponentCount() > 1) {
                this.background.removeAll();
                this.background.revalidate();
            }
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.PAGE_END;
            //gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weighty = 1;

            JPanel container = new JPanel(new GridBagLayout());

            Player human = this.model.getCurrentPlayer();
            //double + assurance + split = miser; -> en fait pa besoin


            Map<Set<Card>, List<Action>> allActions = this.model.getAllHandAction(human);

            for(Map.Entry<Set<Card>, List<Action>> entry : allActions.entrySet())
            {
                JPanel actionPanel = new JPanel(new FlowLayout());
                JPanel cardPanel = new JPanel(new FlowLayout());

                for(Card c : entry.getKey())
                {
                    JLabel card = new JLabel(c.getValueStr(), SwingConstants.CENTER);
                        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        card.setPreferredSize(new Dimension(50,30));
                        cardPanel.add(card);
                }
                for(Action a : entry.getValue())
                {
                    String[] ss = a.getClass().getName().split("[.]");
                    String name = ss[ss.length -1];
                    System.out.println(name);
                    JButton action = new JButton(name);
                        actionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                        actionPanel.add(action);
                        action.addActionListener(e -> 
                        {
                            if(isItMyTurn())
                            {
                                this.controller.update(a);
                                container.remove(actionPanel);
                                container.remove(cardPanel);
                            }
                        });
                }
                container.add(cardPanel, gbc);
                container.add(actionPanel, gbc);


            } 
            /*
            Hand<Set<Card>> hands  = this.model.getPlayerHand(human);
            List<Action> actions = this.model.getHandAction(hands.get(0), human);
            for(Card c : hands.get(0))
                {
                    JLabel card = new JLabel(c.getValueStr(), SwingConstants.CENTER);
                    card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    card.setPreferredSize(new Dimension(50,30));

                    cardPanel.add(card);
                }
                for(Action a : actions)
                {   if(a != null)
                    {
                        String[] ss = a.getClass().getName().split("[.]");
                        System.out.println(ss.length);
                        JButton action = new JButton(ss[ss.length-1]);
                        actionPanel.add(action);
                        action.addActionListener(e -> {this.controller.update(a);});
                    }

                }

            
            */



                //container.setBackground(new Color(0,0,0,64));

            
            this.background.add(container);
            
            add(this.background);
            revalidate();
            repaint();
            pack();
                        
        }

    }

    @Override
    public void somethingHasChanged(Object source) {
        this.actualize(false);

    }

    @Override
    public void setModel(BlackJackProxy model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}