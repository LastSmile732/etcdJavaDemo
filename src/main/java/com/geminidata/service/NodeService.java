package com.geminidata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geminidata.data.entity.Node;
import com.geminidata.data.repository.NodeRepository;
import com.geminidata.model.NodeRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class NodeService {
  
  
  private final NodeRepository nodeRepository;
  
  @Autowired
  public NodeService(NodeRepository nodeRepository) {
    this.nodeRepository = nodeRepository;
  }
  
  public Node create(NodeRequest request) {
    Node node = new Node();
    node.setIp(request.getIp());
    node.setPlatform(request.getPlatform());
    node.setHostname(request.getHostname());
    node.setEnvironmentID(request.getEnvironmentID());
    
    return nodeRepository.save(node);
    
  }
  
  public Node get(UUID id) {
    Node node = nodeRepository.findOne(id);
    
    if(node == null) {
      System.out.println("Node not found: " + id.toString());
  
    }
    return node;
  }
  
  public List<Node> getAll(){
    List<Node> nodes = nodeRepository.findAll();
    if(nodes == null || nodes.isEmpty()) {
      System.out.println("Nodes not found!");
  
    }
    return nodes;
  }
  
  public Map<String, String> getAllKeys(){
    Map<String, String> mapKeysMap = new HashMap<>();
    mapKeysMap = nodeRepository.findAllKeys();
    if(mapKeysMap.isEmpty()) {
      System.out.println("Node keys not found!");
  
    }
    return mapKeysMap;
  }
  
  public void delete(UUID id) {
    nodeRepository.delete(id);
  }
  
  public void deleteAll() {
    nodeRepository.deleteAll();
  }
}
