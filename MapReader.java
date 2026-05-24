import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapReader {
    public static Cell[][] readMap(String fileName){
        Cell[][] cells = null;
        List<String> allLines = new ArrayList<>();
        try(BufferedReader bReader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    allLines.add(line.trim());
                }
            }
            if (allLines.isEmpty()) return null;

            int rowCount = allLines.size();
            int columnCount = allLines.get(0).length();
            cells = new Cell[rowCount][columnCount];

            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                String lineIndex = allLines.get(rowIndex);
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    char cellType = lineIndex.charAt(columnIndex);
                    if (cellType == 'H') cells[rowIndex][columnIndex] = new Housing(rowIndex, columnIndex);
                    else if (cellType == 'I') cells[rowIndex][columnIndex] = new Industrial(rowIndex, columnIndex);
                    else if (cellType == 'C') cells[rowIndex][columnIndex] = new Commercial(rowIndex, columnIndex);
                    else if (cellType == 'P') cells[rowIndex][columnIndex] = new PowerPlant(rowIndex, columnIndex);
                    else if (cellType == 'W') cells[rowIndex][columnIndex] = new WaterPumpingStation(rowIndex, columnIndex);
                    else if (cellType == 'T') cells[rowIndex][columnIndex] = new InternetHub(rowIndex, columnIndex);
                    else if (cellType == 'F') cells[rowIndex][columnIndex] = new PoliceStation(rowIndex, columnIndex);
                    else if (cellType == 'D') cells[rowIndex][columnIndex] = new Hospital(rowIndex, columnIndex);
                    else if (cellType == 'S') cells[rowIndex][columnIndex] = new School(rowIndex, columnIndex);
                    else if (cellType == 'R') cells[rowIndex][columnIndex] = new Road(rowIndex, columnIndex);
                    else if (cellType == 'E') cells[rowIndex][columnIndex] = new EmptyCells(rowIndex, columnIndex);
                    else {
                        throw new IllegalArgumentException("Error: Invalid character '" + cellType + "' in map file at (" + rowIndex + ", " + columnIndex + ").");
                    }
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return null;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
        return cells;
    }
}
