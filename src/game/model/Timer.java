package game.model;

public class Timer {
    private double max;
    private double time;

    public Timer(double max){
        this.max = max;
    }

    public void update(double time){
        this.time += time;
    }

    public boolean check(){
        if(time>max){
            time = 0;
            return true;
        }
        return false;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void setMax(double newMax){
        max = newMax;
    }


}
