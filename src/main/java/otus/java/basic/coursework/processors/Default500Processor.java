package otus.java.basic.coursework.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Default500Processor implements RequestProcessor{
    private static final Logger LOGGER = LogManager.getLogger(Default500Processor.class);
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        ErrorTemplate errorTemplate = new ErrorTemplate();
        String response = "" +
                "HTTP/1.1 500 Internal Server Error\r\n" +
                "Connect-Type: text/html\r\n" +
                "\r\n" +
                errorTemplate.generateErrorResp(500, "Internal Server Error: something wrong...", "");
        output.write(response.getBytes(StandardCharsets.UTF_8));
        LOGGER.error("Internal Server Error: something wrong...");
    }
}
