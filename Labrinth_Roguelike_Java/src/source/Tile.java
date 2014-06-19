package source;

import java.awt.image.BufferedImage;

public class Tile {
    private Sprite sprite; //This would actually be part of a lower class i.e. terrain
                              //If all lower classes, i.e. items and props know what they look like
                              //it makes the Tile class more like a container
    
    public Tile(BufferedImage buf){
        sprite = new Sprite(buf,-1,null);}
    
    public void copyTile(Tile t){
        this.sprite = t.sprite;}
    
    public Sprite getSprite(){
        return sprite;
    }
    
    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }
}

