import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Lines {
    public ArrayList<TrainLine> trainLines = new ArrayList<>();
    private static Map<String, TrainStation[]> LINESANDSTATIONS = new LinkedHashMap<>();


    public Lines(){
        LINESANDSTATIONS.put("Red", new TrainStation[]{new TrainStation("Howard", 40900),
                new TrainStation("Jarvis", 41190),
                new TrainStation("Morse", 40100),
                new TrainStation("Loyola", 41300),
                new TrainStation("Granville", 40760),
                new TrainStation("Thorndale", 40880),
                new TrainStation("Bryn Mawr", 41380),
                new TrainStation("Berwyn", 40340),
                new TrainStation("Argyle", 41200),
                new TrainStation("Lawrence", 40770),
                new TrainStation("Wilson", 40540),
                new TrainStation("Sheridan", 40080),
                new TrainStation("Addison", 41420),
                new TrainStation("Belmont", 41320),
                new TrainStation("Fullerton", 41220),
                new TrainStation("North/Clybourn", 40650),
                new TrainStation("Clark/Division", 40630),
                new TrainStation("Chicago", 41450),
                new TrainStation("Grand", 40330),
                new TrainStation("Lake", 41660),
                new TrainStation("Monroe", 41090),
                new TrainStation("Jackson", 40560),
                new TrainStation("Harrison", 41490),
                new TrainStation("Roosevelt", 41400),
                new TrainStation("Cermak-Chinatown", 41000),
                new TrainStation("Sox-35th", 40190),
                new TrainStation("47th", 41230),
                new TrainStation("Garfield", 41170),
                new TrainStation("63rd", 40910),
                new TrainStation("69th", 40990),
                new TrainStation("79th", 40240),
                new TrainStation("87th", 41430),
                new TrainStation("95th/Dan Ryan", 40450)}
        );
        LINESANDSTATIONS.put( "Blue", new TrainStation[]
                {new TrainStation("O'Hare", 40890),
                        new TrainStation("Rosemont", 40820),
                        new TrainStation("Cumberland", 40230),
                        new TrainStation("Harlem", 40750),
                        new TrainStation("Jefferson Park", 41280),
                        new TrainStation("Montrose", 41330),
                        new TrainStation("Irving Park", 40550),
                        new TrainStation("Addison", 41240),
                        new TrainStation("Belmont", 40060),
                        new TrainStation("Logan Square", 41020),
                        new TrainStation("California", 40570),
                        new TrainStation("Western", 40670),
                        new TrainStation("Damen", 40590),
                        new TrainStation("Division", 40320),
                        new TrainStation("Chicago", 41410),
                        new TrainStation("Grand", 40490),
                        new TrainStation("Clark/Lake", 40380),
                        new TrainStation("Washington", 40370),
                        new TrainStation("Monroe", 40790),
                        new TrainStation("Jackson", 40070),
                        new TrainStation("LaSalle", 41340),
                        new TrainStation("Clinton", 40430),
                        new TrainStation("UIC-Halsted", 40350),
                        new TrainStation("Racine", 40470),
                        new TrainStation("Illinois Medical District", 40810),
                        new TrainStation("Western", 40220),
                        new TrainStation("Kedzie-Homan", 40250),
                        new TrainStation("Pulaski", 40920),
                        new TrainStation("Cicero", 40970),
                        new TrainStation("Austin", 40010),
                        new TrainStation("Oak Park", 40180),
                        new TrainStation("Harlem", 40980),
                        new TrainStation("Forest Park", 40390)});
        LINESANDSTATIONS.put("Brown", new TrainStation[]
                {new TrainStation("Kimball", 41290),
                        new TrainStation("Kedzie", 41180),
                        new TrainStation("Francisco", 40870),
                        new TrainStation("Rockwell", 41010),
                        new TrainStation("Western", 41480),
                        new TrainStation("Damen", 40090),
                        new TrainStation("Montrose", 41500),
                        new TrainStation("Irving Park", 41460),
                        new TrainStation("Addison", 41440),
                        new TrainStation("Paulina", 41310),
                        new TrainStation("Southport", 40360),
                        new TrainStation("Belmont", 41320),
                        new TrainStation("Wellington", 41210),
                        new TrainStation("Diversey", 40530),
                        new TrainStation("Fullerton", 41220),
                        new TrainStation("Armitage", 40660),
                        new TrainStation("Sedgwick", 40800),
                        new TrainStation("Chicago", 40710),
                        new TrainStation("Merchandise Mart", 40460),
                        new TrainStation("Washington/Wells", 40730),
                        new TrainStation("Quincy", 40040),
                        new TrainStation("LaSalle/Van Buren", 40160),
                        new TrainStation("Washington/Wabash", 41700)});

        LINESANDSTATIONS.put("Green", new TrainStation[]
                {new TrainStation("Harlem/Lake", 40020),
                        new TrainStation("Oak Park", 41350),
                        new TrainStation("Ridgeland", 40610),
                        new TrainStation("Austin", 41260),
                        new TrainStation("Central", 40280),
                        new TrainStation("Laramie", 40700),
                        new TrainStation("Cicero", 40480),
                        new TrainStation("Pulaski", 40030),
                        new TrainStation("Conservatory-Central Park Drive", 41670),
                        new TrainStation("Kedzie", 41070),
                        new TrainStation("California", 41360),
                        new TrainStation("Ashland", 40170),
                        new TrainStation("Morgan", 41510),
                        new TrainStation("Clinton", 41160),
                        new TrainStation("Roosevelt", 41400),
                        new TrainStation("35th-Bronzeville-IIT", 41120),
                        new TrainStation("Indiana", 40300),
                        new TrainStation("43rd", 41270),
                        new TrainStation("47th", 41080),
                        new TrainStation("51st", 40130),
                        new TrainStation("Garfield", 40510),
                        new TrainStation("King Drive", 41140),
                        new TrainStation("Cottage Grove", 40720)});


        LINESANDSTATIONS.put("Orange", new TrainStation[]
                {new TrainStation("Midway", 40930),
                        new TrainStation("Pulaski", 40960),
                        new TrainStation("Kedzie", 41150),
                        new TrainStation("Western", 40310),
                        new TrainStation("35th/Archer", 40120),
                        new TrainStation("Ashland", 41060),
                        new TrainStation("Halsted", 41130),
                        new TrainStation("Roosevelt", 41400),
                        new TrainStation("Harold Washington Library", 40850),
                        new TrainStation("LaSalle/Van Buren", 40160),
                        new TrainStation("Quincy", 40040),
                        new TrainStation("Washington/Wells", 40730),
                        new TrainStation("Washington/Wabash", 41700),
                        new TrainStation("Clark/Lake", 40380)});

        LINESANDSTATIONS.put("Pink", new TrainStation[]
                {new TrainStation("54th/Cermak", 40580),
                        new TrainStation("Cicero", 40420),
                        new TrainStation("Kostner", 40600),
                        new TrainStation("Pulaski", 40150),
                        new TrainStation("Central Park", 40780),
                        new TrainStation("Kedzie", 41040),
                        new TrainStation("California", 40440),
                        new TrainStation("Western", 40740),
                        new TrainStation("Damen", 40210),
                        new TrainStation("18th", 40830),
                        new TrainStation("Polk", 41030),
                        new TrainStation("Ashland", 40170),
                        new TrainStation("Morgan", 41510),
                        new TrainStation("Clinton", 41160),
                        new TrainStation("Washington/Wells", 40730),
                        new TrainStation("Quincy", 40040),
                        new TrainStation("Washington/Wabash", 41700)});


        LINESANDSTATIONS.put("Purple", new TrainStation[]
                {new TrainStation("Linden", 41050),
                        new TrainStation("Central", 41250),
                        new TrainStation("Noyes", 40400),
                        new TrainStation("Foster", 40520),
                        new TrainStation("Davis", 40050),
                        new TrainStation("Dempster", 40690),
                        new TrainStation("Main", 40270),
                        new TrainStation("South Boulevard", 40840),
                        new TrainStation("Howard", 40900),
                        new TrainStation("Belmont", 41320),
                        new TrainStation("Wellington", 41210),
                        new TrainStation("Diversey", 40530),
                        new TrainStation("Fullerton", 41220),
                        new TrainStation("Armitage", 40660),
                        new TrainStation("Sedgwick", 40800),
                        new TrainStation("Chicago", 40710),
                        new TrainStation("Merchandise Mart", 40460),
                        new TrainStation("Washington/Wells", 40730),
                        new TrainStation("Quincy", 40040),
                        new TrainStation("Washington/Wabash", 41700)});
        LINESANDSTATIONS.put("Yellow", new TrainStation[]
                {new TrainStation("Skokie", 40140),
                        new TrainStation("Oakton-Skokie", 41680),
                        new TrainStation("Howard", 40900)});
        LINESANDSTATIONS.forEach((lineName, stations) -> trainLines.add(new TrainLine(lineName, stations)));


        }

    }




