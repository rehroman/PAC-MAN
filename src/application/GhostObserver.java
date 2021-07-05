package application;

import javafx.geometry.Point2D;

public interface GhostObserver {
    void update(int ghostID, Point2D ghostLocation);
}
