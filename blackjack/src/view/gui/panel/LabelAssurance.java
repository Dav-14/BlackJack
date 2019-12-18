package view.gui.panel;

import javax.swing.JLabel;

import model.BlackJackProxy;
import view.Vue;

public class LabelAssurance extends JLabel implements Vue {

    private BlackJackProxy model;

    public LabelAssurance(BlackJackProxy model, int assurance) {
        super("Assurance: " +assurance);
        this.model = model;
    } 

    public void autoListen()
    {
        this.model.addInsuranceListener(this);
    }

    @Override
    public void somethingHasChanged(Object source) {
        
        if(source instanceof String)
        {
            String s = (String) source; 
            setText("Assurance: " + s);
        }
    }

    @Override
    public void setModel(BlackJackProxy model) {
        this.model = model;

    }

    
}