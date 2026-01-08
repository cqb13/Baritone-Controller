package dev.cqb13.BaritoneController.gui.tabs;

import static meteordevelopment.meteorclient.MeteorClient.mc;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import dev.cqb13.BaritoneController.BaritoneManager;
import dev.cqb13.BaritoneController.config.BaritoneControllerConfig;
import dev.cqb13.BaritoneController.config.GotoCmdConfig;
import dev.cqb13.BaritoneController.config.SelCmdConfig;
import dev.cqb13.BaritoneController.config.TunnelCmdConfig;
import dev.cqb13.BaritoneController.gui.utils.WidgetSizing;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.tabs.Tab;
import meteordevelopment.meteorclient.gui.tabs.TabScreen;
import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
import meteordevelopment.meteorclient.gui.widgets.WLabel;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
import meteordevelopment.meteorclient.gui.widgets.input.WBlockPosEdit;
import meteordevelopment.meteorclient.gui.widgets.input.WIntEdit;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
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
        return new BaritoneControllerScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof BaritoneControllerScreen;
    }

    private static class BaritoneControllerScreen extends WindowTabScreen {
        private BaritoneControllerConfig settings;
        private IBaritone b;

        public BaritoneControllerScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            this.settings = BaritoneControllerConfig.get();
            b = BaritoneAPI.getProvider().getPrimaryBaritone();
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
            selCmdSection();
            tunnelCmdSection();
        }

        private void tunnelCmdSection() {
            TunnelCmdConfig sectionSettings = settings.getTunnelCmdConfig();

            WSection tunnelCmdSection = theme.section("Tunnel", sectionSettings.isExpanded());

            WHorizontalList widthContainer = theme.horizontalList();
            WLabel widthLabel = theme.label("Width");
            WIntEdit width = theme.intEdit(3, 1, 1000, 1, 100, false);

            WHorizontalList heightContainer = theme.horizontalList();
            WLabel heightLabel = theme.label("Height");
            WIntEdit height = theme.intEdit(3, 1, 1000, 1, 100, false);

            WHorizontalList depthContainer = theme.horizontalList();
            WLabel depthLabel = theme.label("Depth");
            WIntEdit depth = theme.intEdit(5, 1, 10000, 1, 100, false);

            WButton digBtn = theme.button("Start Digging");

            tunnelCmdSection.action = () -> {
                sectionSettings.setExpanded(tunnelCmdSection.isExpanded());
            };

            width.action = () -> {
                sectionSettings.setWidth(width.get());
            };

            height.action = () -> {
                sectionSettings.setHeight(height.get());
            };

            depth.action = () -> {
                sectionSettings.setDepth(depth.get());
            };

            add(tunnelCmdSection).expandX();

            tunnelCmdSection.add(widthContainer).expandX();
            widthContainer.add(widthLabel).expandCellX();
            widthContainer.add(width).expandX();

            tunnelCmdSection.add(heightContainer).expandX();
            heightContainer.add(heightLabel).expandCellX();
            heightContainer.add(height).expandX();

            tunnelCmdSection.add(depthContainer).expandX();
            depthContainer.add(depthLabel).expandCellX();
            depthContainer.add(depth).expandX();

            tunnelCmdSection.add(digBtn).centerX().expandX();
        }

        private void selCmdSection() {
            SelCmdConfig sectionSettings = settings.getSelCmdConfig();

            WSection selCmdSection = theme.section("Selection", sectionSettings.isExpanded());
            WBlockPosEdit selection1 = theme.blockPosEdit(sectionSettings.getSel1());
            WBlockPosEdit selection2 = theme.blockPosEdit(sectionSettings.getSel2());
            WHorizontalList selectionManagementContainer = theme.horizontalList();
            WButton createSelBtn = theme.button("Create Selection");
            WButton clearSelBtn = theme.button("Clear Selection");
            WButton clearAreaBtn = theme.button("Clear Area");

            selCmdSection.action = () -> {
                sectionSettings.setExpanded(selCmdSection.isExpanded());
            };

            selection1.action = () -> {
                sectionSettings.setSe1(selection1.get());
            };

            selection2.action = () -> {
                sectionSettings.setSel2(selection2.get());
            };

            createSelBtn.action = () -> {
                if (sectionSettings.getSelection() != null) {
                    BaritoneManager.removeSelection(b, sectionSettings.getSelection());
                }

                var sel = BaritoneManager.createSelection(b, sectionSettings.getSel1(), sectionSettings.getSel2());

                sectionSettings.setSelection(sel);
            };

            clearSelBtn.action = () -> {
                BaritoneManager.removeSelection(b, sectionSettings.getSelection());
            };

            clearAreaBtn.action = () -> {
                BaritoneManager.clearSelectionArea(b, sectionSettings.getSelection());
            };

            add(selCmdSection).expandX();
            selCmdSection.add(selection1).expandX();
            selCmdSection.add(selection2).expandX();
            selCmdSection.add(selectionManagementContainer).expandX();
            selectionManagementContainer.add(createSelBtn).expandX();
            selectionManagementContainer.add(clearSelBtn).expandX();
            selCmdSection.add(clearAreaBtn).expandX();
        }

        private void gotoCmdSection() {
            GotoCmdConfig sectionSettings = BaritoneControllerConfig.get().getGotoCmdConfig();

            WSection gotoCmdSection = theme.section("Go To", sectionSettings.isExpanded());
            WBlockPosEdit gotoCoords = theme.blockPosEdit(sectionSettings.getBlockPos());
            WButton gotoBtn = theme.button("Execute");
            WHorizontalList ignoreYContainer = theme.horizontalList();
            WCheckbox ignoreY = theme.checkbox(sectionSettings.getIgnoreY());
            WLabel label = theme.label("Ignore Y");

            gotoCmdSection.action = () -> {
                sectionSettings.setExpanded(gotoCmdSection.isExpanded());
            };

            gotoCoords.action = () -> {
                sectionSettings.setBlockPos(gotoCoords.get());
            };

            ignoreY.action = () -> {
                sectionSettings.setIgnoreY(ignoreY.checked);
            };

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
