package source;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
    private int FRAMESIZEX = 300;
    private int FRAMESIZEY = 300;
    long lastMenuUpdate = 0;
    //private static long DELAY = 1000; //time to ensure steps are even
    //private Thread animator;
    
    int x = 0;
    int y = 0;
    GameLogicCore CORE = new GameLogicCore(FRAMESIZEX,FRAMESIZEY);
    BufferedImage RENDER_CANVAS;
    BufferedImage STABLE_CANVAS;
   
    //Menu SPLASHMENU = new Menu();
    //Sprite SPLASHSPRITE; //!!! HACK: this variable is a hack to display a splash image
                         //This should probably have a 'menu' object encasing it.
    
    public Game(){
        addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e){}
            public void keyReleased(KeyEvent e){}
            public void keyPressed(KeyEvent e){
                //System.out.println("Typed "+e.getKeyChar());
                //CORE.HandlePlayerNextTick();
                CORE.ProcessPlayerInput(e);}
        });
        setFocusable(true);
        gameSetup();
        
    }
    
    private void gameSetup(){
        RENDER_CANVAS = new BufferedImage(FRAMESIZEX,FRAMESIZEY,BufferedImage.TYPE_INT_RGB);
        STABLE_CANVAS = new BufferedImage(FRAMESIZEX,FRAMESIZEY,BufferedImage.TYPE_INT_RGB);
        //!!!HACK
        //These methods should be called within the game as part of setup
        //CORE.loadSpriteSheet();   
       // CORE.spriteRip(TILESIZE,TILESIZE);
       //loadSplashMenu();
       //loadSplashImage();
       //CORE.loadRandomWorld();
    }
    
    private void updateGameState(){
        if(CORE.openingMenu){}
        else if(CORE.menuMode){}
        else if(CORE.paused){}
        else{CORE.HandleNextTick();}
    }
    
    private void updateImg(){
        if(CORE.openingMenu){updateOpeningMenu();}
        else if(CORE.menuMode){updateActiveMenu();}    
        //else if(paused){}
        else{renderActiveGame();}
        
        //Copies the latest update from RENDER_CANVAS on to the STABLE_CANVAS
        //for rendering in 'paint()'.
        //This seems to side step the frame drop issue from before.
        Graphics g = STABLE_CANVAS.getGraphics();
        g.drawImage(RENDER_CANVAS,0,0,null);
        g.dispose();
    }    
  
    private void updateOpeningMenu(){
        CORE.ACTIVEMENU.updateSpriteFrames();
        BufferedImage menu = CORE.ACTIVEMENU.drawMenu();
        Graphics g = RENDER_CANVAS.getGraphics();
        g.drawImage(menu,0,0,null);
        g.dispose();
    }
    
    private void updateActiveMenu(){
        if(CORE.ACTIVEMENU==null){System.out.println("Menu is null.");}
        CORE.ACTIVEMENU.updateSpriteFrames();
        BufferedImage menu = CORE.ACTIVEMENU.drawMenu();
        
        Graphics g = RENDER_CANVAS.getGraphics();
        g.drawImage(menu,0,0,null);
        g.dispose();
    }
    
    private void renderActiveGame(){
        Camera Cam = new Camera(CORE.PLAYER.getX(),CORE.PLAYER.getY(),CORE.TILESIZE*CORE.getRENDER_LENSESIZE());
        BufferedImage img = new BufferedImage(Cam.getLenseSize(),Cam.getLenseSize(),BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        int tilesInLense = (int)(Cam.getLenseSize()/CORE.TILESIZE);
        boolean odd = (tilesInLense%2 != 0);
        int tilesInHalfLense = (int)(tilesInLense/2);
  
        int LoX = Cam.getX()-tilesInHalfLense;
        int HiX  = Cam.getX()+tilesInHalfLense;
        int LoY = Cam.getY()-tilesInHalfLense;
        int HiY  = Cam.getY()+tilesInHalfLense;
        int diff;
        if(LoX<0){
            diff = -LoX;
            HiX = HiX + diff;
            if(odd){HiX++;}
            LoX = 0;}
        if(HiX>=CORE.WORLD.getSizeX()){
            diff = HiX -CORE.WORLD.getSizeX();
            LoX = LoX - diff;
            if(odd){LoX--;}
            HiX = CORE.WORLD.getSizeX()-1;}
        if(LoY<0){
            diff = -LoY;
            HiY = HiY + diff;
            if(odd){HiY++;}
            LoY = 0;}
        if(HiY>=CORE.WORLD.getSizeY()){
            diff = HiY -CORE.WORLD.getSizeY();
            LoY = LoY-diff;
            if(odd){LoY--;}
            HiY = CORE.WORLD.getSizeY()-1;}

        int tmpx = 0;
        int tmpy = 0;
        for(int i=LoX;i<=HiX;i++){
            tmpy = 0;
             for(int j=LoY;j<=HiY;j++){
                Tile tile = CORE.WORLD.getTileMap().get(i).get(j);
                //!!! NOTE: ANIMATION UPDATES GO HERE
                tile.getSprite().updateFrames();
                g.drawImage(tile.getSprite().getSpriteImage(),tmpx*CORE.TILESIZE,tmpy*CORE.TILESIZE, null);
                tmpy++;}
            tmpx++; }
       
        //!!!NOTE: For some reason this seems to fix the brief frame drops.
        //Idk, maybe it had to do with reallocating this on the fly?
        //!!!NOTE: I fixed this issue by adding STABLE_CANVAS, however, I am still removing this
        //for efficiency.
        //RENDER_CANVAS = new BufferedImage(FRAMESIZEX,FRAMESIZEY,BufferedImage.TYPE_INT_RGB);
        int diffHoriz = FRAMESIZEX - img.getWidth();
        int diffVert = FRAMESIZEY - img.getHeight();
        
        boolean oddHorizSize = FRAMESIZEX%2 != 0;
        int totalHorizSize = FRAMESIZEX-img.getWidth();
        if(oddHorizSize){totalHorizSize--;}
        int sidePanelSize = totalHorizSize/2;
        int panelSizeL = sidePanelSize;
        if(oddHorizSize){panelSizeL++;} //This is so the extra 'odd' pixel is caught and placed in the left side
        
        BufferedImage sideBarL = new BufferedImage(panelSizeL,img.getHeight(),BufferedImage.TYPE_INT_RGB);
        g = sideBarL.getGraphics();
        g.setColor(Color.blue);
        g.drawRect(0,0,sideBarL.getWidth(),sideBarL.getHeight());
        
        BufferedImage sideBarR = new BufferedImage(sidePanelSize,img.getHeight(),BufferedImage.TYPE_INT_RGB);
        g = sideBarR.getGraphics();
        g.setColor(Color.blue);
        g.drawRect(0,0,sideBarR.getWidth(),sideBarR.getHeight());
        
        BufferedImage loBar = new BufferedImage(FRAMESIZEX,diffVert,BufferedImage.TYPE_INT_RGB);
        g = loBar.getGraphics();
        g.setColor(Color.red);
        g.drawRect(0,0,loBar.getWidth(),loBar.getHeight());
        g = RENDER_CANVAS.getGraphics();
        g.drawImage(sideBarL,0,0,null);
        g.drawImage(img,sideBarL.getWidth(),0,null);
        g.drawImage(sideBarR,sideBarL.getWidth()+img.getWidth(),0,null);
        g.drawImage(loBar,0,img.getHeight(),null);
        g.dispose();
        }
    
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(STABLE_CANVAS,0, 0, null);
        g2d.dispose();}
    
    public int getFRAMESIZEX() {return FRAMESIZEX;}
    public void setFRAMESIZEX(int fRAMESIZEX) {FRAMESIZEX = fRAMESIZEX;}
    public int getFRAMESIZEY() {return FRAMESIZEY;}
    public void setFRAMESIZEY(int fRAMESIZEY) {FRAMESIZEY = fRAMESIZEY;}
    
    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("Labrinth!");
        Game game = new Game();
        frame.add(game);
        frame.setSize(game.getFRAMESIZEX(),game.getFRAMESIZEY());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        while(true){ 
            game.updateGameState();
            game.updateImg();
            game.repaint();
            Thread.sleep(10);}
    }  
}
