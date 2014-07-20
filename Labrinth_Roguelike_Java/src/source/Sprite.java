package source;

import java.awt.image.BufferedImage;

public class Sprite {
    private BufferedImage spriteImage;
    private long animationDelay;
    private long lastUpdate;
    private int currentFrame;
    private BufferedImage[] animationFrames;
    
    public Sprite(
        BufferedImage image,
        long delay,
        BufferedImage[] frames){
        spriteImage = image;
        animationDelay = delay;  
        lastUpdate = -1; //dummy value, indicating never updated before
        currentFrame = 0;   // !!!HACK not sure if constructor will need to be able to set this
        animationFrames = frames;}
    /*!!! NOTE on animationDelay variable
     * I'm implementing it so that this variable, if set to -1 is actually a flag
     * which indicates that the sprite is static and will not need animation updates.
     * */
    
    public void updateFrames(){
        if(animationDelay == -1){return;} //if static sprite, ignore this call
        long nanoDelay = animationDelay*1000000; //millis to nanos
        long now = System.nanoTime();
        if(now >= lastUpdate+nanoDelay){
            getNextFrame();
            lastUpdate = System.nanoTime();}
    }
    
    private void getNextFrame(){
        if(currentFrame+1 == animationFrames.length){
            currentFrame = 0;
            spriteImage = animationFrames[0];}
        else{
            currentFrame++;
            spriteImage = animationFrames[currentFrame];}
    }
    
    public BufferedImage getSpriteImage() {return spriteImage;}
    public void setSpriteImage(BufferedImage sprite) {this.spriteImage = sprite;}
    public long getAnimationDelay() {return animationDelay;}
    public void setAnimationDelay(long animationDelay) {this.animationDelay = animationDelay;}
    public long getLastUpdate() {return lastUpdate;}
    public void setLastUpdate(long lastUpdate) {this.lastUpdate = lastUpdate;}
    public int getCurrentFrame() {return currentFrame;}
    public void setCurrentFrame(int currentFrame) {this.currentFrame = currentFrame;}
    public BufferedImage[] getAnimationFrames() {return animationFrames;}
    public void setAnimationFrames(BufferedImage[] animationFrames) {this.animationFrames = animationFrames;}
    
}
