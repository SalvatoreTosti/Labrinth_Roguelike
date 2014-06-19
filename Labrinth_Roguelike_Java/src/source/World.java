package source;

import java.util.ArrayList;

public class World {
    private ArrayList<ArrayList<Tile>> tileMap = new ArrayList<ArrayList<Tile>>();
    private int SIZEX;
    private int SIZEY;
    public World(int sizex, int sizey){
        SIZEX = sizex;
        SIZEY = sizey;
        for(int i=0;i<SIZEX;i++){
            getTileMap().add(new ArrayList<Tile>());
            for(int j=0;j<SIZEY;j++){
               getTileMap().get(i).add(new Tile(null));
            }}
    }
    
    public void setTile(int x, int y, Tile tile){
        getTileMap().get(x).get(y).copyTile(tile);
    }
    
    
    public ArrayList<ArrayList<Tile>> getTileMap() {
        return tileMap;
    }

    public void setTileMap(ArrayList<ArrayList<Tile>> tileMap) {
        this.tileMap = tileMap;
    }

    public int getSizeX(){
        return SIZEX;
    }
    public int getSizeY(){
        return SIZEY;
    }
}
