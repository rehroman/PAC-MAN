package application;

/**
 * Defines methods for observing moves of game elements.
 */
public interface MovementObservable {
    /**
     * Registers a MovementObserver.
     * @param o The MovementObserver to register.
     */
    void register(MovementObserver o);

    /**
     * Unregisters an observer.
     * @param o The Observer to unregister.
     */
    void unregister(MovementObserver o);

    /**
     * Notifies observers about a game element's movement.
     */
    void notifyMovementObservers();
}
