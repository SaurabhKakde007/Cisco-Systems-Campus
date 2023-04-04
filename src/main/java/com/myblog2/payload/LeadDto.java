package com.myblog2.payload;

import lombok.Data;

@Data
public class LeadDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long mobile;
}
