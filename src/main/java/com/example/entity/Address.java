package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String country;

    private String city;

    private Integer pincode;

}
