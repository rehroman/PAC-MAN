package application;

import javafx.geometry.Point2D;

public interface GhostObserver {
	public void update (Point2D ghostLocation);
}