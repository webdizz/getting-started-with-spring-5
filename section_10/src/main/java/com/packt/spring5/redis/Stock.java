package com.packt.spring5.redis;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("stocks")
public class Stock implements Serializable {

    @Id
    private String id;
    private String name;
    private Double price;
}
