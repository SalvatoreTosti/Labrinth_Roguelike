package source.Actors;

import java.util.ArrayList;

import source.Items.Armor;
import source.Items.Item;
import source.Items.Weapon;

public class Actor {
    private int x;
    private int y;

    private String Name;
    //private Race Race;
    //private Role Role;
    private long TotalXP;
    
    //Initiative
    private int init;
    
    //Defenses
    private int AC;
    private int Fort;
    private int Ref;
    private int Will;
    
    //Movement
    private int Speed;
    
    //Senses
    private int PassiveInsight;
    private int PassivePerception;
    
    //Hit Points
    private int MaxHP;
    private int CurrentHP;
  
    
    //Ability Scores
    private int Str;
    private int Con;
    private int Dex;
    private int Intel;
    private int Wis;
    private int Cha;
   
    //Skills
    
    
    private ArrayList<Item> backpack;
    private Armor Head;
    private Armor Chest;
    private Armor Legs;
    //private Trinket Neck;
    private Weapon Left;
    private Weapon Right;
    
    //private ArrayList<Ability> abilities;
    
    public Actor(){}
    
    //Calculation Area
    

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public long getTotalXP() {
        return TotalXP;
    }


    public void setTotalXP(long totalXP) {
        TotalXP = totalXP;
    }


    public int getInit() {
        return init;
    }


    public void setInit(int init) {
        this.init = init;
    }


    public int getAC() {
        return AC;
    }


    public void setAC(int aC) {
        AC = aC;
    }


    public int getFort() {
        return Fort;
    }


    public void setFort(int fort) {
        Fort = fort;
    }


    public int getRef() {
        return Ref;
    }


    public void setRef(int ref) {
        Ref = ref;
    }


    public int getWill() {
        return Will;
    }


    public void setWill(int will) {
        Will = will;
    }


    public int getSpeed() {
        return Speed;
    }


    public void setSpeed(int speed) {
        Speed = speed;
    }


    public int getPassiveInsight() {
        return PassiveInsight;
    }


    public void setPassiveInsight(int passiveInsight) {
        PassiveInsight = passiveInsight;
    }


    public int getPassivePerception() {
        return PassivePerception;
    }


    public void setPassivePerception(int passivePerception) {
        PassivePerception = passivePerception;
    }


    public int getMaxHP() {
        return MaxHP;
    }


    public void setMaxHP(int maxHP) {
        MaxHP = maxHP;
    }


    public int getCurrentHP() {
        return CurrentHP;
    }


    public void setCurrentHP(int currentHP) {
        CurrentHP = currentHP;
    }


    public int getStr() {
        return Str;
    }


    public void setStr(int str) {
        Str = str;
    }


    public int getCon() {
        return Con;
    }


    public void setCon(int con) {
        Con = con;
    }


    public int getDex() {
        return Dex;
    }


    public void setDex(int dex) {
        Dex = dex;
    }


    public int getIntel() {
        return Intel;
    }


    public void setIntel(int intel) {
        Intel = intel;
    }


    public int getWis() {
        return Wis;
    }


    public void setWis(int wis) {Wis = wis;}
    public int getCha() {return Cha;}
    public void setCha(int cha) {Cha = cha;}
    
    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int newX){x = newX;}
    public void setY(int newY){y = newY;}
    
}
