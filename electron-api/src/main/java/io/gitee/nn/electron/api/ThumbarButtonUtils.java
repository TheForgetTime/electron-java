package io.gitee.nn.electron.api;

import io.gitee.nn.electron.api.entities.ThumbarButton;

import java.util.List;
import java.util.UUID;

final class ThumbarButtonUtils {
    static ThumbarButton[] AddThumbarButtonsId(ThumbarButton[] thumbarButtons) {
        for (ThumbarButton thumbarButton : thumbarButtons) {
            if (thumbarButton == null)
                continue;
            if (thumbarButton.getId() == null || thumbarButton.getId().isEmpty()) {
                thumbarButton.setId(UUID.randomUUID().toString());
            }
        }

        return thumbarButtons;
    }

    static ThumbarButton GetThumbarButton(List<ThumbarButton> thumbarButtons, String id) {
        ThumbarButton result = new ThumbarButton("");

        for (var item : thumbarButtons) {
            if (id.equals(item.getId())) {
                result = item;
            }
        }

        return result;
    }
}
