package source;

import java.awt.image.BufferedImage;

public class Camera {
    private int x; 
    private int y;
    private int lenseSize;
    private BufferedImage img;
    
    public Camera(int a,int b,int lense){
        setX(a);
        setY(b);
        setLenseSize(lense);
        //img = new BufferedImage(0,0,)
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLenseSize() {
        return lenseSize;
    }

    public void setLenseSize(int lenseSize) {
        this.lenseSize = lenseSize;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
    
    
}
