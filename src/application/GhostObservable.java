package application;

/**
 * Defines methods for observing a ghost's movement.
 */
public interface GhostObservable {
    /**
     * Registers an observer.
     * @param o The Observer to register.
     */
    void register(GhostObserver o);

    /**
     * Unregisters an observer.
     * @param o The Observer to unregister.
     */
    void unregister(GhostObserver o);

    /**
     * Notifies observers about a ghost position change.
     */
    void notifyObservers();
}
