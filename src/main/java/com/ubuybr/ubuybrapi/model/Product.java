package com.ubuybr.ubuybrapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("product")
public class Product {
    @Id
    private String id;

    @NotEmpty(message = "Product description is mandatory")
    private String description;

    @NotNull(message = "Product quantity is mandatory")
    private int quantity;
}
