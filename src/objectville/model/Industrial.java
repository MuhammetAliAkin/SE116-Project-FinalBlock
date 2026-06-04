package objectville.model;

public class Industrial extends Zone {
    private int populationReceived;
    public Industrial(int x, int y) {
        super(x, y, 'I');
    }

    @Override
    public char getType() {
        return 'I';
    }

    public int getPopulationReceived() {
        return populationReceived;
    }

    public void setPopulationReceived(int populationReceived) {
        this.populationReceived = populationReceived;
    }

    public int getCurrentOutput() {
        switch (level){
            case 0:
                return 0;
            case 1:
                return getM();
            case 2:
                return getM()*2;
            case 3:
                return (getM()*2) + populationReceived;
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
        return (populationReceived > 0);
    }
    @Override
    public void resetPool() {
        populationReceived = 0;
    }

    @Override
    public int getM() {
        return Math.min(electricityReceived, waterReceived);
    }

    @Override
    public boolean hasRequiredServices() {
        return hasSecurity;
    }
}
