package source.menu_helpers;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import source.LocatedSprite;
import source.Sprite;
import source.Actors.Player;
import source.Items.Item;

public class InventoryBackend extends MenuBackend {
    private Player player;
    private ArrayList<Sprite> itemSprites;
    private ArrayList<Sprite> menuElements;
    
    public InventoryBackend(Player p){
        player = p;
        itemSprites = new ArrayList<Sprite>();
        menuElements = new ArrayList<Sprite>();
        loadPlayerInventorySprites();
        loadMenuElements();
        //loadMenuElements would be for loading generic elments of a menu.
        //i.e. pointers, dividers, other images.
        
    }
    
    
    private void loadPlayerInventorySprites(){
        if(player == null){
            System.out.println("player is null.");
        }
        if(player.getBackpack() == null){
            System.out.println("player bp is null.");
            ArrayList<Sprite> tmp = new ArrayList<Sprite>();
            Sprite sprt = loadGIFImage("Resources/SpinArrowClear.gif");
            sprt.setAnimationDelay(100);
            tmp.add(sprt);
            itemSprites = tmp;}
        else{
        for(Item i: player.getBackpack()){
           itemSprites.add(i.getItemSprite());}}
    }
    
    private void loadMenuElements(){
        Sprite sprt = loadGIFImage("Resources/SpinArrowClear.gif");
        //sprt.setAnimationDelay(100);
        LocatedSprite lsprt = new LocatedSprite(sprt,150,232);
        menuElements.add(lsprt);
    }
    
    public void InputHandler(KeyEvent e){
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
            for(Sprite s: menuElements){
                if(s instanceof LocatedSprite){
                    x = ((LocatedSprite) s).getLocationX();
                    x-=10;
                    ((LocatedSprite) s).setLocationX(x);}}
            break;
        case KeyEvent.VK_RIGHT:
            for(Sprite s: menuElements){
                if(s instanceof LocatedSprite){
                    x = ((LocatedSprite) s).getLocationX();
                    x+=10;
                    ((LocatedSprite) s).setLocationX(x);}}
            break;
            
        default:
            break;
            }
    }
    
    public Sprite getBackgroundSprite() {
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
    
    /*!!!NOTE:
     * This loadGIFImage is a temporary method, for testing.
     * The sprites loaded by this method should be stored loaded and stored elsewhere in
     * the program and then passed to the backend during its creation.
     * Or it could be passed through a method call? (idk, seems messy, reaching back up into
     * 'higher' logic in the program.
     */
    
    private Sprite loadGIFImage(String location){
        ArrayList<BufferedImage> spriteAnim = new ArrayList<BufferedImage>();
        ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
        try{
            ImageInputStream in = ImageIO.createImageInputStream(new File(location));
            reader.setInput(in);
            int noi = reader.getNumImages(true);
            for(int i=0;i<noi;i++){
                spriteAnim.add(reader.read(i));}
    
        } catch (IOException e){
            System.out.println("Failed to open splash image.");
            e.printStackTrace();}
        BufferedImage[] b = new BufferedImage[spriteAnim.size()];
        Sprite sprite = new Sprite(spriteAnim.get(0),100,spriteAnim.toArray(b));
        return sprite;}
    
    public ArrayList<Sprite> getItemSprites(){return itemSprites;}
    public ArrayList<Sprite> getMenuElements(){return menuElements;}
    
}
