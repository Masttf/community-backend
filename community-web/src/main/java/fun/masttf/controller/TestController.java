package fun.masttf.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TestController {
    @RequestMapping(value = "/test", method=RequestMethod.GET)
    public String test(){
        return "test";
    }
}
