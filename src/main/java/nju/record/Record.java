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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Record))
            return false;
        Record record = (Record)obj;
        return type.equals(record.type) && time == record.time && id == record.id && positionX == record.positionX &&
                positionY == record.positionY && x == record.x && y == record.y
                && health == record.health && angle == record.angle;
    }


}
