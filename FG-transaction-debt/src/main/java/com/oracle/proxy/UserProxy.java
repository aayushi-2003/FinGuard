package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="user-services")
public interface UserProxy {
   @GetMapping("/users/balance/{uid}")
	float getBalanceById(@PathVariable("uid") Long id);
   
   @PutMapping("/users/{uid}/balance")
   void updateBalance(@PathVariable("uid") Long uid, @RequestBody float newBalance);

}
