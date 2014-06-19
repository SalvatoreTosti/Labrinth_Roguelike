#LabrinthEngine.py

class Ticker(object):
    def __init__(self,director):
        self.director = director
        self.ticks = 0L
        self.schedule = {}
    def scheduleTurn(self,interval,obj):
        self.schedule.setdefault(self.ticks+interval, []).append(obj)
    
    def nextTurn(self):
        thingsToDo = self.schedule.pop(self.ticks,[])
        for obj in thingsToDo:
            director.turn_handler(obj)
            

class director(object):
    def __init__(self,player,enemyList,world):
        self.player = player
        self.enemyList = enemyList
        self.world = world
    
    
    