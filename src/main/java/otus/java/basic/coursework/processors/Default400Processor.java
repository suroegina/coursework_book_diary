package otus.java.basic.coursework.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.BadRequestException;
import otus.java.basic.coursework.HttpRequest;
import otus.java.basic.coursework.application.ErrorDto;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Default400Processor implements RequestProcessor {
    private static final Logger LOGGER = LogManager.getLogger(Default400Processor.class);
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        ErrorDto errorDto = new ErrorDto(
                ((BadRequestException)request.getErrorCause()).getCode(),
                ((BadRequestException) request.getErrorCause()).getDescription()
        );
        Gson gson = new Gson();
        String jsonError = gson.toJson(errorDto);
        String response = "" +
                "HTTP/1.1 400 Bad Request\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                jsonError;
        output.write(response.getBytes(StandardCharsets.UTF_8));
        LOGGER.error(errorDto.getDescription());
    }
}
