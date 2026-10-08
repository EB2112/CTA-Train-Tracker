import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.*;

public class TrainMapPanel extends JPanel {

    public static int canvasWidth = 1000;
    public static int canvasHeight = 2310;
    private List<Train> trains = new ArrayList<>();
    private final int linesSize = TrainLayout.lines.trainLines.size();
    private ArrayList<TrainLine> trainLines = TrainLayout.lines.trainLines;
    private Client client;
    private final String apiKey;
    private Station selectedStation;
    private PopUp popUp;
    private final Map<String, TrainMotion> motions = new HashMap<>();

    public TrainMapPanel(String apiKey) {
        setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));
        this.addMouseListener(new MouseAdapter() {
            //click listener for eventual feature to display when next trains will be arriving
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    clickHandler(e.getX(), e.getY(), 10);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        this.apiKey = apiKey;
        client = new Client(apiKey);
        new Timer(30, e -> repaint()).start();
    }

    public void setTrains(List<Train> newTrains) {
        this.trains = newTrains;
        long now = System.currentTimeMillis();
        Set<String> placedRunNumbers = new HashSet<>();
        for(Train train : trains){
            Point target = trainCoordinates(train);
            if(target == null || train.runNumber.isEmpty() || target.y == 0 || target.x == 0){
                continue;
            }
            placedRunNumbers.add(train.runNumber);
            if(motions.get(train.runNumber) == null){
                motions.put(train.runNumber, new TrainMotion(target.x, target.y, target.x, target.y, now));

            }else {
                Point current = motions.get(train.runNumber).positionAt(now);
                motions.put(train.runNumber, new TrainMotion(current.x, current.y, target.x, target.y, now));
            }
//            System.out.println(
//                    train.runNumber +
//                            " route=" + train.routeName +
//                            " direction=" + train.direction +
//                            " next=" + train.nextStationId +
//                            " approaching=" + train.isApproaching
//            );
        }
        motions.keySet().retainAll(placedRunNumbers);
        repaint();
    }


    @Override
    //plots all the stations on the panel with names and markers
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font("Monospaced", Font.BOLD, 13));
        FontMetrics metrics = g2D.getFontMetrics();
        fillBackground(g2D);

        for (int i = 0; i < linesSize; i++) {
            TrainLine currentLine = trainLines.get(i);
            drawLines(g2D, currentLine);
            addMarkers(g2D, currentLine);
        }
        drawCurrentTrains(g2D);
    }

    private void fillBackground(Graphics2D g2D){


        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //antialiasing for smoother drawings
        g2D.setColor(Color.decode("#2b2b2b"));
        g2D.fillRect(0, 0, getWidth(), getHeight());
    }
    private void drawLines(Graphics2D g2D, TrainLine currentLine){
        for (int j = 0; j < currentLine.getPlottedStations().size() - 1; j++) {
            g2D.setColor(currentLine.getColor());
            g2D.setStroke(new BasicStroke(15, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Station a = currentLine.getPlottedStations().get(j);
            Station b = currentLine.getPlottedStations().get(j + 1);
            g2D.drawLine(a.xCoordinate(), a.yCoordinate(), b.xCoordinate(), b.yCoordinate());

        }
    }
    private void addMarkers(Graphics2D g2D, TrainLine currentLine){
        int radius = 10;
        FontMetrics metrics = g2D.getFontMetrics();
        for (int j = 0; j < currentLine.getPlottedStations().size(); j++) {
            Station station = currentLine.getPlottedStations().get(j);
            g2D.setColor(Color.white);
            g2D.fillOval(station.xCoordinate() - radius, station.yCoordinate() - radius, radius * 2, radius * 2);
            g2D.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));

            g2D.setColor(Color.decode("#222222"));

            g2D.drawOval(station.xCoordinate() - radius, station.yCoordinate() - radius, radius * 2, radius * 2);
            g2D.setColor(Color.white);
            String[] words = station.name().split(" ");

            if (words.length <= 2) { //helps with longer names
                g2D.drawString(station.name(), station.xCoordinate() + 15, station.yCoordinate() + 4);

            } else if (words.length > 2) {
                for (int y = 1; y < words.length + 1; y++) {
                    g2D.drawString(words[y - 1], station.xCoordinate() + 15, station.yCoordinate() + (12 * y));
                }
            }



        }
    }
    private void drawCurrentTrains(Graphics2D g2d) {
        long now = System.currentTimeMillis();
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        for (Train train : trains) {
            TrainMotion motion = motions.get(train.runNumber);
            if(motion == null){
                continue;
            }
            TrainLine line = TrainLayout.lines.getTrainLineLookUp(train.routeName);
            if(line == null){
                continue;
            }
            Point p = motion.positionAt(now);
//                    plotTrainDropShadow(train, g2d, trainX, trainY);
//                    plotTrain(train, g2d, trainX, trainY);
                    Color shadowColor = new Color(0, 0, 0, 216);
                    drawShadow(train, g2d, p.x + 3, p.y + 3, shadowColor);
                    drawTriangle(train, g2d, p.x, p.y, line.getColor());
                }
            }




    private void drawTriangle(Train train, Graphics2D g2d, int trainX, int trainY, Color fill){
        int width = 30;
        int height = 30;
        g2d.setColor(fill);
        g2d.setStroke((new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND)));
        int[] xIntsUp = {trainX , trainX - width / 2 , trainX + width / 2};
        int[] yIntsUp = {trainY - height / 2  , trainY + height / 2 , trainY + height / 2};
        int[] xIntsDown = {trainX, trainX + width / 2, trainX - width / 2};
        int[] yIntsDown = {trainY + height / 2, trainY - height / 2, trainY - height / 2};
        if ( trainDirection(train) == "up" ) {
            g2d.fillPolygon(xIntsUp, yIntsUp, 3);
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(xIntsUp, yIntsUp, 3);

        } else {
            g2d.fillPolygon(xIntsDown, yIntsDown, 3);
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(xIntsDown, yIntsDown, 3);
        }
    }
    private void drawShadow (Train train, Graphics2D g2d, int trainX, int trainY, Color fill){ //draws offset black triangle below train triangle
        int width = 30;
        int height = 30;
        g2d.setColor(fill);
        g2d.setStroke((new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND)));
        int[] xIntsUp = {trainX , trainX - width / 2 , trainX + width / 2};
        int[] yIntsUp = {trainY - height / 2  , trainY + height / 2 , trainY + height / 2};
        int[] xIntsDown = {trainX, trainX + width / 2, trainX - width / 2};
        int[] yIntsDown = {trainY + height / 2, trainY - height / 2, trainY - height / 2};
        if (trainDirection(train) == "up") {
            g2d.fillPolygon(xIntsUp, yIntsUp, 3);

        } else {
            g2d.fillPolygon(xIntsDown, yIntsDown, 3);

        }
    }

    private void clickHandler(int x, int y, int radius) throws Exception {
        for (TrainLine trainLine : trainLines) {
            for (Station station : trainLine.getPlottedStations()) {
                //find distance b/t click and station coordinate using distance formula
                double distance = Math.sqrt(
                        Math.pow(x - station.xCoordinate(), 2) +
                                Math.pow(y - station.yCoordinate(), 2)

                );

                if (distance <= radius) {selectedStation = station;
                   popUp = new PopUp(this, station.name(), client.getStationArrivals(station.ID()));
                   popUp.addWindowListener(new WindowAdapter() {
                       @Override
                       public void windowClosing(WindowEvent e) {
                           super.windowClosing(e);
                           selectedStation = null;
                           popUp = null;
                       }
                   });
                    return;
                }
            }

        }
    }

    private Point trainCoordinates(Train train) {
        TrainLine trainLine = TrainLayout.lines.getTrainLineLookUp(train.routeName);

        int trainY = 0;
        int trainX= 0;
        if(trainLine == null){
            return null;
        }
        int nextTrainStationIndex = trainLine.indexOf(train.nextStationId); //gets index of next train station for the train
        if(nextTrainStationIndex == -1){
            return null;
        }
        Station nextStation = trainLine.getPlottedStations().get(nextTrainStationIndex); //finds the plotted coordinates on the screen

        int x = nextStation.xCoordinate();
        int y = nextStation.yCoordinate();

        if (train.isApproaching) {
            return new Point(x, y);
        }

        int offset =
                ((canvasHeight - 80) / trainLine.getPlottedStations().size()) / 2; // places train in the middle of the two stations

        if (train.direction == 1) {
            y += offset;
        } else {
            y -= offset;
        }

        return new Point(x, y);
    }

    private String trainDirection(Train train){ //some train directions do not reflect what is drawn on the map;
        switch (train.routeName){
            case "red", "blue", "brn", "g", "p", "y" ->{if (train.direction == 1) {
                return "up";
            }
            else {
            return "down";}
            }

            case "org", "pink" ->{if (train.direction == 5) {
                return "up";
            }
            else {
                return "down";}
            }


            default -> {
                return null;
            }


        }
    }
    public void refreshOpenStation()  {
        if (selectedStation == null || popUp == null) return;
        Station station = selectedStation;

        new SwingWorker<ArrayList<Arrival>, Void>() {
            @Override
            protected ArrayList<Arrival> doInBackground() throws Exception {
                return client.getStationArrivals(station.ID());
            }

            @Override
            protected void done() {
                try {
                    if (popUp != null) { // user may have closed it while we were fetching
                        popUp.updateStation(station.name(), get());
                    }
                } catch (Exception ex) {
                    System.err.println("Arrivals refresh failed: " + ex.getMessage());
                }
            }
        }.execute();

    }

    public Station getSelectedStation() {
        return selectedStation;
    }
}

