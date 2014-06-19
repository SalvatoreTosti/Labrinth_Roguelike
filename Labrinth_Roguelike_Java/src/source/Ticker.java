package source;

import java.util.ArrayList;
import java.util.HashMap;

public class Ticker {
   
    private Director director;
    
    private long ticks;
    private HashMap<Long, ArrayList<Object>> schedule;
    
    public Ticker(long ticks){
        director = new Director();
        this.ticks = ticks;
        this.schedule = new HashMap<Long, ArrayList<Object>>();
    }
    
    public void nextTurn(){
        ticks += 1;
        if(schedule.containsKey(ticks)){
            for(int i=0;i<schedule.get(ticks).size();i++){
                Object obj = schedule.get(ticks).get(i);
                long waitTicks = director.doTurn(obj);  //All AI handling goes in director
                addToTickList(obj,waitTicks);}
        schedule.remove(ticks);} //clean up to keep map small
    }
    
    public void addToTickList(Object obj,long waitTicks){
        long newTick = waitTicks+ticks;
        if(schedule.containsKey(newTick)){schedule.get(newTick).add(obj);}
        else{
            ArrayList<Object> tmp = new ArrayList<Object>();
            tmp.add(obj);
            schedule.put(newTick,tmp);}
    }
    
    
    
    
    
    public Director getDirector() {return director;}
    public void setDirector(Director director) {this.director = director;}
    public long getTicks() {return ticks;}
    public void setTicks(long ticks) {this.ticks = ticks;}
    public HashMap<Long, ArrayList<Object>> getSchedule() {return schedule;}
    public void setSchedule(HashMap<Long, ArrayList<Object>> schedule) {this.schedule = schedule;}
    
    
}
