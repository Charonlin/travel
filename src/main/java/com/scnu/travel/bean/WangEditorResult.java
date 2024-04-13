package com.scnu.travel.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class WangEditorResult {
    private int errno;
    private String[] data;
}
