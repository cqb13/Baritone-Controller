package dev.cqb13.BaritoneController.gui.utils;

import meteordevelopment.meteorclient.gui.widgets.WWidget;

public final class WidgetSizing {
    private WidgetSizing() {
    }

    public static double maxWidgetWidth(WWidget... widgets) {
        double max = 0;

        for (WWidget widget : widgets) {
            widget.calculateSize();
            if (widget != null && widget.width > max) {
                max = widget.width;
            }
        }

        return max;
    }
}
