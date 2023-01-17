package com.projects.app.dto;

import com.projects.app.commons.constants.ProfilePictureTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserProfileDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    private String profilePicture;

    private ProfilePictureTypeEnum profilePictureType;
}
