package com.ubuybr.ubuybrapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@Document("cart")
@AllArgsConstructor
public class Cart {
    @Id
    public String id;

    @NotEmpty
    public Map<String, Integer> products = new HashMap<>();
}
