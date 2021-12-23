package io.gitee.nn.controller;

import io.gitee.nn.electron.api.Electron;
import io.gitee.nn.electron.api.HybridSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {
    @RequestMapping(value = {"", "/index"})
    public String Index(Model model, Locale locale) {
        if (HybridSupport.IsElectronActive()) {
            Electron.getPowerMonitor().AddOnLockScreen(() -> System.out.println("Screen Locked detected from Java"));

            Electron.getPowerMonitor().AddOnUnLockScreen(() -> System.out.println("Screen unlocked detected from Java "));

            Electron.getPowerMonitor().AddOnSuspend(() -> System.out.println("The system is going to sleep"));

            Electron.getPowerMonitor().AddOnResume(() -> System.out.println("The system is resuming"));

            Electron.getPowerMonitor().AddOnAC(() -> System.out.println("The system changes to AC power"));

            Electron.getPowerMonitor().AddOnBattery(() -> System.out.println("The system is about to change to battery power"));
        }

        return "Home/Index";
    }
}
