package com.jadevirek.datacontrol.controler;


import com.jadevirek.datacontrol.data.providers.CustomerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CustomerController {

    private final CustomerProvider customerProvider;

    @Autowired
    public CustomerController(CustomerProvider customerProvider) {
        this.customerProvider = customerProvider;
    }

    @GetMapping(value = "/customers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getCustomers(){
        return customerProvider.generate();
    }

}
