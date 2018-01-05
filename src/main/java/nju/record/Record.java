package nju.record;


import nju.creature.Creature;

public class Record {
    public enum RecordType {
        CREATE, MOVE, HURT, DEAD, BULLET_CREATE, BULLET_MOVE, BULLET_REMOVE
    };

    public RecordType type;
    public long time;
    public int id;
    public Creature creature;
    public int positionX;
    public int positionY;
    public int x, y;
    public double health;
    public double angle;
    public String src;

    @Override
    public String toString() {
        if (creature == null)
            return type + " time=" + time + " id=" + id  + " (pX,pY)=" + positionX + ", " + positionY + " (x,y)="+
                    x+", "+y+" health="+health + " angle="+angle + " src="+src;
        else
            return type + " time=" + time + " id=" + id + " creature=" + creature.getClass().getName() + " (pX,pY)=" + positionX + ", " + positionY + " (x,y)="+
                    x+", "+y+" health="+health + " angle="+angle + " src="+src;
    }

}
