package com.allknu.backend.web;

import com.allknu.backend.web.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @GetMapping("/dev/health-check")
    @ResponseBody
    public ResponseEntity<CommonResponse> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/dev/policy")
    public String policy() {
        return "policy";
    }
}
