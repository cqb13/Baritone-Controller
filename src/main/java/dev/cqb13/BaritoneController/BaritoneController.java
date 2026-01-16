package dev.cqb13.BaritoneController;

import org.meteordev.starscript.value.Value;
import org.meteordev.starscript.value.ValueMap;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import dev.cqb13.BaritoneController.config.BaritoneControllerConfig;
import dev.cqb13.BaritoneController.gui.tabs.BaritoneControllerTab;
import dev.cqb13.BaritoneController.hud.TextPresets;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.gui.tabs.Tabs;
import meteordevelopment.meteorclient.systems.Systems;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.utils.misc.MeteorStarscript;

public class BaritoneController extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final HudGroup BaritoneControllerHUD = new HudGroup("Baritone Controller");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Baritone Controller\n\tBy: cqb13 - https://github.com/cqb13/Baritone-Controller");

        LOG.info("Initializing config...");
        Systems.add(new BaritoneControllerConfig());

        LOG.info("Initializing starscript...");
        BaritoneControllerStarscript.init();
        MeteorStarscript.ss.set("baritoneController", Value.map(new ValueMap()
                .set("ticksInSegment", BaritoneControllerStarscript::getTicksRemainingInSegment)
                .set("secsInSegment", BaritoneControllerStarscript::getSecondsRemainingInSegment)
                .set("ticksInGoal", BaritoneControllerStarscript::getTicksRemainingInGoal)
                .set("secsInGoal", BaritoneControllerStarscript::getSecondsRemainingInGoal)
                .set("bProccessExists", BaritoneControllerStarscript::isBaritoneProcessExists)));

        LOG.info("Adding HUD elements...");
        Hud hud = Systems.get(Hud.class);
        hud.register(TextPresets.INFO);

        LOG.info("Adding tabs...");
        Tabs.add(new BaritoneControllerTab());
    }

    @Override
    public String getPackage() {
        return "dev.cqb13.BaritoneController";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("cqb13", "Baritone-Controller");
    }
}
