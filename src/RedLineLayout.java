import java.util.ArrayList;
import java.util.List;

//basically just hard coding all the stations and names from the different lines for now
public class RedLineLayout {
    private static final String[] stationNames = {"Howard", "Jarvis", "Morse", "Loyola", "Granville", "Thorndale"
            , "Bryn Mawr", "Berwyn", "Argyle", "Lawrence", "Wilson", "Sheridan",
            "Addison", "Belmont", "Fullerton", "North/Clybourn", "Clark/Division",
            "Chicago", "Grand", "Lake", "Monroe", "Jackson", "Harrison", "Roosevelt",
            "Cermak-Chinatown", "Sox-35th", "47th", "Garfield", "63rd", "69th", "79th",
            "87th", "95th/Dan Ryan"};
    private static final int[] stationIds = {
            40900, 41190, 40100, 41300, 40760, 40880,
            41380, 40340, 41200, 40770, 40540, 40080,
            41420, 41320, 41220, 40650, 40630,
            41450, 40330, 41660, 41090, 40560, 41490, 41400,
            41000, 40190, 41230, 41170, 40910, 40990, 40240,
            41430, 40450
    };
    public static final int canvasWidth = 700;
    public static final int canvasHeight = 900;

    public static final List<Station> stations = buildLayout();

    private static List<Station> buildLayout() {
        List<Station> stations = new ArrayList<>();
        int baseX = 380;
        int topMargin = 40;
        int spacingY = (canvasHeight - topMargin * 2) / (stationNames.length - 1);

        int jogStart = 16;
        int jogEnd = 23;

//        double jogMid = (jogStart + jogEnd) / 2.0;
//        double jogHalfWidth = (jogEnd - jogStart) / 2.0;
//        int maxJogOffset = 130;

        for (int i = 0; i < stationNames.length; i++) {
            int y = topMargin + i * spacingY;
            int x = baseX;

//            if (i >= jogStart && i <= jogEnd) {
//                double distFromMid = Math.abs(i - jogMid);
//                double proportion = 1.0 - (distFromMid / jogHalfWidth);
//                x = baseX + (int) Math.round(maxJogOffset * proportion);
//
//            }
            stations.add(new Station(stationNames[i], x, y, stationIds[i]));
        }
        return stations;
    }

    public static int indexOf(String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).name().equalsIgnoreCase(stationName)) {
                return i;
            }
        }
        return -1;


    }


    }
