import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Main {
    private static final String[] ROUTES = {"red", "blue", "brn", "g", "org", "p", "pink", "y"};
    private static final int TIMER_DELAY = 10000;
    private static boolean fetching = false;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("Usage: java Main <apiKey>");

            return;
        }

        String apiKey = args[0];
        Client client = new Client(apiKey);

        SwingUtilities.invokeLater(() -> {
            TrainMapPanel map = new TrainMapPanel(apiKey);
            JFrame jFrame = new JFrame("CTA Map");
            JScrollPane jScrollPane = new JScrollPane(map);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.add(jScrollPane);
            jFrame.setSize(1000, 1200);
            jFrame.setVisible(true);

            refresh(client, map, jFrame);
            new Timer(TIMER_DELAY, e -> refresh(client, map, jFrame)).start();
        });

    }


    private static void refresh(Client client, TrainMapPanel map, JFrame jFrame) {
        if (fetching) {
            return;
        }
        fetching = true;


        new SwingWorker<List<Train>, Void>() {
            @Override
            protected List<Train> doInBackground() throws Exception {
                return client.getTrainsOnRoutes(ROUTES);
            }

            @Override
            protected void done() {
                try {
                    map.setTrains(get());
                    jFrame.setTitle("CTA Map (last updated: " + LocalTime.now().format(formatter) + ")");
                    map.refreshOpenStation();
                } catch (Exception exception) {
                    System.out.println("Fetch failed: " + exception.getMessage());
                } finally {
                    fetching = false;
                }
            }

        }.execute();
    }
}




