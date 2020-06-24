package com.ubuybr.ubuybrapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@Document("cart")
@AllArgsConstructor
public class Cart {
    @Id
    private String id;

    private User user;

    private Double value = 0.0;

    @NotEmpty
    private List<Product> products = new ArrayList<>();
}
