package util;

import java.util.ArrayList;

public abstract class AbstractListenableModel implements ListenableModel{

    private ArrayList<ModelListener> list;

    public AbstractListenableModel(){
        this.list = new ArrayList<>();
    }


    @Override
    public void addModelListener(ModelListener l){
        this.list.add(l);
    }

    @Override
    public void removeModelListener(ModelListener l){
        this.list.remove(l);
    }
}
