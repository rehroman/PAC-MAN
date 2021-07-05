package application;

import javafx.geometry.Point2D;

public interface GhostObserver {
	public void update (int ghostID, Point2D ghostLocation);
}