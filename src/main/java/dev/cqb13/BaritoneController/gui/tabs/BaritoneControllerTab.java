package dev.cqb13.BaritoneController.gui.tabs;

import dev.cqb13.BaritoneController.gui.utils.WidgetSizing;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.tabs.Tab;
import meteordevelopment.meteorclient.gui.tabs.TabScreen;
import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import net.minecraft.client.gui.screen.Screen;

public class BaritoneControllerTab extends Tab {
    public BaritoneControllerTab() {
        super("Baritone Controller");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new AddonsTabScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof AddonsTabScreen;
    }

    private static class AddonsTabScreen extends WindowTabScreen {
        public AddonsTabScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            add(theme.label("Baritone Controller", true)).expandX().centerX();
            add(theme.horizontalSeparator()).expandX();

            WVerticalList list = theme.verticalList();

            list.add(commonControls());

            add(list);
        }

        private WHorizontalList commonControls() {
            WHorizontalList list = theme.horizontalList();

            WButton stop = theme.button("Stop");
            WButton pause = theme.button("Pause");
            WButton resume = theme.button("Resume");

            double maxWidth = maxButtonWidth(stop, pause, resume);

            WidgetSizing.forceCellWidth(maxWidth + 5, list.add(stop), list.add(pause), list.add(resume));

            return list;
        }

        private double maxButtonWidth(WButton... buttons) {
            double max = 0;

            for (WButton button : buttons) {
                button.calculateSize();
                if (button != null && button.width > max) {
                    max = button.width;
                }
            }

            return max;
        }

        @Override
        public boolean shouldCloseOnEsc() {
            return true;
        }
    }
}
