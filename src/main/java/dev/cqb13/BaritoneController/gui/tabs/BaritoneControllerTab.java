package dev.cqb13.BaritoneController.gui.tabs;

import static meteordevelopment.meteorclient.MeteorClient.mc;

import dev.cqb13.BaritoneController.gui.utils.WidgetSizing;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.tabs.Tab;
import meteordevelopment.meteorclient.gui.tabs.TabScreen;
import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.pathing.BaritoneUtils;
import meteordevelopment.meteorclient.pathing.PathManagers;
import meteordevelopment.meteorclient.utils.render.color.Color;
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

            if (!BaritoneUtils.IS_AVAILABLE) {
                add(theme.label("Bartione must be installed to use this addon.")).widget().color(Color.RED);
                return;
            }

            if (mc.world == null) {
                add(theme.label("Please join a world.")).widget().color(Color.ORANGE);
                return;
            }

            WVerticalList list = theme.verticalList();

            list.add(commonControls());

            add(list);
        }

        private WHorizontalList commonControls() {
            WHorizontalList list = theme.horizontalList();

            WButton stop = theme.button("Stop");
            stop.action = () -> {
                PathManagers.get().stop();
            };
            WButton pause = theme.button("Pause");
            pause.action = () -> {
                PathManagers.get().pause();
            };
            WButton resume = theme.button("Resume");
            resume.action = () -> {
                PathManagers.get().resume();
            };

            double maxWidth = WidgetSizing.maxWidgetWidth(stop, pause, resume);

            WidgetSizing.forceCellWidth(maxWidth + 5, list.add(stop), list.add(pause), list.add(resume));

            return list;
        }

        @Override
        public boolean shouldCloseOnEsc() {
            return true;
        }
    }
}
