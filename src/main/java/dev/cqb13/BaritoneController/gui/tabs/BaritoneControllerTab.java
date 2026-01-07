package dev.cqb13.BaritoneController.gui.tabs;

import static meteordevelopment.meteorclient.MeteorClient.mc;

import dev.cqb13.BaritoneController.gui.utils.WidgetSizing;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.tabs.Tab;
import meteordevelopment.meteorclient.gui.tabs.TabScreen;
import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
import meteordevelopment.meteorclient.gui.widgets.WLabel;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
import meteordevelopment.meteorclient.gui.widgets.input.WBlockPosEdit;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
import meteordevelopment.meteorclient.pathing.BaritoneUtils;
import meteordevelopment.meteorclient.pathing.PathManagers;
import meteordevelopment.meteorclient.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.BlockPos;

public class BaritoneControllerTab extends Tab {
    public BaritoneControllerTab() {
        super("Baritone Controller");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new BaritoneControllerScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof BaritoneControllerScreen;
    }

    private static class BaritoneControllerScreen extends WindowTabScreen {
        public BaritoneControllerScreen(GuiTheme theme, Tab tab) {
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

            commonControls();
            gotoCmdSection();
        }

        private void gotoCmdSection() {
            WSection gotoCmdSection = theme.section("Go To", true);
            WBlockPosEdit gotoCoords = theme.blockPosEdit(new BlockPos(0, 0, 0));
            WButton gotoBtn = theme.button("Execute");
            WHorizontalList ignoreYContainer = theme.horizontalList();
            WCheckbox ignoreY = theme.checkbox(true);
            WLabel label = theme.label("Ignore Y");

            gotoBtn.action = () -> {
                PathManagers.get().moveTo(gotoCoords.get(), ignoreY.checked);
            };

            add(gotoCmdSection).expandX().widget();
            gotoCmdSection.add(gotoCoords).expandX().widget();
            gotoCmdSection.add(ignoreYContainer).expandX().widget();
            ignoreYContainer.add(ignoreY);
            ignoreYContainer.add(label);
            gotoCmdSection.add(gotoBtn).expandX().widget();
        }

        private void commonControls() {
            WHorizontalList list = theme.horizontalList();

            WButton stopBtn = theme.button("Stop");
            stopBtn.action = () -> {
                PathManagers.get().stop();
            };
            WButton pauseBtn = theme.button("Pause");
            pauseBtn.action = () -> {
                PathManagers.get().pause();
            };
            WButton resumeBtn = theme.button("Resume");
            resumeBtn.action = () -> {
                PathManagers.get().resume();
            };

            double maxWidth = WidgetSizing.maxWidgetWidth(stopBtn, pauseBtn, resumeBtn);

            add(list);
            list.add(stopBtn).minWidth(maxWidth + 5).widget();
            list.add(pauseBtn).minWidth(maxWidth + 5).widget();
            list.add(resumeBtn).minWidth(maxWidth + 5).widget();
        }

        @Override
        public boolean shouldCloseOnEsc() {
            return true;
        }
    }
}
