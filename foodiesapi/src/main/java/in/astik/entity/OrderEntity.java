package in.astik.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OrderEntity {
    private String foodId;
    private int quantity;
    private double price;
    private String category;
    private String imageUrl;
    private String description;
}
