package com.geminidata.data.repository;

import org.springframework.stereotype.Repository;

import com.geminidata.data.entity.Node;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface NodeRepository {

  Node save(Node entity);
  
  Node findOne(UUID id);
  
  List<Node> findAll();
  
  Map<String, String> findAllKeys();
  
  void delete(UUID id);
  
  void deleteAll();
}
