package com.etcd;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.kv.GetResponse;

public class EtcdTest {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    EtcdTest test = new EtcdTest();
    Map<ByteSequence, ByteSequence> map = test.findAllKeys();
    for (ByteSequence key : map.keySet()) {
      String keyString =  new String(key.getBytes());
      String valString = new String(map.get(key).getBytes());
      System.out.println(keyString + " : " + valString);
    }
      
    

  }
  
  public void jetcdConn() {
    Client client = 
        Client.builder()
        .endpoints("http://172.27.14.60:2379",
                   "http://172.27.14.61:2379",
                   "http://172.27.14.62:2379").build();
    KV kvClient = client.getKVClient();
    
    ByteSequence key = ByteSequence.from("test_key".getBytes());
    ByteSequence value = ByteSequence.from("test_value2".getBytes());
    
    //try {
      //PutResponse res = kvClient.put(key, value).get();
    //} catch (InterruptedException | ExecutionException e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
    //}
    CompletableFuture<GetResponse> getFuture = kvClient.get(ByteSequence.from("/message".getBytes()));

    // get the value from CompletableFuture
    try {
      GetResponse response = getFuture.get();
      System.out.println(response.toString());
      System.out.println(response.getKvs());
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  public Map<ByteSequence, ByteSequence> findAllKeys() {
    Client client = 
        Client.builder()
        .endpoints("http://172.27.14.60:2379",
                   "http://172.27.14.61:2379",
                   "http://172.27.14.62:2379").build();
    try {
        ByteSequence key = ByteSequence.from("\0".getBytes());
        GetOption option = GetOption.newBuilder()
                .withSortField(GetOption.SortTarget.KEY)
                .withSortOrder(GetOption.SortOrder.DESCEND)
                .withRange(key)
                .build();

        CompletableFuture<GetResponse> futureResponse =
                client.getKVClient().get(key, option);

        GetResponse response = futureResponse.get();
        System.out.println(response.toString());
        if (response.getKvs().isEmpty()) {

            return null;
        }


        Map<ByteSequence, ByteSequence> keyValueMap = new HashMap<>();
        for (KeyValue kv : response.getKvs()) {
            keyValueMap.put(kv.getKey(),
                    kv.getValue());
        }



        return keyValueMap;

    } catch (Exception e) {
        // do nothing
      System.out.println(e.toString());
    }
    return null;
}

}
