package source;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import source.menu_helpers.MenuBackend;
import source.menu_helpers.MenuLogic;

public class Menu {
    private Sprite backgroundSprite;
    private ArrayList<Sprite> spriteList;
    private int FRAMESIZEX;
    private int FRAMESIZEY;
    //!!!NOTE:
    //maybe have array of sprites, with x/y coords
    //i.e perhaps there is an animated arrow pointing on the screen
    //and also an animated image of a shop keeper
    //these could both be sprites, which the menu knows where to render
    /*06/19/14
     * The feature discussed above is implemented as the 'LocatedSprite' class
     */
    
    private MenuBackend menuBackend;
    private MenuLogic logic;
    
    public Menu(int x, int y, MenuLogic log){
        logic = log;
        backgroundSprite = logic.getBackground();
        spriteList = logic.getMenuElements();
        FRAMESIZEX = x;
        FRAMESIZEY = y;
    }
    
    
    
    public Menu(int x, int y,MenuBackend backend){
        backgroundSprite = backend.getBackgroundSprite();
        spriteList = new ArrayList<Sprite>();
        FRAMESIZEX = x;
        FRAMESIZEY = y;
        menuBackend = backend;
        spriteList = backend.getMenuElements();
        //spriteList = backend.getItemSprites();
        //spriteList = new ArrayList<Sprite>();
        
       if(backend.getMenuElements() == null){
           System.out.println("null!");
       }
        if(!backend.getMenuElements().isEmpty()){
            spriteList.addAll(backend.getMenuElements());}
    }
    
   
    
    public Menu(int x, int y){
        backgroundSprite = loadDefaultBackgroundSprite();
        spriteList = new ArrayList<Sprite>();
        FRAMESIZEX = x;
        FRAMESIZEY = y;
        menuBackend = new MenuBackend();
    }


    public void updateSpriteFrames(){
        for(Sprite s: spriteList){s.updateFrames();}
        backgroundSprite.updateFrames();
    }
    
    
    public void InputHandler(KeyEvent e){
        if(logic == null){nullBackendKeyEventHandler(e);}
        else{
            int keycode = e.getKeyCode();
            logic.eventHandler(e);
        }
        
        
        /*if(menuBackend == null){nullBackendKeyEventHandler(e);}
        else{
            int keycode = e.getKeyCode();
            menuBackend.InputHandler(e);
            
        }*/
        
        
    }
    
    /*
    public void InputHandler(KeyEvent e){
        if(menuBackend == null){
            nullBackendKeyEventHandler(e);
            return;}
        int keycode = e.getKeyCode();
        int x;
        int y;
        switch(keycode){
        case KeyEvent.VK_ENTER:
            break;
        case KeyEvent.VK_UP:
            break;
        case KeyEvent.VK_DOWN:
            break;
        case KeyEvent.VK_LEFT:
            for(Sprite s: spriteList){
                if(s instanceof LocatedSprite){
                    x = ((LocatedSprite) s).getLocationX();
                    x-=10;
                    ((LocatedSprite) s).setLocationX(x);}}
            break;
        case KeyEvent.VK_RIGHT:
            for(Sprite s: spriteList){
                if(s instanceof LocatedSprite){
                    x = ((LocatedSprite) s).getLocationX();
                    x+=10;
                    ((LocatedSprite) s).setLocationX(x);}}
            break;
            
        default:
            break;
            }
    }*/
    
    private void nullBackendKeyEventHandler(KeyEvent e){
        int keycode = e.getKeyCode();
        switch(keycode){
        case KeyEvent.VK_ENTER:
            break;
        case KeyEvent.VK_UP:
            break;
        case KeyEvent.VK_DOWN:
            break;
        case KeyEvent.VK_LEFT:
            break;
        case KeyEvent.VK_RIGHT:
            break;
            
        default:
            break;
            }
    }
    
    public BufferedImage drawMenu(){
        int frmx = (int) (FRAMESIZEX);
        int frmy = (int) (FRAMESIZEY);
        BufferedImage img = new BufferedImage(frmx,frmy,BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        
        g.drawImage(getBackgroundSprite().getSpriteImage(),0,0,null);
        for(Sprite s: spriteList){
            if(s instanceof LocatedSprite){
                int x = ((LocatedSprite) s).getLocationX();
                int y = ((LocatedSprite) s).getLocationY();
                g.drawImage(s.getSpriteImage(),x,y,null);
                }
            else{g.drawImage(s.getSpriteImage(),0,0,null);}
        }
        return img;
    }
    
    private Sprite loadDefaultBackgroundSprite(){
        BufferedImage Background = null;
        try {
           Background = ImageIO.read(new File("Resources/monorail.jpg"));
           System.out.println("opened it!");
        } catch (IOException e) {
           System.out.println("it didn't open.");}
        BufferedImage[] frames = new BufferedImage[1];
        frames[0] = Background;
        Sprite sprt = new Sprite(Background,-1,frames);
        return sprt;
    }
    
    //Test method, used to set a different background for the 'default' active menu.
    public void loadAlternateBackgroundSprite(){
        BufferedImage Background = null;
        try {
           Background = ImageIO.read(new File("Resources/BWGameGrid.png"));
           System.out.println("opened it!");
        } catch (IOException e) {
           System.out.println("it didn't open.");}
        BufferedImage[] frames = new BufferedImage[1];
        frames[0] = Background;
        Sprite sprt = new Sprite(Background,-1,frames);
        setBackgroundSprite(sprt);
    }
    
    public Sprite getBackgroundSprite() {return backgroundSprite;}
    public void setBackgroundSprite(Sprite backgroundSprite) {this.backgroundSprite = backgroundSprite;}
    public ArrayList<Sprite> getSpriteList(){return spriteList;}
    //public MenuBackend getMenuBackend() {return menuBackend;}
    //public void setMenuBackend(MenuBackend menuBackend) {this.menuBackend = menuBackend;}
    public MenuLogic getLogic(){return logic;}
    
}
