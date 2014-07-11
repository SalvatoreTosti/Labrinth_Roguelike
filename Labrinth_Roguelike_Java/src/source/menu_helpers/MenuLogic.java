package source.menu_helpers;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import source.Sprite;

public abstract class MenuLogic {
    protected ArrayList<Sprite> menuElements;
    protected Sprite background;
    protected String backgroundPath = "Resources/BWGameGrid.png";
    protected boolean menuActive = true;
    
    public MenuLogic(){
        setMenuElements(new ArrayList<Sprite>());
        loadMenuElements();
        loadBackgroundSprite(getBackgroundPath());
    }
    
    abstract void loadMenuElements();
    
    protected void loadBackgroundSprite(String s) {
        BufferedImage Background = null;
        try {
           Background = ImageIO.read(new File(s));
           System.out.println("opened it!");
        } catch (IOException e) {
           System.out.println("it didn't open.");}
        BufferedImage[] frames = new BufferedImage[1];
        frames[0] = Background;
        Sprite sprt = new Sprite(Background,-1,frames);
        background = sprt;
        //return sprt;
    }
    
    protected Sprite loadGIFImage(String location){
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
    
    public abstract void eventHandler(KeyEvent e);
    
    public ArrayList<Sprite> getMenuElements() {
        return menuElements;
    }
    public void setMenuElements(ArrayList<Sprite> menuElements) {
        this.menuElements = menuElements;
    }
    public Sprite getBackground() {
        return background;
    }
    public void setBackground(Sprite background) {
        this.background = background;
    }
    
    public void setBackgroundPath(String s){
        backgroundPath = s;
    }
    
    public String getBackgroundPath(){
        return backgroundPath;
    }
    
    public boolean isMenuActive(){
        return menuActive;
    }
    
    public void setMenuActive(boolean b){
        menuActive=b;
    }

    
}

