import java.util.ArrayList;
import java.util.List;
//basically just hard coding all the stations and names from the different lines for now
public class BlueLineLayout {


    private static final String[] stationNames = {
            "O'Hare", "Rosemont", "Cumberland", "Harlem", "Jefferson Park",
            "Montrose", "Irving Park", "Addison", "Belmont", "Logan Square",
            "California", "Western", "Damen", "Division", "Chicago", "Grand",
            "Clark/Lake", "Washington", "Monroe", "Jackson", "LaSalle", "Clinton",
            "UIC-Halsted", "Racine", "Illinois Medical District", "Western",
            "Kedzie-Homan", "Pulaski", "Cicero", "Austin", "Oak Park", "Harlem",
            "Forest Park"
    };

    private static final int[] stationIds = {
            40890, 40820, 40230, 40750, 41280,
            41330, 40550, 41240, 40060, 41020,
            40570, 40670, 40590, 40320, 41410, 40490,
            40380, 40370, 40790, 40070, 41340, 40430,
            40350, 40470, 40810, 40220,
            40250, 40920, 40970, 40010, 40180, 40980,
            40390
    };
        public static final int canvasWidth = 700;
        public static final int canvasHeight = 900;

        public static final List<Station> stations = buildLayout();

        private static List<Station> buildLayout() {
            List<Station> stations = new ArrayList<>();
            int baseX = 220;
            int topMargin = 40;
            int spacingY = (canvasHeight - topMargin * 2) / (stationNames.length - 1);

//            //jog is a little turn in the map to represent the loop for now might just make it all straight
//            int jogStart = 16;
//            int jogEnd = 23;
//
//            double jogMid = (jogStart + jogEnd) / 2.0;
//            double jogHalfWidth = (jogEnd - jogStart) / 2.0;
//            int maxJogOffset = 130;

            for (int i = 0; i < stationNames.length; i++) {
                int y = topMargin + i * spacingY;
                int x = baseX;
//
//                if (i >= jogStart && i <= jogEnd) {
//                    double distFromMid = Math.abs(i - jogMid);
//                    double proportion = 1.0 - (distFromMid / jogHalfWidth);
//                    x = baseX + (int) Math.round(maxJogOffset * proportion);
//
//                }
                stations.add(new Station(stationNames[i], x, y, stationIds[i]));
            }
            return stations;
        }

        public static int indexOf(Integer stationID) {
            for (int i = 0; i < stations.size(); i++) {
                if (stations.get(i).ID() == stationID) {
                    return i;
                }
            }
            return -1;


        }


    }


