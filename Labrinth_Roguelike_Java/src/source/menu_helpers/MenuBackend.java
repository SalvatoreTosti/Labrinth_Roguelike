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

public class MenuBackend {
    public ArrayList<Sprite> menuElements;
    public Sprite background;
    public String backgroundPath = "Resources/BWGameGrid.png";
    
    public MenuBackend(){
        menuElements = new ArrayList<Sprite>();
        loadMenuElements();
        loadBackgroundSprite(backgroundPath);
    }
    
    public Sprite getBackgroundSprite() {return background;}
    public void InputHandler(KeyEvent e) {return;}
    public ArrayList<Sprite> getMenuElements() {return menuElements;}
    
    public void loadMenuElements(){
        Sprite sprt = loadGIFImage("Resources/SpinArrowClear.gif");
        //sprt.setAnimationDelay(100);
        LocatedSprite lsprt = new LocatedSprite(sprt,150,232);
        menuElements.add(lsprt);
    }
    
    public Sprite loadGIFImage(String location){
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
    
    public Sprite loadBackgroundSprite(String s) {
        BufferedImage Background = null;
        try {
           Background = ImageIO.read(new File(s));
           System.out.println("opened it!");
        } catch (IOException e) {
           System.out.println("it didn't open.");}
        BufferedImage[] frames = new BufferedImage[1];
        frames[0] = Background;
        Sprite sprt = new Sprite(Background,-1,frames);
        return sprt;
    }
    
    
}
