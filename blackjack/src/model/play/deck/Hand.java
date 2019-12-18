package model.play.deck;

import util.ListenableModel;
import util.ModelListener;

import java.util.*;

public class Hand<T extends Set> extends ArrayList<T> implements ListenableModel {

    public enum HandStatus{
        PLAYABLE,
        WAIT,
        SURREND
    }

    public HashMap<T, HandStatus> status;
    private List<ModelListener> list = new ArrayList<>();

    public Hand(T... objs){
        this.status = new HashMap<>();
        for (T obi: objs) {
            this.add(obi);
            this.setStatus(obi,HandStatus.PLAYABLE);
        }
    }

    public Hand(){
        this((T) new HashSet<>());
    }

    @Override
    public void addModelListener(ModelListener l) {
        this.list.add(l);
    }

    @Override
    public void removeModelListener(ModelListener l) {
        this.list.remove(l);
    }

    public void setStatus(T hand, HandStatus status){
        this.status.put(hand,status);
        this.update(hand);
    }

    public boolean addToHand(T hand, Object o){
        if (this.contains(hand)){
            hand.add(o);
            return true;
        }
        return false;
    }

    public boolean delInHand(T hand, Object o){
        if (this.contains(hand)){
            hand.remove(o);
            return true;
        }
        return false;
    }

    public void delHand(T hand){
        this.remove(hand);
    }

    public void addHand(T hand){
        addHand(hand,HandStatus.PLAYABLE);
    }
    public void addHand(T hand, HandStatus status){
        this.add(hand);
        this.setStatus(hand,status);
    }


    /**
     *
     * @param i index of the selected Hand
     * @return null if the HAND selected is not PLAYABLE
     * @throws IndexOutOfBoundsException
     */
    @Override
    public T get(int i){
        return super.get(i);
    }

    public HandStatus getStatus(T hand){

        assert (this.status.containsKey(hand)) : "This hand does not belong to YOU !";

        return (this.status.get(hand));
    }

    public boolean isValid(){
        for (T obj: this) {
            if(this.status.get(obj).equals(HandStatus.WAIT)) return false;
        }
        return true;
    }

    public void update(Object object){
        this.list.forEach(obj -> obj.somethingHasChanged(obj));
    }




}
