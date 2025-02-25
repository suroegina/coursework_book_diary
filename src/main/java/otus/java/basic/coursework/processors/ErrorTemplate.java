package otus.java.basic.coursework.processors;

public class ErrorTemplate {
    public String generateErrorResp(int errorCode,String errorText, String errorAdd) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\"\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" +
                "<title>Document</title>" +
                "</head>\n" +
                "<body>\n" +
                "<h1>" + "ERROR " + errorCode + "</h1>\n" +
                "<h2>" + errorText + "</h2>\n" +
                "<h3>" + errorAdd + "</h3>\n" +
                "</body>\n" +
                "</html>";
    }
}
