package view.gui;

import controller.Controller;
import model.play.BlackJack;

import view.gui.panel.ColorGradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SuperApp extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BlackJack model;
    private Controller controller;
    private JPanel background;

    public SuperApp(Controller controller) 
    {
        this.controller = controller;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("BlackJack");
        this.setSize(950, 900);
        this.setLocationRelativeTo(null);
        this.background = new ColorGradientPanel(Color.BLACK, ColorGradientPanel.DARKGREEN);

        this.displayHomePage();
        this.setVisible(true);

    }

    public void displayHomePage() {
        this.background.removeAll();
        this.background.revalidate();
        this.background.repaint();
        JLabel titleLabel = new JLabel("BLACK JACK");
            titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 60));
            titleLabel.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel container = new JPanel(new GridBagLayout());
            container.setOpaque(false);
            container.add(titleLabel, gbc);
            container.add(Box.createVerticalStrut(270), gbc);
        JButton start = new JButton("Start");
            start.addActionListener(e->this.controller.update("start"));
            container.add(start, gbc);
            container.add(Box.createVerticalStrut(250), gbc);
        JButton exit = new JButton("Exit");
            exit.addActionListener(e -> System.exit(0));
            container.add(exit, gbc);
            gbc.weighty = 1;

        this.background.add(container);
        this.add(this.background);
        this.pack();
    }

    public void displayParameterChoicePage()
    {
        this.background.removeAll();
        
        ArrayList<Integer> toPass = new ArrayList<>();
        toPass.add(3); // par défaut, 3 joueurs.
        GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10,0,0,10);
            //gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weighty = 1;

 

        JLabel nbPlayer = new JLabel("Choisissez le nombre de joueurs supplémentaires :");
            nbPlayer.setFont(new Font(nbPlayer.getFont().getName(), Font.PLAIN, 50));
            nbPlayer.setForeground(Color.WHITE);
        
        JPanel container = new JPanel(new GridBagLayout());
            container.setOpaque(false);
            container.add(nbPlayer, gbc);
        JLabel choice = new JLabel("vous avez choisi: 3(par défaut) ");
            choice.setFont(new Font(choice.getFont().getName(), Font.PLAIN, 40));
            choice.setForeground(Color.WHITE);

        for(int i = 1; i <= 4; i++)
        {
            String s = "" + i;
            int tmp = i;
            JButton button = new JButton(s);
            button.addActionListener(e->{
                toPass.add(0, tmp);
                choice.setText("Vous avez choisi: " + tmp);
            });
            container.add(button, gbc);
        }
            
            container.add(choice, gbc); 
            container.add(Box.createVerticalStrut(50), gbc);
        JButton start = new JButton("Commenencer la partie!");
            start.addActionListener(e-> {
                try {
                    this.controller.update(toPass);
                    this.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            container.add(start, gbc);
            container.add(Box.createVerticalStrut(250), gbc);
        JButton exit = new JButton("Exit");
            exit.addActionListener(e -> System.exit(0));
            container.add(exit, gbc);

        this.background.add(container);
        this.background.revalidate();
        this.background.repaint();
        this.pack();

    }   


    public void changeBackgound(JComponent newBckgrnd)
    {
        this.background.removeAll();
        this.background.add(newBckgrnd);
        this.background.revalidate();
        this.background.repaint();
    }

    
    public static void main(String[] arg) {
        SuperApp sp = new SuperApp(new Controller());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}