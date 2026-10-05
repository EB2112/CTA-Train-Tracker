import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java Main <apiKey>");

            return;
        }
 
        String apiKey = args[0];
        String[] routes = {"red", "blue", "brn", "g", "org", "p", "pink", "y"};

        Client client = new Client(apiKey);
        List<Train> trains = client.getTrainsOnRoutes(routes);
        TrainMapPanel map = new TrainMapPanel(apiKey);
        map.setTrains(trains);
        JFrame jFrame = new JFrame("CTA Map");
        JScrollPane jScrollPane = new JScrollPane(map);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jScrollPane);
      jFrame.setSize(1000, 1200);
        jFrame.setVisible(true);
        System.out.println(client.getStationArrivals(40340));

        System.out.println("Found " + trains.size() + " trains:");
        for (Train t : trains) {
            System.out.println(t);
        }
        int timerDelay = 5000; //5 seconds
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<Train> trains = null;
                try {
                    trains = client.getTrainsOnRoutes(routes);
                    System.out.println("Updated");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                map.setTrains(trains);
            }
        };
        new Timer(timerDelay, task).start();
    }
}

