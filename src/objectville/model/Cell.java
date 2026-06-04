package objectville.model;

public abstract class Cell {
    protected int x;
    protected int y;
    protected char type;

    public Cell(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    public abstract char getType();
    public abstract void tick();

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
}

