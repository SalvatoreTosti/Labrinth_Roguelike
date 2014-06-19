package source;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Menu {
    private Sprite backgroundSprite;
    private ArrayList<Sprite> spriteList;

    //maybe have array of sprites, with x/y coords
    //i.e perhaps there is an animated arrow pointing on the screen
    //and also an animated image of a shop keeper
    //these could both be sprites, which the menu knows where to render
    
    public Menu(){
        backgroundSprite = null;
        spriteList = new ArrayList<Sprite>();
    }

    public void updateSpriteFrames(){
        for(Sprite s: spriteList){s.updateFrames();}
        backgroundSprite.updateFrames();
    }
    
    
    public void InputHandler(KeyEvent e){}
    
    public Sprite getBackgroundSprite() {return backgroundSprite;}
    public void setBackgroundSprite(Sprite backgroundSprite) {this.backgroundSprite = backgroundSprite;}
    public ArrayList<Sprite> getSpriteList(){return spriteList;}
    
    
    
    
}
