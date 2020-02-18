package com.etcd.data.repository;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etcd.data.entity.Node;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.DeleteOption;
import io.etcd.jetcd.options.GetOption;

@Component
public class NodeRepositoryImpl implements NodeRepository {

  private static final Charset UTF_8 = StandardCharsets.UTF_8;
  
  private static final String PREFIX = "Node";

  private Client client;
  
  private ObjectMapper mapper;
  
  @Autowired
  public NodeRepositoryImpl(Client client, ObjectMapper mapper) {
      this.client = client;
      this.mapper = mapper;
  }
  
  @Override
  public Node save(Node node) {
    // TODO Auto-generated method stub
    if (node == null) {
      throw new IllegalArgumentException("No node to save, it should not be null.");
    }
    node.setId(UUID.randomUUID());
    try {
      String key = this.getKey(node.getId());
      client.getKVClient().put(
          ByteSequence.from(key, UTF_8), 
          ByteSequence.from(mapper.writeValueAsString(node), UTF_8)).get();
      System.out.println("Node " + key + " added.");
    } catch (Exception e) { 
      System.out.println("Error: " + e.toString());
    }
    return node;
  }

  @Override
  public Node findOne(UUID id) {
    // TODO Auto-generated method stub
    if (id == null) {
      throw new IllegalArgumentException("No node to find, id should not be null.");
    }
    try {
      String key = this.getKey(id);
      CompletableFuture<GetResponse> futureResponse =
              client.getKVClient().get(
                      ByteSequence.from(key, UTF_8));

      GetResponse response = futureResponse.get();
      if (response.getKvs().isEmpty()) {
        System.out.println("Failed to retrieve Node with ID " + id);
        return null;
      }

      System.out.println("Retrieved Node with ID " + id);
      return mapper.readValue(
              response.getKvs().get(0).getValue().toString(UTF_8),
              Node.class);

      } catch (Exception e) {
        System.out.println("Failed to retrieve Node with ID " + id);
      }
    return null;
  }

  @Override
  public List<Node> findAll() {
    // TODO Auto-generated method stub
      try {
        ByteSequence key = ByteSequence.from(PREFIX, UTF_8);
        GetOption option = GetOption.newBuilder()
                .withSortField(GetOption.SortTarget.KEY)
                .withPrefix(key)
                .build();
  
        CompletableFuture<GetResponse> futureResponse =
                client.getKVClient().get(key, option);
  
        GetResponse response = futureResponse.get();
        if (response.getKvs().isEmpty()) {
            System.out.println("Failed to retrieve any Node.");
            return new ArrayList<>();
        }
  
  
        List<Node> nodes = new ArrayList<>();
        for (KeyValue kv : response.getKvs()) {
            nodes.add(mapper.readValue(kv.getValue().toString(UTF_8),
                    Node.class));
        }
        System.out.println("Retrieved " + response.getKvs().size() + " nodes.");
        
        return nodes;
  
    } catch (Exception e) {
      System.out.println("Failed to retrieve nodes: " + e.toString());
    }
    return new ArrayList<Node>();
  }

  @Override
  public Map<String, String> findAllKeys() {
    // TODO Auto-generated method stub
    try {
      ByteSequence key = ByteSequence.from("\0".getBytes());
      GetOption option = GetOption.newBuilder()
              .withSortField(GetOption.SortTarget.KEY)
              .withRange(key)
              .build();

      CompletableFuture<GetResponse> futureResponse =
              client.getKVClient().get(key, option);

      GetResponse response = futureResponse.get();
      if (response.getKvs().isEmpty()) {
        System.out.println("Cannot find key-value pairs");
          return new HashMap<String, String>();
      }

      Map<String, String> keyValueMap = new HashMap<>();
      for (KeyValue kv : response.getKvs()) {
          keyValueMap.put(kv.getKey().toString(UTF_8),
                  kv.getValue().toString(UTF_8));
      }
      
      return keyValueMap;
      
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }

    return null;
  }

  @Override
  public void delete(UUID id) {
    // TODO Auto-generated method stub
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    try {
      String key = this.getKey(id);
      DeleteResponse response = client.getKVClient().delete(ByteSequence.from(key, UTF_8)).get();
      if (response.getDeleted() == 1) {
        System.out.println("Deleted ID: " + id.toString());
      } else {
        System.out.println("Failed to delete ID: " + id.toString());
      }
    } catch (Exception e){
      System.out.println("Failed to delete ID: " + id.toString() + " Exception " + e.toString());
    }
  }

  @Override
  public void deleteAll() {
    // TODO Auto-generated method stub
    try {
      ByteSequence key = ByteSequence.from(PREFIX, UTF_8);
      DeleteOption deleteOpt = DeleteOption.newBuilder()
              .withPrefix(key).build();

      DeleteResponse response =
              client.getKVClient().delete(key, deleteOpt).get();

      System.out.println("Deleted " + response.getDeleted() + " number of nodes.");
    } catch (Exception e) {
      System.out.println("Exception " + e.toString());
    }
  }
  
  private String getKey(UUID id) {
    return PREFIX + id.toString();
  }

}
