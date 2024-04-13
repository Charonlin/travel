package com.scnu.travel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
public class Result {
    private boolean flag;
    private String message;
    private Object data;

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
}
