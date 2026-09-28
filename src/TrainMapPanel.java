import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrainMapPanel extends JPanel {

    public static int canvasWidth = 1000;
    public static int canvasHeight = 2310;
    private List<Train> trains;
    private final int linesSize = TrainLayout.lines.trainLines.size();
    private  ArrayList<TrainLine>  trainLines = TrainLayout.lines.trainLines;

    public TrainMapPanel(){
       setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));


    }

    public void setTrains(List<Train> newTrains){
        this.trains = newTrains;
        repaint();
    }


    @Override
    //plots all the stations on the panel with names and markers
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font("Monospaced", Font.BOLD, 13));
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //antialiasing for smoother drawings
        g2D.setColor(Color.decode("#2b2b2b"));
        g2D.fillRect(0,0, getWidth(), getHeight());
        int radius = 10;


        for(int i = 0; i < linesSize; i++){
           TrainLine currentLine = trainLines.get(i);

        for(int j = 0; j < currentLine.getPlottedStations().size() - 1 ; j ++){
            g2D.setColor(currentLine.getColor());
            g2D.setStroke(new BasicStroke(15, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Station a = currentLine.getPlottedStations().get(j);
            Station b = currentLine.getPlottedStations().get(j + 1);
            g2D.drawLine(a.xCoordinate(), a.yCoordinate(), b.xCoordinate(), b.yCoordinate());

        }
        for(int j = 0; j < currentLine.getPlottedStations().size(); j ++){
            Station station = currentLine.getPlottedStations().get(j);
            g2D.setColor(Color.white);
            g2D.fillOval(station.xCoordinate() - radius, station.yCoordinate() - radius, radius*2, radius*2);
            g2D.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));

            g2D.setColor(Color.BLACK);
            g2D.drawOval(station.xCoordinate() - radius, station.yCoordinate() - radius, radius*2, radius*2);
            g2D.setColor(Color.white);
            String[] words = station.name().split(" ");

            if(words.length <= 2){ //helps with longer names
                g2D.drawString(station.name(), station.xCoordinate() + 15, station.yCoordinate() + 4);
            }else if(words.length > 2){
                for (int y = 1; y < words.length + 1; y++ ){
                    g2D.drawString(words[y-1], station.xCoordinate() + 15, station.yCoordinate() + (12 * y));
                }
            }



        }
        drawCurrentTrains(g2D);
        }
    }
    private void drawCurrentTrains(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        for (Train train : trains) {
            TrainLine trainLine = TrainLayout.lines.getTrainLineLookUp(train.routeName);

            int trainY;
            if (trainLine != null) {

                int nextTrainIndex = trainLine.indexOf(train.nextStationId);
                if (nextTrainIndex != -1) {
                    Station nextStation = trainLine.getPlottedStations().get(nextTrainIndex);
                    g2d.setColor(trainLine.getColor());
                    int trainX = nextStation.xCoordinate();
                    if (nextTrainIndex == 0 || nextTrainIndex == trainLine.getStations().length) {
                        trainY = nextStation.yCoordinate();
                    } else if (train.direction == 5) {
                        trainY = nextStation.yCoordinate() - (((canvasHeight - 80) / trainLine.getPlottedStations().size()) / 2); //half of the offset in train layout
                    } else {
                        trainY = nextStation.yCoordinate() - (((canvasHeight - 80) / trainLine.getPlottedStations().size()) / 2);
                    }
                    int width = 30;
                    int height = 30;
                    int[] xIntsUp = {trainX, trainX - width / 2, trainX + width / 2};
                    int[] yIntsUp = {trainY - height / 2, trainY + height / 2, trainY + height / 2};
                    int[] xIntsDown = {trainX, trainX + width / 2, trainX - width / 2};
                    int[] yIntsDown = {trainY + height / 2, trainY - height / 2, trainY - height / 2};
                    if (train.direction == 1) {
                        g2d.fillPolygon(xIntsUp, yIntsUp, 3);
                        g2d.setColor(Color.BLACK);
                        g2d.drawPolygon(xIntsUp, yIntsUp, 3);
                    } else {
                        g2d.fillPolygon(xIntsDown, yIntsDown, 3);
                        g2d.setColor(Color.BLACK);
                        g2d.drawPolygon(xIntsDown, yIntsDown, 3);
                    }
                }
            }
        }
    }

}
