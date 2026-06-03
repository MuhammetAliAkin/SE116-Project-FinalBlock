package objectville.main;

import objectville.io.MapReader;
import objectville.model.Cell;

public class ObjectvilleGame {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar ObjectVilleGame.jar <mapfile> <ticks>");
            return;
        }

        String mapFile = args[0];
        int ticks;
        try {
            ticks = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: Tick count must be an integer. Given: " + args[1]);
            return;
        }

        if (ticks <= 0) {
            System.out.println("Error: Tick count must be a positive integer. Given: " + ticks);
            return;
        }

        Cell[][] grid = MapReader.readMap(mapFile);
        if (grid == null) {
            System.out.println("Error: Could not load map from: " + mapFile);
            return;
        }

        SimulationManager simulation = new SimulationManager(grid);
        simulation.run(ticks);
    }
}
