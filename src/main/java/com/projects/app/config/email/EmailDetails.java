package com.projects.app.config.email;

import lombok.Data;

@Data
public class EmailDetails {
    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
