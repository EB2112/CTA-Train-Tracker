import javax.swing.*;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java Main <apiKey>");
//            System.out.println("Route codes: red, blue, brn, g, org, p, pink, y");
            return;
        }
 
        String apiKey = args[0];

        String[] routes = {"red", "blue", "brn", "g", "org", "p", "pink", "y"};
//        System.arraycopy(args, 1, routes, 0, routes.length);
 
        Client client = new Client(apiKey);
        TrainMapPanel map = new TrainMapPanel();
        JFrame jFrame = new JFrame("CTA Map");
        JScrollPane jScrollPane = new JScrollPane(map);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jScrollPane);
      jFrame.setSize(1000, 1200);
        jFrame.setVisible(true);

        List<Train> trains = client.getTrainsOnRoutes(routes);
        map.setTrains(trains);
        System.out.println("Found " + trains.size() + " trains:");
        for (Train t : trains) {
            System.out.println(t);
        }

    }
}

