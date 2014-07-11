package source.menu_helpers;

import java.awt.event.KeyEvent;

import source.LocatedSprite;
import source.Sprite;

public class PlayerCreationLogic extends MenuLogic {
    
    private String backgroundPath = "Resources/monorail.jpg";
    
    public PlayerCreationLogic() {
        super();
        //setBackgroundPath("Resources/monorail.jpg");
    }
    
    
    void loadMenuElements() {
        //Sprite sprt = loadGIFImage("Resources/SpinArrowClear.gif");
        Sprite sprt = super.loadGIFImage("Resources/SpinArrowClear.gif");
        //sprt.setAnimationDelay(100);
        LocatedSprite lsprt = new LocatedSprite(sprt,150,232);
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
