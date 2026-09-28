# CTA Train Tracker

A Java Swing desktop app that pulls live train data from the Chicago Transit Authority's Train Tracker API and renders it as a schematic system map — every 'L' line drawn as its own column of stations, with live train markers that move as trains approach their next stop.

![picture](https://github.com/EB2112/CTA-Train-Tracker/blob/main/assets/cta%20trains.png) 
## What it does

- Polls the CTA Train Tracker `ttpositions.aspx` (Locations) endpoint every 10–15 seconds for all 8 rail lines at once
- Parses the XML response into plain Java objects using JDK's built-in `javax.xml` DOM parser
- Draws each line as a distinct, correctly-colored column on a scrollable canvas, with stations labeled and spaced along it
- Places a directional marker (triangle) for every train in service, positioned between its current and next station and pointing the way it's traveling
- Distinguishes delayed/approaching trains visually

## Getting started

1. Apply for a free API key at [transitchicago.com/developers](https://www.transitchicago.com/developers/)
2. Compile:
   ```
   javac src/*.java -d out
   ```
3. Run:
   ```
   java -cp out Main YOUR_API_KEY
   ```

## Project structure

| File | Responsibility |
|---|---|
| `Client.java` | HTTP + XML wrapper around the CTA Locations API |
| `Train.java` | Data model for a single train (run number, route, next station, ETA, delay/approach flags) |
| `Station.java` | A station's name, GTFS ID, and its plotted (x, y) screen position |
| `TrainStation.java` | Raw name/ID pair for a station, before layout coordinates are computed |
| `TrainLine.java` | One rail line: its name, color, raw stations, and computed `plottedStations` |
| `Lines.java` | Builds all 8 `TrainLine` objects from hardcoded name/ID data, plus a name → `TrainLine` lookup map |
| `TrainLayout.java` | Computes screen coordinates for every line's stations (spacing, column position) |
| `TrainMapPanel.java` | The Swing `JPanel` — draws lines, stations, and live train markers |
| `Main.java` | Entry point: builds the window, starts the polling loop |

