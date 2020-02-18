package com.etcd.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.etcd.data.entity.Node;
import com.etcd.model.NodeRequest;
import com.etcd.service.NodeService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.swagger.annotations.ApiOperation;


@RestController
public class NodeController {
  private NodeService nodeService;
  
  public NodeController(NodeService nodeService) {
    this.nodeService = nodeService;
  }
  
  @ApiOperation(value = "Creates a new node.",
      response = Node.class)
  @RequestMapping(method = RequestMethod.POST, value = "/nodes")
  public Node create(@RequestBody NodeRequest request) {
    return nodeService.create(request);
  }
  
  @ApiOperation(value = "Get a node.",
      response = Node.class)
  @RequestMapping(method = RequestMethod.GET, value = "/nodes/{id}", 
      produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public Node get(@PathVariable("id") UUID id) {
    return nodeService.get(id);
  }
  
  @ApiOperation(value = "Retrieves all nodes.", response = Node.class,
      responseContainer = "List")
  @RequestMapping(method = RequestMethod.GET, produces = {
          MediaType.APPLICATION_JSON_VALUE}, value = "/nodes")
  public List<Node> readAll() {
      return nodeService.getAll();
  }
  
  @ApiOperation(value = "Deletes a node.")
  @RequestMapping(method = RequestMethod.DELETE, value = "/nodes/{id}")
  public void delete(@PathVariable("id") UUID id) {
      nodeService.delete(id);
  }

  @ApiOperation(value = "Deletes all nodes.")
  @RequestMapping(method = RequestMethod.DELETE, value = "/nodes")
  public void deleteAll() {
      nodeService.deleteAll();
  }

  @ApiOperation(value = "Retrieves all keys.", responseContainer = "Map")
  @RequestMapping(method = RequestMethod.GET, produces = {
          MediaType.APPLICATION_JSON_VALUE}, value = "/keys")
  public Map<String, String> readAllKeys() {
      return nodeService.getAllKeys();
  }
}
