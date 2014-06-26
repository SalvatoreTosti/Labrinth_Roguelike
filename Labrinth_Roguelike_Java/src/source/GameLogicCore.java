package source;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import source.Actors.Actor;
import source.Actors.NPC;
import source.Actors.Player;
import source.Items.Item;
import source.menu_helpers.InventoryBackend;
import source.menu_helpers.MenuBackend;
import world_helpers.WorldBuilder;

public class GameLogicCore {
    String SPRITEPATH ="Resources/LabrinthTiles2.png";
    int TILESIZE = 16;
    BufferedImage SPRITESHEET;
    HashMap<String, Sprite> sprites = new HashMap<String,Sprite>();
    
    boolean openingMenu;
    boolean paused;
    Ticker ticker = new Ticker(0);
    boolean waitingForPlayerInput = false;
    boolean menuMode = false;
    Player PLAYER = new Player();
    World WORLD = new World(32,32);
    private int RENDER_LENSE_SIZE =5;
    Menu ACTIVEMENU;
    Menu SPLASHMENU;
    private int FRAMESIZEX;
    private int FRAMESIZEY;
    
    public GameLogicCore(int framesizex, int framesizey){
        FRAMESIZEX = framesizex;
        FRAMESIZEY = framesizey;
        ACTIVEMENU = null;
        openingMenu = true;
        paused = false;
        waitingForPlayerInput = true;
        SPLASHMENU = new Menu(FRAMESIZEX,FRAMESIZEY);
        loadSplashMenu();
        ACTIVEMENU = SPLASHMENU;
    }
    
    private void loadSplashMenu(){
        SPLASHMENU.setBackgroundSprite(loadGIFImage("Resources/LabrinthSplash.gif"));
        Sprite sprt = loadGIFImage("Resources/SpinArrowClear.gif");
        sprt.setAnimationDelay(100);
        LocatedSprite lsprt = new LocatedSprite(sprt,150,232);
        SPLASHMENU.getSpriteList().add(lsprt);
        sprt.setAnimationDelay(500);
        lsprt = new LocatedSprite(sprt,150,200);
        SPLASHMENU.getSpriteList().add(lsprt);
        sprt.setAnimationDelay(1000);
        lsprt = new LocatedSprite(sprt,150,216);
        SPLASHMENU.getSpriteList().add(lsprt);}
    
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
        return sprite;
        
    }
    
    private void startGame(){
        prepareLogicAssets();
        loadSpriteSheet();
        spriteRip(TILESIZE,TILESIZE);
        loadRandomWorld();
        openingMenu = false;
        ACTIVEMENU = new Menu(FRAMESIZEX,FRAMESIZEX);
        ACTIVEMENU.loadAlternateBackgroundSprite();
        paused = false;
        waitingForPlayerInput = true;  
    }
    
    private void prepareLogicAssets(){
        setupPlayer();
        ticker.addToTickList(PLAYER, 1);
        ArrayList<Actor> npcs = new ArrayList<Actor>();
        NPC n = new NPC();
        n.setName("Monster1");
        npcs.add(n);
        for(Actor a: npcs){
            ticker.addToTickList(a,1);
 System.out.println("Added "+a.getName()+" to the list!");}
        //setRENDER_LENSESIZE(5);
    }
    
    private void setupPlayer(){
        PLAYER.setBackpack(new ArrayList<Item>()); 
    }
    
    
    public void HandleNextTick(){
        if(isPlayerTurnNext()){
            waitingForPlayerInput = true;
            return;}
        ticker.nextTurn();}
   
    public void ProcessPlayerInput(KeyEvent e){
        if(waitingForPlayerInput){
            HandlePlayerInput(e);}
        return;}
        
    private void HandlePlayerInput(KeyEvent e){
        if(openingMenu){openingMenuInputHandler(e);}
        else if(paused){pausedMenuInputHandler(e);}
        else{waitingForPlayerInput = HandlePlayerNextTick(e);}
    }
    
    private void openingMenuInputHandler(KeyEvent e){
        ACTIVEMENU.InputHandler(e);
        int keycode = e.getKeyCode();
        switch(keycode){
        case KeyEvent.VK_ENTER:
            startGame();
            break;
        case KeyEvent.VK_UP:
            SPRITEPATH = "Resources/LabrinthTiles2.png";
            break;
        case KeyEvent.VK_DOWN:
            SPRITEPATH ="Resources/LabrinthTiles2Invert.png";
            break;
        case KeyEvent.VK_LEFT:
            break;
        case KeyEvent.VK_RIGHT:
            break;
        default:
            break;
            }
    }
    
    private void pausedMenuInputHandler(KeyEvent e){}
    
    private boolean HandlePlayerNextTick(KeyEvent e){
        boolean turnContinues = false;
        int waitTicks = 0;
        int keycode = e.getKeyCode();
 
        if(menuMode){
            ACTIVEMENU.InputHandler(e);
            if(keycode == KeyEvent.VK_M){
                System.out.println("Menu M!");
                menuMode = false;
                turnContinues = true;}
            else{turnContinues = true;}
            return turnContinues;
        }
            /*System.out.println("Entering Menu Mode switch!");
            switch (keycode){
            case KeyEvent.VK_LEFT:
            System.out.println("Menu Left!");
                turnContinues = true;
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Menu Right!");
                turnContinues = true;
                break; 
            case  KeyEvent.VK_UP:
                System.out.println("Menu Up!");
                turnContinues = true;
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Menu Down!");
                turnContinues = true;
                break;
            case KeyEvent.VK_M:
            System.out.println("Menu M!");
                menuMode = false;
                break;
            default: 
                turnContinues = true;
                }
            return turnContinues;}*/
        
        
        if(keycode == KeyEvent.VK_LEFT){
            int newX = PLAYER.getX()-1;
            turnContinues = AttemptMovePlayer(newX,PLAYER.getY());
            if(!turnContinues){waitTicks = 10;}
        }
        else if(keycode == KeyEvent.VK_RIGHT){
            int newX = PLAYER.getX()+1;
            turnContinues = AttemptMovePlayer(newX,PLAYER.getY());
            if(!turnContinues){waitTicks = 10;}
        }
        else if(keycode == KeyEvent.VK_UP){
            int newY = PLAYER.getY()-1;
            turnContinues = AttemptMovePlayer(PLAYER.getX(),newY);
            if(!turnContinues){waitTicks = 10;}
        }
        else if(keycode == KeyEvent.VK_DOWN){
            int newY = PLAYER.getY()+1;
            turnContinues = AttemptMovePlayer(PLAYER.getX(),newY);
            if(!turnContinues){waitTicks = 10;}
        }
        else if(keycode == KeyEvent.VK_Q){ //get view window smaller
            //System.out.println("Lense Size: "+RENDER_LENSE_SIZE);
            RENDER_LENSE_SIZE-=2;
            if(RENDER_LENSE_SIZE<3){RENDER_LENSE_SIZE = 3;}
            else if(RENDER_LENSE_SIZE>11){RENDER_LENSE_SIZE = 11;}
            else{}
            turnContinues = true;
        }
        else if(keycode == KeyEvent.VK_E){ //get view window bigger
            //System.out.println("Lense Size: "+RENDER_LENSE_SIZE);
            RENDER_LENSE_SIZE+=2;
            if(RENDER_LENSE_SIZE<3){RENDER_LENSE_SIZE = 3;}
            else if(RENDER_LENSE_SIZE>11){RENDER_LENSE_SIZE = 11;}
            else{}
            turnContinues = true;
        }
        else if(keycode == KeyEvent.VK_M){
            menuMode = true;
            Menu m = new Menu(FRAMESIZEX,FRAMESIZEY,new InventoryBackend(PLAYER));
            //m.setMenuBackend(new InventoryBackend(PLAYER));
            setActiveMenu(m);
            turnContinues = true;
        }
        
        
        else{System.out.println("Invalid key pressed, did nothing.");
            turnContinues = true;}
        
        if(!turnContinues){
            System.out.println("Current tick is: "+ticker.getTicks()); 
            ticker.getSchedule().get(ticker.getTicks()+1).remove(PLAYER);
            ticker.addToTickList(PLAYER,waitTicks);
            System.out.println("Player turn is over!");
        }
        //System.out.println("Player x is: "+PLAYER.getX());
        //System.out.println("Player y is: "+PLAYER.getY());
        return turnContinues;}
        
    private void setActiveMenu(Menu m){
        ACTIVEMENU = m;
    }
       
    
    private boolean isPlayerTurnNext(){
        long nextTick = ticker.getTicks()+1;
        if(ticker.getSchedule().get(nextTick) == null){return false;}
        if(ticker.getSchedule().get(nextTick).contains(PLAYER)){return true;}
        return false;}
    
    private boolean isPlayerTurnCurrent(){
        long currentTick = ticker.getTicks();
        if(ticker.getSchedule().containsKey(currentTick)){return true;}
        return false;}

    private boolean AttemptMovePlayer(int x, int y){
        if(x>WORLD.getSizeX()){return true;}
        if(x<0){return true;}
        if(y>WORLD.getSizeY()){return true;}
        if(y<0){return true;}
        PLAYER.setX(x);
        PLAYER.setY(y);
        return false;}

    
    /*Rendering and Graphics methods
     *The following methods are all dedicated to rendering functions, sprite ripping and labeling.
     *!!! NOTE: At a later point, I think I should write a graphics helper to encapsulate these functions.
    */
    public int getRENDER_LENSESIZE() {return RENDER_LENSE_SIZE;}
    public void setRENDER_LENSESIZE(int rENDER_LENSESIZE) {RENDER_LENSE_SIZE = rENDER_LENSESIZE;}
    
    private void loadSpriteSheet(){
        SPRITESHEET = null;
        try {
           SPRITESHEET = ImageIO.read(new File(SPRITEPATH));
           System.out.println("opened it!");
        } catch (IOException e) {
           System.out.println("it didn't open.");} 
    }
    
    private void spriteRip(int tileSizex, int tileSizey){
        /*Rips sprites from left to right starting in the top right hand corner*/
        ArrayList<BufferedImage> spriteList = new ArrayList<BufferedImage>();
        BufferedImage currentTile = null;
        for(int i=0;i<SPRITESHEET.getWidth();i+=tileSizex){
            for(int j=0;j<SPRITESHEET.getHeight();j+=tileSizey){
                currentTile = SPRITESHEET.getSubimage(j,i,tileSizex,tileSizey);
                spriteList.add(currentTile);
            }}

        Sprite Test = new Sprite(spriteList.get(0),-1,null);
        sprites.put("Test", Test);
        BufferedImage[] b = new BufferedImage[3];
        b[0]=spriteList.get(1);//standing
        b[1]=spriteList.get(2);//step right
        b[2]=spriteList.get(3);//step left
        Sprite Player = new Sprite(spriteList.get(1),1000,b);
        sprites.put("Player", Player);
        
        Sprite Wall_UL = new Sprite(spriteList.get(4),-1,null);
        sprites.put("Wall_UL", Wall_UL);
        Sprite Wall_UM = new Sprite(spriteList.get(5),-1,null);
        sprites.put("Wall_UM", Wall_UM);
        Sprite Wall_UR = new Sprite(spriteList.get(6),-1,null);
        sprites.put("Wall_UR", Wall_UR);
        Sprite Wall_Door2 = new Sprite(spriteList.get(7),-1,null);
        sprites.put("Wall_Door2", Wall_Door2);
        Sprite Wall_ML = new Sprite(spriteList.get(8),-1,null);
        sprites.put("Wall_ML", Wall_ML);
        Sprite Wall_Door = new Sprite(spriteList.get(9),-1,null);
        sprites.put("Wall_Door", Wall_Door);
        Sprite Wall_MR = new Sprite(spriteList.get(10),-1,null);
        sprites.put("Wall_RM", Wall_MR);
        Sprite Wall_Door3 = new Sprite(spriteList.get(3),-1,null);
        sprites.put("Wall_Door3 ", Wall_Door3);
        Sprite Wall_LL = new Sprite(spriteList.get(12),-1,null);
        sprites.put("Wall_LL", Wall_LL);
        Sprite Wall_LM = new Sprite(spriteList.get(13),-1,null);
        sprites.put("Wall_LM", Wall_LM);
        Sprite Wall_LR = new Sprite(spriteList.get(14),-1,null);
        sprites.put("Wall_LR", Wall_LR);
        Sprite Blank = new Sprite(spriteList.get(15),-1,null);
        sprites.put("Blank", Blank);
        //!!! NOTE ON NAMES OF SPRITES
        //The naming goes as follows:
        //First letter is Upper, Lower, or Middle
        //The second letter is Left, Right or Middle
        //EX.   LM = Lower Middle
        //      UR = Upper Right
    }
    
    private void loadRandomWorld(){
        WorldBuilder builder = new WorldBuilder(WORLD);
        builder.buildRandomLabrinth(sprites);
           
        
    /*int sizeX = WORLD.getSizeX();
    int sizeY = WORLD.getSizeY();
    for(int i=0;i<sizeX;i++){
        for(int j=0;j<sizeY;j++){
            Sprite tmpSprt = null;
            int pick = (int)(Math.random()*4);
            if(pick == 0){tmpSprt = sprites.get("Blank");}
            else if(pick == 1){tmpSprt = sprites.get("Wall_UL");}
            else if(pick == 2){tmpSprt = sprites.get("Wall_UR");}
            else if(pick == 3){tmpSprt = sprites.get("Player");}
            WORLD.tileMap.get(i).get(j).setSprite(tmpSprt);
        }}*/
    }
    
    
    
    
}

