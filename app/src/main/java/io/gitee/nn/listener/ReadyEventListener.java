package io.gitee.nn.listener;

import io.gitee.nn.electron.api.Electron;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        Electron.getWindowManager()
                .CreateWindowAsync(options -> {
                    options.setWidth(1280);
                    options.setHeight(720);
                    options.setShow(false);
                    options.getWebPreferences().setNodeIntegration(true);
                }).thenAccept(browserWindow -> browserWindow.AddOnReadToShow(browserWindow::Show));
    }
}
