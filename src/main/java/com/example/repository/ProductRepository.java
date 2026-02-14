package com.example.repository;

import com.example.entity.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

import java.util.List;
import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private DynamoDbTable<Product> productTable;

    @PostConstruct
    public void init(){
        productTable = dynamoDbEnhancedClient.table("Product", TableSchema.fromBean(Product.class));
    }

    public Product save(Product product){
        productTable.putItem(product);
        return product;
    }

    public Product findById(String id){
//        return productTable.getItem(r -> r.key(key -> key.partitionValue(id)));
        return productTable.getItem(new Consumer<GetItemEnhancedRequest.Builder>() {
            @Override
            public void accept(GetItemEnhancedRequest.Builder builder) {
                builder.key(new Consumer<Key.Builder>() {
                    @Override
                    public void accept(Key.Builder builder) {
                        builder.partitionValue(id);
                    }
                });
            }
        });
    }

    public List<Product> findAll(){
        return productTable.scan().items().stream().toList();
    }

    public void deleteById(String id){
        productTable.deleteItem(new Consumer<DeleteItemEnhancedRequest.Builder>() {
            @Override
            public void accept(DeleteItemEnhancedRequest.Builder builder) {
                builder.key(new Consumer<Key.Builder>() {
                    @Override
                    public void accept(Key.Builder builder) {
                        builder.partitionValue(id);
                    }
                });
            }
        });
    }
}
