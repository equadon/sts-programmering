public interface ObjectStreamListener {
    void objectReceived(Object object);
    void exceptionReceieved(Exception exception);
}
