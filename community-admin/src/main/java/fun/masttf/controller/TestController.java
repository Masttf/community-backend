package fun.masttf.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class TestController {
    @RequestMapping(value = "/test", method=RequestMethod.GET)
    public String test(){
        return "test admin";
    }
}
