package com.launchpad.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String skills;
    private String location;
    private String recruiterName;
}