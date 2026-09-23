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
import java.util.ArrayList;
import java.util.List;


public class Client {
    
    private static final String BASE_URL= "http://lapi.transitchicago.com/api/1.0/ttpositions.aspx";

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
        String url = BASE_URL + "?key=" + apiKeyString + rtParams;

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


}
