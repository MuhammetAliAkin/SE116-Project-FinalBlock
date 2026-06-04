package objectville.model;

public class Housing extends Zone {
    private int lifestyleReceived;
    public Housing(int x, int y) {
        super(x, y, 'H');
    }
    @Override
    public char getType() {
        return 'H';
    }

    public int getLifestyleReceived() {
        return lifestyleReceived;
    }

    public void setLifestyleReceived(int lifestyleReceived) {
        this.lifestyleReceived = lifestyleReceived;
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
                return getM()*2 + lifestyleReceived;
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
        return (lifestyleReceived > 0);
    }
    @Override
    public void resetPool() {
        lifestyleReceived = 0;
    }

    @Override
    public boolean hasRequiredServices() {
        return hasSecurity  && hasHealth && hasEducation;
    }
    public String getSimpleName(){
        return "House";
    }
}
