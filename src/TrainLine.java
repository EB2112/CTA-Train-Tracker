import java.awt.*;
import java.util.List;

public class TrainLine {
    private String name;
    private TrainStation[] stations;
    private List<Station> plottedStations;
    private java.awt.Color color;

    public TrainLine(String name, TrainStation[] stations) {
        this.name = name;
        this.stations = stations;
        setColor(this.name);
    }

    public String getName() {
        return name;
    }

    public TrainStation[] getStations() {
        return stations;
    }

    public void setPlottedStations(List<Station> plottedStations) {
        this.plottedStations = plottedStations;
    }

    public List<Station> getPlottedStations() {
        return plottedStations;
    }

    public void setColor(String color) {
        switch (color.toLowerCase()) {
            case "red" -> this.color = new Color(206, 0, 0,255);
            case "blue" -> this.color = Color.blue;
            case "brown" -> this.color = Color.decode("#964B00");
            case "green" -> this.color = new Color(0, 150, 0 ,255);
            case "orange" -> this.color = new Color(255, 107, 2,255);
            case "pink" -> this.color = Color.pink;
            case "purple" -> this.color = Color.decode("#800080");
            case "yellow" -> this.color = new Color(241, 237, 10,255);

        }
    }

    public Color getColor() {
        return color;
    }

    public int indexOf(int stationID) {
        for (int i = 0; i < plottedStations.size(); i++) {
            if (plottedStations.get(i).ID() == stationID) {
                return i;
            }
        }
        return -1;

    }
}
