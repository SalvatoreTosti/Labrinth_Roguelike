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

public class PlayerCreationBackend extends MenuBackend {
    private Player player;
    private ArrayList<Sprite> itemSprites;
    private ArrayList<Sprite> menuElements;
    //private Sprite background;
    private String backgroundPath = "Resources/monorail.jpg";
    
    public PlayerCreationBackend(Player p){
        super();
        player = p;
        itemSprites = new ArrayList<Sprite>();
        if(!itemSprites.isEmpty()){
        menuElements.addAll(itemSprites);}
        //prepareImages();
       
    }
    
    private void prepareImages(){
        ArrayList<String> elementPathList = new ArrayList<String>();
        elementPathList.add("Resources/SpinArrowClear.gif");
        loadMenuElements(elementPathList);
        //loadBackgroundSprite(backgroundPath);
    }
    
    private void loadMenuElements(ArrayList<String> elementPathList){
        for(String s: elementPathList){
            Sprite sprt = loadGIFImage(s);
          //sprt.setAnimationDelay(100);
            LocatedSprite lsprt = new LocatedSprite(sprt,150,232);
            menuElements.add(lsprt);}

    }
    
    /*private Sprite loadGIFImage(String location){
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
        return sprite;}*/
    
    
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
    
 }
