#k

class Terrain
  attr_reader :name

  def initialize(name)
    @name = name
  end
  
  def rep
    [@name]
  end
end

class Matrix
  def initialize(cols,rows)
    @rows = rows
    @cols = cols
    @data = []
    rows.times do |y|
      @data[y] = Array.new(cols)
    end
  end
  
  def [](x,y)
    @data[y][x] = value
  end
  def []=(x,y,value)
    @data[y][x] = value
  end
end

class Matrix
  def all_positions
    (0...@rows).collect do |y|
      (0...@cols).collect do |x|
        [x,y]
      end
    end.inject([]){|a,b| a.concat b}
  end
end

class Map
  attr_reader :terrain, :units
  def initialize(key,layout)
    rows = layout.split("\n")
    rows.collect! {|row| row.gsub(/\s+/,'').split(//)}
    y = rows.size
    x = rows[0].size
    @terrain = Matrix.new(x,y)
    @units = Matrix.new(x,y)
    
    rows.each_with_index do |row,y|
      row.each_with_index do |glyph,x|
        @terrain[x,y] = key[glyph]
      end
    end
  end
      
  def place(x,y,unit)
    @units[x,y] = unit
    unit.x = x
    unit.y = y
  end
  
  def move(old_x,old_y,new_x,new_y)
    raise LocationOccupiedError.new(new_x,new_y) if @units[new_x,new_y]
    @units[new_x,new_y] = @units[old_x,old_y]
    @units[old_x,old_y] = nil
  end
end

class LocationOccupiedError < Exception
end

forest = Terrain.new("Forest")
grass = Terrain.new("Grass")
mountain = Terrain.new("Mountain")

terrain_key = {
  "f" => forest,
  "g" => grass,
  "m" => mountain,
  }
  
map = Map.new terrain_key, <<-END
  gggggggg
  gggggggg
  ggmmmmgg
  mggfffgg
  mmfffggm
  END


 