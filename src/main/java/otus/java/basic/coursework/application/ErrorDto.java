package otus.java.basic.coursework.application;

import java.time.LocalDateTime;

public class ErrorDto {
    private String code;
    private String description;
    private String timestamp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ErrorDto(String code, String description) {
        this.code = code;
        this.description = description;
        this.timestamp = LocalDateTime.now().toString();
    }
}
