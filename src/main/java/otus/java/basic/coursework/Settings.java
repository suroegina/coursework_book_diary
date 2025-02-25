package otus.java.basic.coursework;

import java.io.*;
import java.util.Properties;


public class Settings {
    private int port;
    private int threadNumbers;
    private int maxReqSize;

    public Settings() {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("src/main/resources/data.properties")) {
            properties.load(in);
            this.port = Integer.parseInt(properties.getProperty("port","8189"));
            this.threadNumbers = Integer.parseInt(properties.getProperty("thread_size", "3"));
            this.maxReqSize = Integer.parseInt(properties.getProperty("max_size", "8192"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public int getThreadNumbers() {
        return threadNumbers;
    }

    public int getMaxReqSize() {
        return maxReqSize;
    }

}
