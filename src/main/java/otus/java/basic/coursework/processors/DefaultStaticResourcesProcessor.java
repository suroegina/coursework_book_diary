package otus.java.basic.coursework.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.HttpRequest;
import otus.java.basic.coursework.application.Book;
import otus.java.basic.coursework.application.BookService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DefaultStaticResourcesProcessor implements RequestProcessor {
    private static final Logger LOGGER = LogManager.getLogger(GetBookProcessor.class);

    private BookService bookService;
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String filename = httpRequest.getUri().substring(1);
        LOGGER.debug("filename = " + filename);
        Path filePath = Paths.get("static/", filename);
        String fileType = filename.substring(filename.lastIndexOf(".") + 1);
        byte[] fileData = Files.readAllBytes(filePath);

        String contentDisposition = "";


        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + fileData.length + "\r\n" +
                "Cache-Control: no-cache, no-store, must-revalidate\r\n" +
                contentDisposition +
                "\r\n";
        output.write(response.getBytes());
        output.write(fileData);
    }
}
