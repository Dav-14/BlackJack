package model.player;

public abstract class Human implements Player{

    private String name;

    public Human(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
