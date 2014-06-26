package source.Items;

import java.awt.image.BufferedImage;

import source.Sprite;

public class Item {
    private Sprite itemSprite;
    
    public Item(){}

    public Sprite getItemSprite() {
        return itemSprite;
    }

    public void setItemSprite(Sprite itemSprite) {
        this.itemSprite = itemSprite;
    }
}
