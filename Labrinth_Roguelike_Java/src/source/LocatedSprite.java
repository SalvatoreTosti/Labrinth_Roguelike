package source;

import java.awt.image.BufferedImage;

public class LocatedSprite extends Sprite {
    private int locationX;
    private int locationY;    
    
    public LocatedSprite(BufferedImage image, long delay, BufferedImage[] frames,int x, int y) {
        super(image, delay, frames);
        setLocationX(x);
        setLocationY(y);
    }
    
    public LocatedSprite(Sprite s, int x, int y){
        super(s.getSpriteImage(),s.getAnimationDelay(),s.getAnimationFrames());
        setLocationX(x);
        setLocationY(y);
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

}
