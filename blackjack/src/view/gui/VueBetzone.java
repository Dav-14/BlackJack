package view.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;

import model.BlackJackProxy;
import model.player.Human;
import model.player.Player;
import view.Vue;


/**
 * A view of the Human player bet zone.
 */
public class VueBetzone extends JFrame implements Vue{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BlackJackProxy model;
    private Player human;

    /**
     * 
     * @param model the model it 
     */
    public VueBetzone(BlackJackProxy model)
    {
        this.model = model;
        for(Player p : this.model.getPlayersList())
        {
            if(p instanceof Human)
            {
                this.human = p;
                break;
            }
        }
        this.setTitle("Zone de pari");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 200));
        this.setLocation(100, 400);
        
        
        this.actualize();
        //
        this.pack();
        this.setVisible(true);


    }

    /**
     * Actualize the betzone view with good values.
     */
    public void actualize()
    {
        this.getContentPane().removeAll();

        JPanel container = new JPanel(new FlowLayout());

        JLabel insuranceLabel = new JLabel("<html>Votre Assurance:<br/>0</html>");
            insuranceLabel.setFont(new Font(insuranceLabel.getFont().getName(), Font.PLAIN, 20));
            insuranceLabel.setPreferredSize(new Dimension(150, 200));
        JLabel betzone = new JLabel("<html>Votre mise<br/>0</html>");
            betzone.setFont(new Font(betzone.getFont().getName(), Font.PLAIN, 20));   
            betzone.setPreferredSize(new Dimension(150, 200));

        container.add(insuranceLabel);
        container.add(Box.createHorizontalStrut(50));
        container.add(betzone);


        this.add(container);

        
        this.revalidate();
        this.repaint();
        this.pack();
    }

    @Override
    public void somethingHasChanged(Object source)
    {
        this.actualize();
    }

    @Override
    public void setModel(BlackJackProxy model) {
        this.model = model;
    }

    public static void main(String[] args) {
        VueBetzone vb = new VueBetzone(null);
    }

}
