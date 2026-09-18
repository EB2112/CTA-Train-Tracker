import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Train {
    public String runNumber;      // e.g. "406" - unique per train, use as your GUI marker's ID
    public String routeName;      // e.g. "Red", "Blue"
    public String destName;       // human-readable destination, e.g. "95th/Dan Ryan"
    public int nextStationId;  // stpid of the next stop
    public String nextStopName;   // human-readable next stop name
    public String arrivalTime;    // predicted arrival timestamp at next stop, format: yyyyMMdd HH:mm:ss
    public boolean isApproaching; // true if train is "due" at next stop
    public boolean isDelayed;
    public double heading;        // compass heading in degrees, useful if you want to rotate a marker icon
    public double lat;
    public double lon;

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime parsedTime = LocalTime.parse(arrivalTime.substring(9), formatter) ;
        DateTimeFormatter display = DateTimeFormatter.ofPattern("h:mm a");
        String displayTime = parsedTime.format(display) ;

        return String.format(
            "Run %s (%s Line) | %s -> next: %s%s%s (%s) ",
            runNumber, routeName, destName, nextStopName,
            isApproaching ? " [DUE]"  : "",
            isDelayed ? " [DELAYED]" : "",
                displayTime
        );
    }
}