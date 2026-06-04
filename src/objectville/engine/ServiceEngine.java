package objectville.engine;

import objectville.model.Cell;
import objectville.model.ServiceProvider;
import objectville.model.Zone;

public class ServiceEngine {
    public void updateServices(Cell[][] grid){
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if(grid[row][column] instanceof ServiceProvider provider){
                    applyServiceRange(grid,row,column,provider.getRadius(),provider.getServiceType());
                }
            }
        }
    }
    public void applyServiceRange(Cell[][] grid, int startRow, int startCol, int radius, String serviceType){
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Error: Map grid cannot be null or empty.");
        }
        if (startRow < 0 || startRow >= grid.length || startCol < 0 || startCol >= grid[0].length) {
            throw new IllegalArgumentException("Error: Service station coordinates are out of bounds. Row: " + startRow + ", Col: " + startCol);
        }
        if (radius <= 0) {
            throw new IllegalArgumentException("Error: Service radius must be greater than zero. Given: " + radius);
        }
        int totalRows = grid.length;
        int totalCols = grid[0].length;
        int minRow = Math.max(startRow-radius,0);
        int maxRow = Math.min(startRow+radius,totalRows-1);
        int minCol = Math.max(startCol-radius,0);
        int maxCol = Math.min(startCol+radius,totalCols-1);
        for (int i = minRow; i <= maxRow ; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (grid[i][j] instanceof Zone){
                    int distance = Math.abs(i - startRow) + Math.abs(j - startCol);
                    if (distance > radius) {
                        continue;
                    }
                    Zone zone = (Zone) grid[i][j];
                    String className = zone.getClass().getSimpleName();
                    if (className.equals("Housing")) {
                        className = "House";
                    }
                    if (serviceType.equals("Security") && !zone.isHasSecurity()){
                        zone.setHasSecurity(true);
                        System.out.println(className + " at (" + i + "," + j + ") received security service");
                    } else if (serviceType.equals("Health") && !zone.isHasHealth()){
                        zone.setHasHealth(true);
                        System.out.println(className + " at (" + i + "," + j + ") received health service");
                    } else if (serviceType.equals("Education") && !zone.isHasEducation()) {
                        zone.setHasEducation(true);
                        System.out.println(className + " at (" + i + "," + j + ") received education service");
                    }
                }
            }
        }
    }
}
