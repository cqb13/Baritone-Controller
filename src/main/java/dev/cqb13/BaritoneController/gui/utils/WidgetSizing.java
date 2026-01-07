
package dev.cqb13.BaritoneController.gui.utils;

import meteordevelopment.meteorclient.gui.utils.Cell;
import meteordevelopment.meteorclient.gui.widgets.WWidget;

public final class WidgetSizing {
    private WidgetSizing() {
    }

    public static <T extends WWidget> void forceCellWidth(Cell<T> cell, double width) {
        cell.width = width;
        T widget = cell.widget();
        widget.width = width;
        widget.minWidth = width;
    }

    @SafeVarargs
    public static <T extends WWidget> void forceCellWidth(double width, Cell<T>... cells) {
        for (Cell<T> cell : cells) {
            forceCellWidth(cell, width);
        }
    }
}
