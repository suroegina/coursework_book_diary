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

public class ExceptionProcessor  {
    private static final Logger LOGGER = LogManager.getLogger(ExceptionProcessor.class);

    public void start(Integer errCode,HttpRequest request, OutputStream output) throws IOException {
        Default400Processor default400Processor = new Default400Processor();
        Default404Processor default404Processor = new Default404Processor();
        Default405Processor default405Processor = new Default405Processor();
        Default500Processor default500Processor = new Default500Processor();

        if (errCode == 400) {
            default400Processor.execute(request, output);
        } else if (errCode == 404) {
            default404Processor.execute(request, output);
        } else if (errCode == 405) {
            default405Processor.execute(request, output);
        } else if (errCode == 500) {
            default500Processor.execute(request, output);
        } else {
            ErrorDto errorDto = new ErrorDto(
                    ((BadRequestException)request.getErrorCause()).getCode(),
                    ((BadRequestException) request.getErrorCause()).getDescription()
            );
            Gson gson = new Gson();
            String jsonError = gson.toJson(errorDto);
            String response = "" +
                    "HTTP/1.1 "+ errorDto.getCode() +" " + errorDto.getDescription() +"\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    jsonError;
            output.write(response.getBytes(StandardCharsets.UTF_8));
            LOGGER.error(errorDto.getDescription());
        }
    }
}
