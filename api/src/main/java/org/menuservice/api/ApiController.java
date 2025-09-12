package org.menuservice.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class ApiController {

    @GetMapping(value = "/")
    public String home() {
        return "login";
    }
}
