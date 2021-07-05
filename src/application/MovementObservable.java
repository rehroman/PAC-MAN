package application;

public interface MovementObservable {
    void register(MovementObserver o);
    void unregister(MovementObserver o);
    void notifyMovementObservers();
}
