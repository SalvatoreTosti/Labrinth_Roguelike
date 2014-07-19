package source;

import source.Actors.NPC;
import source.Actors.Player;

public class Director {

    public long doTurn(Object obj){ 
        //returns number of turns the object must wait before it acts again
        if(obj instanceof Player){
        System.out.print("Player incorrectly passed to Director.");}
        
        if(obj instanceof NPC){}
        //System.out.println(((NPC) obj).getName()+" had a turn!");}
        return 10;
    }
}
