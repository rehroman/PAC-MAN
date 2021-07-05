package application;

public interface GhostObservable {
	public void register(GhostObserver o);
	public void unregister(GhostObserver o);
	public void notifyObservers();
}