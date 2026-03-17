package com.launchpad.dto.request;

import lombok.Data;

@Data
public class JobRequestDTO {

    private String title;
    private String description;
    private String skills;
    private String location;
    private Long recruiterId;  // for now we pass manually
}
