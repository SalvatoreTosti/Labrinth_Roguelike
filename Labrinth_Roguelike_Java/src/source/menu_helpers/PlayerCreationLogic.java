package source.menu_helpers;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import source.LocatedSprite;
import source.Sprite;

public class PlayerCreationLogic extends MenuLogic {
    
    private String backgroundPath = "Resources/monorail.jpg";
    
    
    
    public PlayerCreationLogic() {
        super();
        
    }
    
    
    void loadMenuElements() {
        Sprite sprt = super.openGIFImage("Resources/SpinArrowClear.gif");
        LocatedSprite lsprt = new LocatedSprite(sprt,150,232);
        menuElements.add(lsprt);
        lsprt = new LocatedSprite(Alphabet.get("A"),100,232);
        menuElements.add(lsprt);
        lsprt = new LocatedSprite(Alphabet.get("B"),108,232);
        menuElements.add(lsprt);
        lsprt = new LocatedSprite(Alphabet.get("Z"),116,232);
        menuElements.add(lsprt);
    }
    
    public void eventHandler(KeyEvent e){
        int keycode = e.getKeyCode();
        System.out.println("menu logic got: "+keycode);
        if(keycode == KeyEvent.VK_M){
            setMenuActive(false);
        }
    }
    
    
    

}
