package world_helpers;
import java.util.ArrayList;

public class BooleanTileMap {
    private int sizex;
    private int sizey;
    ArrayList<ArrayList<Boolean>> map;
    
    public BooleanTileMap(int x, int y){
        map = new ArrayList<ArrayList<Boolean>>();
        setSizex(x);
        setSizey(y);
        for(int i=0;i<x;i++){
            map.add(new ArrayList<Boolean>());
            for(int j=0;j<y;j++){
                map.get(i).add(false);
        }}
    }
    
    public boolean getValue(int x,int y){
        return map.get(x).get(y);
    }
    public void setValue(int x, int y, boolean b){
        map.get(x).set(y, b);
    }

    public int getSizex() {
        return sizex;
    }

    public void setSizex(int sizex) {
        this.sizex = sizex;
    }

    public int getSizey() {
        return sizey;
    }

    public void setSizey(int sizey) {
        this.sizey = sizey;
    }
    
    public void invertMap(){
        for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                boolean b = map.get(i).get(j);
                map.get(i).set(j,!b);
            }
        }
    }
   
}
