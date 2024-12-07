package accountServer;

public interface ResourceServer {
    String getName();

    int getAge();

    void readResource(String path);
}
