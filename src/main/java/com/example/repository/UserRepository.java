package com.example.repository;

import com.example.entity.User;
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
public class UserRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private DynamoDbTable<User> userTable;

    @PostConstruct
    public void init(){
        userTable = dynamoDbEnhancedClient.table("User", TableSchema.fromBean(User.class));
    }

    public User save(User user){
        userTable.putItem(user);
        return user;
    }

    public User findById(String id){
//        return productTable.getItem(r -> r.key(key -> key.partitionValue(id)));
        return userTable.getItem(new Consumer<GetItemEnhancedRequest.Builder>() {
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

    public List<User> findAll(){
        return userTable.scan().items().stream().toList();
    }

    public void deleteById(String id){
        userTable.deleteItem(new Consumer<DeleteItemEnhancedRequest.Builder>() {
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
