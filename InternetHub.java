public class InternetHub extends Cell {
    private int internetSupply;
    public InternetHub(int x, int y) {
        super(x, y, 'T');
    }

    public int getInternetSupply() {
        return internetSupply;
    }

    public void setInternetSupply(int internetSupply) {
        this.internetSupply = internetSupply;
    }

    @Override
    public char getType() {
        return 'T';
    }
    @Override
    public void tick() {
        internetSupply = 100;
    }
}
