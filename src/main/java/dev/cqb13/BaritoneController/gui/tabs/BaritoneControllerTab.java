package dev.cqb13.BaritoneController.gui.tabs;

import static meteordevelopment.meteorclient.MeteorClient.mc;

import dev.cqb13.BaritoneController.config.BaritoneControllerConfig;
import dev.cqb13.BaritoneController.config.BaritoneControllerConfig.Section;
import dev.cqb13.BaritoneController.config.GotoCmdSettings;
import dev.cqb13.BaritoneController.config.SelCmdSettings;
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
import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;

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
        }

        private void selCmdSection() {
            SelCmdSettings sectionSettings = settings.getSelCmdSettings();
            boolean isCollapsed = this.settings.isCollapsedSection(Section.Sel);

            WSection selCmdSection = theme.section("Selection", !isCollapsed);
            selCmdSection.action = () -> {
                if (isCollapsed) {
                    settings.removeCollapsedSection(Section.Sel);
                } else {
                    settings.addCollapsedSection(Section.Sel);
                }
            };
            WBlockPosEdit selection1 = theme.blockPosEdit(sectionSettings.getSel1());
            WBlockPosEdit selection2 = theme.blockPosEdit(sectionSettings.getSel2());
            WHorizontalList selectionManagementContainer = theme.horizontalList();
            WButton createSelBtn = theme.button("Create Selection");
            WButton clearSelBtn = theme.button("Clear Selection");
            WButton clearAreaBtn = theme.button("Clear Area");

            selection1.action = () -> {
                sectionSettings.setSe1(selection1.get());
                settings.save();
            };

            selection2.action = () -> {
                sectionSettings.setSel2(selection2.get());
                settings.save();
            };

            createSelBtn.action = () -> {
                sectionSettings.createBaritoneSelection(b);
            };

            clearSelBtn.action = () -> {
                sectionSettings.removeSellection(b);
            };

            clearAreaBtn.action = () -> {
                System.out.println(sectionSettings.getSel1().toString());
                System.out.println(sectionSettings.getSel2().toString());
                sectionSettings.clearArea(b);
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
            GotoCmdSettings sectionSettings = BaritoneControllerConfig.get().getGotoCmdSettings();
            boolean isCollapsed = this.settings.isCollapsedSection(Section.Goto);

            WSection gotoCmdSection = theme.section("Go To", !isCollapsed);
            gotoCmdSection.action = () -> {
                if (isCollapsed) {
                    settings.removeCollapsedSection(Section.Goto);
                } else {
                    settings.addCollapsedSection(Section.Goto);
                }
            };

            WBlockPosEdit gotoCoords = theme.blockPosEdit(sectionSettings.getBlockPos());
            WButton gotoBtn = theme.button("Execute");
            WHorizontalList ignoreYContainer = theme.horizontalList();
            WCheckbox ignoreY = theme.checkbox(sectionSettings.getIgnoreY());
            WLabel label = theme.label("Ignore Y");

            gotoCoords.action = () -> {
                sectionSettings.setBlockPos(gotoCoords.get());
                settings.save();
            };

            ignoreY.action = () -> {
                sectionSettings.setIgnoreY(ignoreY.checked);
                settings.save();
            };

            gotoBtn.action = () -> {
                BaritoneControllerConfig.get()
                        .setGotoCmdSettings(new GotoCmdSettings(gotoCoords.get(), ignoreY.checked));
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
