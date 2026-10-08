import java.awt.*;

public record TrainMotion(int startX, int startY, int endX, int endY, long startTime) {
    static final long DURATION_MS =6000;

    Point positionAt(long now){
        double time = Math.min(1.0, (now - startTime) / (double) DURATION_MS);
        int x = (int) Math.round(startX + (endX - startX) * time);
        int y = (int) Math.round(startY + (endY - startY) * time);
        return new Point(x, y);
    }
}
