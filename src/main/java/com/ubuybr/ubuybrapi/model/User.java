package com.ubuybr.ubuybrapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("user")
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String telephone;
}
