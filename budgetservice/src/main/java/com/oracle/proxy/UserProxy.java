package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-services")
public interface UserProxy {
   @GetMapping("/users/balance/{uid}")
	float getBalanceById(@PathVariable("uid") Long id);
}
