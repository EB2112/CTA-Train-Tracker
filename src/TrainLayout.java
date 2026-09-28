import java.util.ArrayList;
import java.util.List;

public class TrainLayout {

    public static Lines lines = new Lines();
    public static final int canvasHeight = TrainMapPanel.canvasHeight;
    static {
        buildLayout();
    }



    private static void buildLayout() {

        int baseX = 240;
        int topMargin = 40;
        for (int i = 0; i < lines.trainLines.size(); i++) {
            List<Station> stations = new ArrayList<>();
            int spacingY = 70;


//            //jog is a little turn in the map to represent the loop for now might just make it all straight
//            int jogStart = 16;
//            int jogEnd = 23;
//
//            double jogMid = (jogStart + jogEnd) / 2.0;
//            double jogHalfWidth = (jogEnd - jogStart) / 2.0;
//            int maxJogOffset = 130;

            for (int j = 0; j < lines.trainLines.get(i).getStations().length; j++) {
                int y = topMargin + j * spacingY;
                int x = baseX;
                String stationName = lines.trainLines.get(i).getStations()[j].name();
                int stationId = lines.trainLines.get(i).getStations()[j].id();
//
//                if (j >= jogStart && j <= jogEnd) {
//                    double distFromMid = Math.abs(j - jogMid);
//                    double proportion = 1.0 - (distFromMid / jogHalfWidth);
//                    x = baseX + (int) Math.round(maxJogOffset * proportion);
//
//                }
                stations.add(new Station(stationName, x, y, stationId));
            }
            baseX += 190;
            lines.trainLines.get(i).setPlottedStations(stations);

        }


    }



    public TrainLayout(){
        buildLayout();
    }
}
