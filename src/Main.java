import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java Main <apiKey> <routeCode> [routeCode2 ...]");
            System.out.println("Route codes: red, blue, brn, g, org, p, pink, y");
            return;
        }
 
        String apiKey = args[0];
        String[] routes = new String[args.length - 1];
        System.arraycopy(args, 1, routes, 0, routes.length);
 
        Client client = new Client(apiKey);
        TrainMapPanel map = new TrainMapPanel();

        List<Train> trains = client.getTrainsOnRoutes(routes);
        map.setTrains(trains);
        System.out.println("Found " + trains.size() + " trains:");
        for (Train t : trains) {
            System.out.println(t);
        }
    }
}

