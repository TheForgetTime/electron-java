package io.gitee.nn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/desktopcapturer")
public class DesktopCapturerController {
    @RequestMapping(value = {"", "/index"})
    public String Index() {
        return "DesktopCapturer/Index";
    }
}
