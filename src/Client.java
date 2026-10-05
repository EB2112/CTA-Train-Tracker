import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Client {
    
    private static final String LOCATIONS_URL= "http://lapi.transitchicago.com/api/1.0/ttpositions.aspx";
    private static final String ARRIVALS_URL = "http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx";
    private final String apiKeyString;
    private final HttpClient httpClient;

    public Client(String apiKeyString){
        this.apiKeyString = apiKeyString;
        this.httpClient = HttpClient.newHttpClient();
    }



    public List<Train> getTrainsOnRoutes(String... routeCodes) throws Exception {
        //we are using the locations api from the cta developers site https://www.transitchicago.com/developers/ttdocs/
        StringBuilder rtParams = new StringBuilder();
        for (String r : routeCodes){
            rtParams.append("&rt=").append(r);
        }
        String url = LOCATIONS_URL + "?key=" + apiKeyString + rtParams;

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

        HttpResponse<String> response = 
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200){
            throw new RuntimeException("CTA API returned HTTP " + response.statusCode());
        }
        return parseTrains(response.body());

    }
    public List<Arrival> getStationArrivals(int stationID) throws Exception {
        String url = ARRIVALS_URL + "?key=" + apiKeyString + "&mapid=" + stationID + "&max=3";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("CTA API returned HTTP " + response.statusCode());
        }

        return (parseArrivals(response.body()));

    }
    private List<Train> parseTrains(String xml) throws Exception{

        List<Train> trains = new ArrayList<>();

        //api returns xml doc so we must parse through to find trains
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));

               NodeList errNodes = doc.getElementsByTagName("errCd");
        if (errNodes.getLength() > 0) {
            String errCd = errNodes.item(0).getTextContent();
            if (!"0".equals(errCd)) {
                NodeList errNm = doc.getElementsByTagName("errNm");
                String msg = errNm.getLength() > 0 ? errNm.item(0).getTextContent() : "unknown error";
                throw new RuntimeException("CTA API error " + errCd + ": " + msg);
            }
        }
 
        // Structure: <ctatt><route name="Red"><train>...</train>...</route>...</ctatt>
        NodeList routeNodes = doc.getElementsByTagName("route");
        for (int r = 0; r < routeNodes.getLength(); r++) {
            Element routeEl = (Element) routeNodes.item(r);
            String routeName = routeEl.getAttribute("name");
 
            NodeList trainNodes = routeEl.getElementsByTagName("train");
            for (int t = 0; t < trainNodes.getLength(); t++) {
                Element trainEl = (Element) trainNodes.item(t);
                trains.add(toTrain(trainEl, routeName));
            }
        }
 
        return trains;
 
    }
    private List<Arrival> parseArrivals(String xml) throws Exception{
        List<Arrival> arrivals = new ArrayList<>();
        System.out.println(xml);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));


        NodeList errNodes = doc.getElementsByTagName("errCd");
        if (errNodes.getLength() > 0) {
            String errCd = errNodes.item(0).getTextContent();
            if (!"0".equals(errCd)) {
                NodeList errNm = doc.getElementsByTagName("errNm");
                String msg = errNm.getLength() > 0 ? errNm.item(0).getTextContent() : "unknown error";
                throw new RuntimeException("CTA API error " + errCd + ": " + msg);
            }
        }

        NodeList arrivalNodes = doc.getElementsByTagName("eta");
        for (int r = 0; r < arrivalNodes.getLength(); r++) {
            Element arrival = (Element) arrivalNodes.item(r);

            String isApproaching = (arrival.getElementsByTagName("isApp").getLength() > 0) ? arrival.getElementsByTagName("isApp").item(0).getTextContent(): "" ;
            boolean isApproachingValue = "1".equals(isApproaching);
            String rtValue = text(arrival, "rt");
            String destinationValue = text(arrival, "stpDe");
            String arrivalTimeValue = text(arrival, "arrT");
            String arrivalTime = parseTime(arrivalTimeValue);
            System.out.println(String.format("%s line train %s arrivng at %s", rtValue, destinationValue, arrivalTime));
            arrivals.add(new Arrival(rtValue, destinationValue, arrivalTime, isApproachingValue));

        }

        return arrivals;
    }

        private Train toTrain(Element el, String routeName) {
        Train train = new Train();
        train.routeName = routeName;
        train.runNumber = text(el, "rn");
        train.destName = text(el, "destNm");
        String nextStationId = text(el, "nextStaId");
        train.nextStationId = nextStationId.isEmpty() ? 0 : Integer.parseInt(nextStationId);
        train.nextStopName = text(el, "nextStaNm");
        train.arrivalTime = text(el, "arrT");
        train.isApproaching = "1".equals(text(el, "isApp"));
        train.isDelayed = "1".equals(text(el, "isDly"));
        String direction = text(el, "trDr");
        train.direction = direction.isEmpty() ? 1 : Integer.parseInt(direction);
 
        String lat = text(el, "lat");
        String lon = text(el, "lon");
        String heading = text(el, "heading");
        train.lat = lat.isEmpty() ? 0.0 : Double.parseDouble(lat);
        train.lon = lon.isEmpty() ? 0.0 : Double.parseDouble(lon);
        train.heading = heading.isEmpty() ? 0.0 : Double.parseDouble(heading);
 
        return train;
    }
private String text(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        return nodes.getLength() > 0 ? nodes.item(0).getTextContent() : "";
    }
    private String parseTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime parsedTime = LocalTime.parse(time.substring(9), formatter) ;
        DateTimeFormatter display = DateTimeFormatter.ofPattern("h:mm a");
        String displayTime = parsedTime.format(display) ;
        return displayTime;
    }


}
