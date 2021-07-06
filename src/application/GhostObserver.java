package application;

import javafx.geometry.Point2D;

/**
 * Observes the ghosts' movement.
 */
public interface GhostObserver {
    /**
     * Updates observers about a ghost's position.
     * @param ghostID ID of the ghost to update.
     * @param ghostLocation New location for this ghost.
     */
    void update(int ghostID, Point2D ghostLocation);
}
