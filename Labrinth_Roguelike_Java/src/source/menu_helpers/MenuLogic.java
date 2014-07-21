package source.menu_helpers;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import source.Sprite;

/*!!!NOTE:
 * Methods named with 'load' open a file and then place it somewhere in the program.
 * They should all return void, because they are self contained.
 * Methods named with 'open' open a file and then return some type of data using that file.
 * They should return an object because they are essentially helpers.
 */

public abstract class MenuLogic {
    protected ArrayList<Sprite> menuElements;
    protected Sprite background;
    protected String backgroundPath = "Resources/BWGameGrid.png";
    protected boolean menuActive = true;
    
    private String alphabetPath = "Resources/RealNumerals.png";
    protected HashMap<String,Sprite> Alphabet;
    protected int alphabetSizeX=8,alphabetSizeY=8;
    
    public MenuLogic(){
        Alphabet = new HashMap<String,Sprite>();
        openAlphabet(alphabetPath);
        setMenuElements(new ArrayList<Sprite>());
        loadMenuElements();
        loadBackgroundSprite(getBackgroundPath());}
    
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
        background = sprt;}
    
    protected Sprite openGIFImage(String location){
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
    
    protected ArrayList<BufferedImage> ripSpriteSheet(String path, int tileSizeX, int tileSizeY){ 
        BufferedImage sheet = loadSpriteSheet(path);
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        BufferedImage tile = null;
        
        for(int j=0;j<sheet.getHeight();j+=tileSizeY){
            for(int i=0;i<sheet.getWidth();i+=tileSizeX){
                System.out.println(i+" "+j+" "+tileSizeX+" "+tileSizeY);
                tile = sheet.getSubimage(i,j,tileSizeX,tileSizeY);
                sprites.add(tile);
        }}

        return sprites;}
    
    protected BufferedImage loadSpriteSheet(String path){
        BufferedImage sheet = null;
        try {sheet = ImageIO.read(new File(path));}
        catch (IOException e) {System.out.println("Failed to load "+path);} 
        return sheet;}
    
    protected void openAlphabet(String alphabetPath){
        ArrayList<BufferedImage> alphaImages = ripSpriteSheet(alphabetPath,alphabetSizeX,alphabetSizeY);
        ArrayList<Sprite> alphaSprites = imagesToSprites(alphaImages);
        Alphabet.put("A",alphaSprites.get(0));
        Alphabet.put("B",alphaSprites.get(1));
        Alphabet.put("C",alphaSprites.get(2));
        Alphabet.put("D",alphaSprites.get(3));
        Alphabet.put("E",alphaSprites.get(4));
        Alphabet.put("F",alphaSprites.get(5));
        Alphabet.put("G",alphaSprites.get(6));
        Alphabet.put("H",alphaSprites.get(7));
        Alphabet.put("I",alphaSprites.get(8));
        Alphabet.put("J",alphaSprites.get(9));
        Alphabet.put("K",alphaSprites.get(10));
        Alphabet.put("L",alphaSprites.get(11));
        Alphabet.put("M",alphaSprites.get(12));
        Alphabet.put("N",alphaSprites.get(13));
        Alphabet.put("O",alphaSprites.get(14));
        Alphabet.put("P",alphaSprites.get(15));
        Alphabet.put("Q",alphaSprites.get(16));
        Alphabet.put("R",alphaSprites.get(17));
        Alphabet.put("S",alphaSprites.get(18));
        Alphabet.put("T",alphaSprites.get(19));
        Alphabet.put("U",alphaSprites.get(20));
        Alphabet.put("V",alphaSprites.get(21));
        Alphabet.put("W",alphaSprites.get(22));
        Alphabet.put("X",alphaSprites.get(23));
        Alphabet.put("Y",alphaSprites.get(24));
        Alphabet.put("Z",alphaSprites.get(25));
    }
    
    protected ArrayList<Sprite> imagesToSprites(ArrayList<BufferedImage> imgList){
        ArrayList<Sprite> sprtList = new ArrayList<Sprite>();
        int i=0;
        for(BufferedImage img : imgList){
            Sprite sprt = new Sprite(imgList.get(i),-1,null);
            sprtList.add(sprt);
            i++;}
        return sprtList;}
    
    public abstract void eventHandler(KeyEvent e);
    
    public ArrayList<Sprite> getMenuElements() {return menuElements;}
    public void setMenuElements(ArrayList<Sprite> menuElements) {this.menuElements = menuElements;}
    public Sprite getBackground() {return background;}
    public void setBackground(Sprite background) {this.background = background;}
    public void setBackgroundPath(String s){backgroundPath = s;}   
    public String getBackgroundPath(){return backgroundPath;}
    public boolean isMenuActive(){return menuActive;}
    public void setMenuActive(boolean b){menuActive=b;}  
}

