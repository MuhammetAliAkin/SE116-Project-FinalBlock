public class EmptyCells extends Cell {
    public EmptyCells(int x, int y) {
        super(x, y, 'E');
    }

    @Override
    public char getType() {
        return 'E';
    }
    @Override
    public void tick() {
        // Empty
    }
}
