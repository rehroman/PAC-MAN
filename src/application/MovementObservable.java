package application;

public interface MovementObservable {
    /**
     * Registers a MovementObserver.
     * @param o The MovementObserver to register.
     */
    void register(MovementObserver o);
    void unregister(MovementObserver o);
    void notifyMovementObservers();
}
