package view;

import model.BlackJackProxy;
import model.play.*;
import util.ModelListener;

public interface Vue extends ModelListener{
    public void somethingHasChanged(Object source);
    public void setModel(BlackJackProxy model);
    //
}