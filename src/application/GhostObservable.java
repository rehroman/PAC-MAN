package application;

public interface GhostObservable {
    void register(GhostObserver o);
    void unregister(GhostObserver o);
    void notifyObservers();
}
