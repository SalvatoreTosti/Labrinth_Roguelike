package world_helpers;

import java.util.ArrayList;
import java.util.HashMap;

import source.Sprite;
import source.World;
/*
 * The WorldBuilder class is a helper class which is used to contain all the functions for building a world.
 * This contains all the 'logic' for how to build a random world, with regard to accessibility and
 * tile sprite, i.e. corners get a 'corner' sprite, floors get 'floor' sprites, ect.
 */


public class WorldBuilder {
    World WORLD_REFERENCE;
    
    
    public WorldBuilder(World world){
        WORLD_REFERENCE = world;
    }
    
    private void readLabrith(){
        //This method could allow for the quick production of 'binary' images which could be used
        //as 'blueprints' for levels
        
    }
    
    /*Referenced algorithm from here:
     * http://www.roguebasin.com/?title=Dungeon-Building_Algorithm#Java_example
    */
    
    public void buildRandomLabrinth(HashMap<String, Sprite> sprites){ 
        //This function is used to create a random 'Room and corridor' type of map
        int sizex = WORLD_REFERENCE.getSizeX();
        int sizey = WORLD_REFERENCE.getSizeY();
        int rooms = 10;
        
        boolean building = true;
        BooleanTileMap worldBoolean = null;
        while(building){
            worldBoolean = new BooleanTileMap(sizex,sizey);
            BooleanTileMap first = new BooleanTileMap(5,5);
            first.invertMap();
            
            placeTileMap(worldBoolean,first);
            for(int i=0;i<rooms;i++){
                boolean hung = expandDungeon(worldBoolean);
                if(hung){
                    building = true;
                    break;}
                }
            building = false;
         }
        
        placeSpriteTiles(sprites,worldBoolean);
        
       /* for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                Sprite tmpSprt;
                if(worldBoolean.getValue(i, j)==true){tmpSprt = sprites.get("Blank");}
                else{tmpSprt = sprites.get("Test");}
                WORLD_REFERENCE.getTileMap().get(i).get(j).setSprite(tmpSprt);}
            }*/
    }

    
    private void placeSpriteTiles(HashMap<String, Sprite> sprites, BooleanTileMap worldBoolean){
       
     
        
        for(int i=0;i<worldBoolean.getSizex();i++){
            for(int j=0;j<worldBoolean.getSizey();j++){
               Sprite tmp = pickSprite(sprites,i,j,worldBoolean);
               WORLD_REFERENCE.getTileMap().get(i).get(j).setSprite(tmp);
            }}
    }
              
    private Sprite pickSprite(HashMap<String, Sprite> sprites, int x, int y,  BooleanTileMap wb){
    
    /* UL UM UR
     * ML MM MR
     * LL LM LR
     */
        //a b c
        //d e f
        //g h i
    
        int ULx,ULy,UMx,UMy,URx,URy;
        int MLx,MLy,MMx,MMy,MRx,MRy;
        int LLx,LLy,LMx,LMy,LRx,LRy;
        boolean a,b,c,d,e,f,g,h,i;
        
        ULx = x-1;   ULy = y-1;
        UMx = x;     UMy = y-1;
        URx = x+1;   URy = y-1;
        
        MLx = x-1;   MLy = y;
        MMx = x;     MMy = y;
        MRx = x+1;   MRy = y;
        
        LLx = x-1;   LLy = y+1;
        LMx = x;     LMy = y+1;
        LRx = x+1;   LRy = y+1;
        
        if(ULx<0 || ULy<0){a=false;}
        else{a = wb.getValue(ULx, ULy);}
        
        if(UMy<0){b=false;}
        else{b = wb.getValue(UMx, UMy);}
        
        if(URx>=wb.getSizex() ||URy<0){c=false;}
        else{c = wb.getValue(URx, URy);}
        
        if(MLx<0){d = false;}
        else{d = wb.getValue(MLx, MLy);}
        
        e = wb.getValue(MMx, MMy); //no test conditions needed?
        
        if(MRx>=wb.getSizex()){f=false;}
        else{f = wb.getValue(MRx, MRy);}
        
        if(LLx<0 || LLy>=wb.getSizey()){g=false;}
        else{g = wb.getValue(LLx, LLy);}
        
        if(LMy>=wb.getSizey()){h=false;}
        else{h = wb.getValue(LMx, LMy);}
        
        if(LRx>=wb.getSizex() || LRy>=wb.getSizey()){i=false;}
        else{i = wb.getValue(LRx, LRy);}
                
        boolean[] tmpArr = new boolean[9];
        tmpArr[0]= a;
        tmpArr[1]= b;
        tmpArr[2]= c;
        tmpArr[3]= d;
        tmpArr[4]= e;
        tmpArr[5]= f;
        tmpArr[6]= g;
        tmpArr[7]= h;
        tmpArr[8]= i;
        
        Sprite s = determineTileSituation(sprites,tmpArr);
        return s;}
    
    private Sprite determineTileSituation(HashMap<String, Sprite> sprites, boolean[] arr){
        Sprite tile = null;
        /* 0 1 2
         * 3 4 5
         * 6 7 8 */
        boolean NW,NN,NE,WW,CENT,EE,SW,SS,SE;
        NW = arr[0]; NN = arr[1]; NE = arr[2];
        WW = arr[3]; CENT=arr[4]; EE = arr[5];
        SW = arr[6]; SS = arr[7]; SE = arr[8];        
        
        if(CENT && ((NN && SS && !EE && !WW) || (EE && WW && !NN && !SS))){tile=sprites.get("Wall_Door");} //Doors
        else if(CENT){tile=sprites.get("Blank");} //all 'true' tiles are floor or doors
        else if(EE && !NN && !SS && !WW){tile=sprites.get("Wall_ML");} //vertical wall
        else if(WW && !NN && !SS && !EE){tile=sprites.get("Wall_RM");} //vertical wall
        else if(NN && !WW && !SS && !EE){tile=sprites.get("Wall_LM");} //Horiz wall facing N
        else if(SS && !WW && !NN && !EE){tile=sprites.get("Wall_UM");} //Horis wall facing S
        
        else if(SE && !EE && !SS){tile=sprites.get("Wall_UL");} //upper left corner
        else if(SW && !WW && !SS){tile=sprites.get("Wall_UR");} //upper right corner
        else if(NE && !EE && !NN){tile=sprites.get("Wall_LL");} //lower left corner
        else if(NW && !WW && !NN){tile=sprites.get("Wall_LR");} //lower right corner
        
       
        else{tile = sprites.get("Test");}
        return tile;
    }
    
    
    private boolean expandDungeon(BooleanTileMap worldBoolean){
        boolean timeOut = false;
        boolean roomAdded = false;
        int worldx = WORLD_REFERENCE.getSizeX();
        int worldy = WORLD_REFERENCE.getSizeY();
        int trys = 0;
        long startTime = System.nanoTime();
        long endTime = startTime + 1000000000*5;
        while(!roomAdded){
            if(System.nanoTime() >= endTime){
                timeOut = true;
                break;}
            
            trys = 0;
            BooleanTileMap randomRoom = buildRandomRoom();
            while(trys<100){
                int x = (int) (Math.random()*(worldx-randomRoom.getSizex()));
                int y = (int) (Math.random()*(worldy-randomRoom.getSizey()));
                boolean validSpot = checkAdjacentRandomSpot(worldBoolean,randomRoom,x,y);
                if(!validSpot){
                    trys++;
                    continue;} 
                boolean roomFits = checkRoomFit(worldBoolean,randomRoom,x,y);
                if(!roomFits){
                    trys++;
                    continue;}
                
                placeRoom(worldBoolean,randomRoom,x,y);
                roomAdded = true; 
                break;
            }
        }
        return timeOut;}
    
    private BooleanTileMap buildRandomRoom(){
        //boolean corridor = ((int)(Math.random()*4))==0;
        //boolean corridor = false;
        boolean circ = ((int)(Math.random()*4))==0;
        //boolean circ =true;
        int x,y;
        if(circ){
            x=6;
            y=6;
            BooleanTileMap map = new BooleanTileMap(x,y);
            map.invertMap();
            //upper left corner
            map.setValue(0, 0, false);
            map.setValue(1, 0, false);
            map.setValue(0, 1, false);
            
            //lower left corner
            map.setValue(0, 5, false);
            map.setValue(0, 4, false);
            map.setValue(1, 5, false);
            
            //lower upper corner
            map.setValue(5, 0, false);
            map.setValue(5, 1, false);
            map.setValue(4, 0, false);
            
            //lower right corner
            map.setValue(5, 5, false);
            map.setValue(5, 4, false);
            map.setValue(4, 5, false);
            return map;
            }
        
        //if(corridor){
            //boolean vert = ((int)(Math.random()*2))==0;
           // x=5;
          //  y=1;}
            /*if(vert){
                x=1;
                y= ((int)Math.random()*5);}
            
            else{
                x= ((int)Math.random()*5);
                y=1;}
            }*/
        //not a corridor
            //x = ((int)Math.random()*7);
            //y = ((int)Math.random()*7);
    
           x=3;
           y=3;
           x+=((int)(Math.random()*5));
           y+=((int)(Math.random()*5));
        
        BooleanTileMap map = new BooleanTileMap(x,y);
        map.invertMap();
        return map;}
    
    private boolean checkAdjacentRandomSpot(BooleanTileMap worldBoolean, BooleanTileMap room,int x, int y){
        //+1 for walls?
        if(x+room.getSizex()+1>WORLD_REFERENCE.getSizeX()){return false;}
        if(y+room.getSizey()+1>WORLD_REFERENCE.getSizeY()){return false;}
        //if(x+1>=worldBoolean.getSizex()){return false;}
        //if(y+1>=worldBoolean.getSizey()){return false;}
        if(x == 0 || y == 0){return false;}
        boolean north,south,east,west;
        north = worldBoolean.getValue(x, y-1)==true;
        south = worldBoolean.getValue(x, y+1)==true;
        east  = worldBoolean.getValue(x+1, y)==true;
        west  = worldBoolean.getValue(x-1, y)==true;
        if(north && !south && !east && !west){return true;}
        if(!north && south && !east && !west){return true;}
        if(!north && !south && east && !west){return true;}
        if(!north && !south && !east && west){return true;}
        //These checks ensure that the 'spot' picked is on the perimeter of the 'true' area.
        //thus, it would only be surrounded on 1 side by a 'true' value
        return false;}
    
    private boolean checkRoomFit(BooleanTileMap worldBoolean, BooleanTileMap room,int x, int y){
        //!!! NOTE because we checked to ensure that the point is an outlier in the checkAdjacentRandomSpot()
        //we do not have to recheck in this, and can simply identify which direction the 'true' space is
        //relative to the selected spot.
        boolean north,south,east,west;
        north = worldBoolean.getValue(x, y-1)==true;
        south = worldBoolean.getValue(x, y+1)==true;
        east  = worldBoolean.getValue(x+1, y)==true;
        west  = worldBoolean.getValue(x-1, y)==true;
        
        if(north){   System.out.println("North");}
        else if(south){   System.out.println("South");}
        else if(east){   System.out.println("East");}
        else if(west){   System.out.println("West");}
        else{   System.out.println("checkRoomFit problem.");}
        
        int maxX;
        int maxY;
        int minX;
        int minY;
        int halfRoomSizex,halfRoomSizey;
        if(room.getSizex()==1){halfRoomSizex=1;}
        else{halfRoomSizex=room.getSizex()/2;}
        if(room.getSizey()==1){halfRoomSizey=1;}
        else{halfRoomSizey=room.getSizey()/2;}
        
        if(north){
            //check to the south
            /*...........
             *...#####...
             *...#####...
             *...#####...
             *.....X..... 
             */
            
            maxX = x+halfRoomSizex;
            maxY = y+room.getSizey()+1; //+1 is for door way, i.e. where the x is on the map above.
            minX = x-halfRoomSizex;
            minY = y;
            
            //padding
            maxX +=1;
            maxY +=1;
            minX -=1;
            //minY -=1;
            
            if(maxX>=worldBoolean.getSizex() ||
               maxY>=worldBoolean.getSizey() ||
               minX<0 || minY <0){return false;}
           
            for(int i=minX;i<=maxX;i++){ // was just <
                for(int j=minY;j<=maxY;j++){ // was just <
                    if(worldBoolean.getValue(i,j)==true){return false;} 
                    //if any space is already used i.e. 'true', this is an invalid placement.
                }}
           return true;} //if all tests passed, it is valid placement.
        
        if(south){
            //check to the north
            /*.....X.....
             *...#####...
             *...#####...
             *...#####...
             *........... 
             */
            maxX = x+halfRoomSizex;
            maxY = y;
            minX = x-halfRoomSizex;
            minY = y-room.getSizey();//-1 is for door way, i.e. where the x is on the map above.
            //padding
            maxX +=1;
            //maxY +=1;
            minX -=1;
            minY -=1;
            
            if(maxX>=worldBoolean.getSizex() ||
               maxY>=worldBoolean.getSizey() ||
               minX<0 || minY <0){return false;}
            for(int i=minX;i<=maxX;i++){
                for(int j=minY;j<=maxY;j++){
                    if(worldBoolean.getValue(i,j)==true){return false;} 
                }}
            return true;}
        
        
        if(east){
            //check to the west
            /*...........
             *...#####...
             *..X#####...
             *...#####...
             *........... 
             */
  
            maxX = x;
            maxY = y+halfRoomSizey;
            minX = x-room.getSizex()-1;//-1 is for door way, i.e. where the x is on the map above.
            minY = y-halfRoomSizey;
            
            //padding
            //maxX +=1;
            maxY +=1;
            minX -=1;
            minY -=1;
            
            if(maxX>=worldBoolean.getSizex() ||
               maxY>=worldBoolean.getSizey() ||
               minX<0 || minY <0){return false;}
            for(int i=minX;i<=maxX;i++){
                for(int j=minY;j<=maxY;j++){
                    if(worldBoolean.getValue(i,j)==true){return false;} 
                }}
            return true;}
        
        if(west){
            //check to the east
            /*...........
             *...#####...
             *...#####X..
             *...#####...
             *........... 
             */
            maxX = x+room.getSizex()+1; //+1 is for door way, i.e. where the x is on the map above.
            maxY = y+halfRoomSizey;
            minX = x; 
            minY = y-halfRoomSizey;
            
            //padding
            maxX +=1;
            maxY +=1;
            //minX -=1;
            minY -=1;
            
            if(maxX>=worldBoolean.getSizex() ||
               maxY>=worldBoolean.getSizey() ||
               minX<0 || minY <0){return false;}
            for(int i=minX;i<=maxX;i++){
                for(int j=minY;j<=maxY;j++){
                    if(worldBoolean.getValue(i,j)==true){return false;} 
                }}
            return true;}
        
        System.out.println("Something failed in WorldBuilder.checkRoomFit().");
        return false;} //return false catch if all directional checks fail.
    
    private void placeRoom(BooleanTileMap worldBoolean,BooleanTileMap room,int x,int y){
        boolean north,south,east,west;
        north = worldBoolean.getValue(x, y-1)==true;
        south = worldBoolean.getValue(x, y+1)==true;
        east  = worldBoolean.getValue(x+1, y)==true;
        west  = worldBoolean.getValue(x-1, y)==true;
        
        int upLeftX;
        int upLeftY;
        int halfRoomSizex,halfRoomSizey;
        if(room.getSizex()==1){halfRoomSizex=1;}
        else{halfRoomSizex=room.getSizex()/2;}
        if(room.getSizey()==1){halfRoomSizey=1;}
        else{halfRoomSizey=room.getSizey()/2;}
        
        //!!!NOTE: directions refer to which blocks are 'true'.
        //i.e. 'north' would indicate that the program should be searching to the 'south' of the indicated area
        //because the 'northern' blocks are already marked as 'true'.
        
        if(north){
            /*...........
             *...#####...
             *...#####...
             *...#####...
             *.....X..... 
             */
            //maxX = x+halfRoomSizex;
            //maxY = y+room.getSizey()+1; //+1 is for door way, i.e. where the x is on the map above.
            upLeftX = x-halfRoomSizex;
            upLeftY = y+1;//+1
            worldBoolean.setValue(x,y,true);
            copyBooleanMapSection(worldBoolean,room,upLeftX,upLeftY);
        }
        if(south){
            /*.....X.....
             *...#####...
             *...#####...
             *...#####...
             *........... 
             */
            upLeftX = x-halfRoomSizex;
            upLeftY = y-room.getSizey(); //-1 included
            worldBoolean.setValue(x,y,true);
            copyBooleanMapSection(worldBoolean,room,upLeftX,upLeftY);
        }
        if(east){
            /*...........
             *...#####...
             *..X#####...
             *...#####...
             *........... 
             */
            upLeftX = x-room.getSizex();//room size includes -1? b/c not zero based
            upLeftY = y-halfRoomSizey;
            worldBoolean.setValue(x,y,true);
            copyBooleanMapSection(worldBoolean,room,upLeftX,upLeftY);
        }
        if(west){
            /*...........
             *...#####...
             *...#####X..
             *...#####...
             *........... 
             */
            upLeftX = x+1;//+1
            upLeftY = y-halfRoomSizey;
            worldBoolean.setValue(x,y,true);
            copyBooleanMapSection(worldBoolean,room,upLeftX,upLeftY);
        }        
        
    }
    
    private void placeTileMap(BooleanTileMap big, BooleanTileMap small){
        int maxX = big.getSizex();
        int maxY = big.getSizey();
        maxX -= small.getSizex();
        maxY -= small.getSizey();
        int startx = (int)(Math.random()*maxX);
        int starty = (int)(Math.random()*maxY);
        copyBooleanMapSection(big,small,startx,starty);
        
    }
    
    private void copyBooleanMapSection(BooleanTileMap big, BooleanTileMap small, int startX, int startY){
        int tmpx = startX;
        int tmpy = startY;
        
        for(int i=0;i<small.getSizex();i++){
            for(int j=0;j<small.getSizey();j++){
                boolean b = small.getValue(i,j);
                big.setValue(tmpx,tmpy, b);
                tmpy++; 
                }
            tmpy=startY;
            tmpx++;}
    }
    
    
}
