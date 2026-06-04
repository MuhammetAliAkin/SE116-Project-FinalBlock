package objectville.model;

public class Commercial extends Zone {
    private int populationReceived;
    private int goodsReceived;
    public Commercial(int x, int y) {
        super(x, y, 'C');
    }

    @Override
    public char getType() {
        return 'C';
    }

    public int getPopulationReceived() {
        return populationReceived;
    }

    public void setPopulationReceived(int populationReceived) {
        this.populationReceived = populationReceived;
    }

    public int getGoodsReceived() {
        return goodsReceived;
    }

    public void setGoodsReceived(int goodsReceived) {
        this.goodsReceived = goodsReceived;
    }

    @Override
    public int getCurrentOutput() {
        switch (level){
            case 0:
                return 0;
            case 1:
                return getM();
            case 2:
                return getM()*2;
            case 3:
                return (getM()*2)+ Math.min(populationReceived,goodsReceived);
            default:
                throw new IllegalStateException("Error: Invalid zone level: " + level);
        }
    }

    @Override
    public int getUtilityDemand() {
        return Math.max(1,getCurrentOutput());
    }

    @Override
    public boolean canUpgradeToLevel3() {
        return ((populationReceived > 0) && (goodsReceived > 0));
    }

    @Override
    public void resetPool() {
        populationReceived = 0;
        goodsReceived = 0;
    }

    @Override
    public boolean hasRequiredServices() {
        return hasSecurity;
    }
}
