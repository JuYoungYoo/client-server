package com.juyoung.client.clientserver.domain;

import lombok.*;

@Data
public class Boards {

    private long seq = 0; // bigint
    private String title;
    private String content;
    private String author;

}