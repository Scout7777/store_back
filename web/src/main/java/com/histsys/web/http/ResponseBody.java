package com.histsys.web.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.histsys.web.json.JsonUtils;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseBody<T> {
    private T data;
    private String message;
    private Integer status = 200; // 等效于 HTTP 状态码，此后 HTTP 的状态码一律 200 OK，或 401、403 业务拒绝
    private boolean success = true;

    public ResponseBody() {
    }

    public ResponseBody(Integer status, T data, String message) {
        this.setStatus(status);
        this.setData(data);
        this.setMessage(message);
    }

    public ResponseBody(T data, String message) {
        this.setData(data);
        this.setMessage(message);
    }

    public ResponseBody(String message) {
        this.setMessage(message);
    }

    public static <T> ResponseBody status(Integer status) {
        return new ResponseBody(status, null, null);
    }

    public static <T> ResponseBody ok(T data) {
        return status(200).body(data);
    }

    public static <T> ResponseBody fail(T data) {
        return status(400).body(data);
    }

    public ResponseBody body(T data) {
        this.setData(data);
        return this;
    }

    public ResponseBody message(String message) {
        this.setMessage(message);
        return this;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
    public ResponseEntity toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}
