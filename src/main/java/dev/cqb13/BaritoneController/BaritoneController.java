package dev.cqb13.BaritoneController;

import com.mojang.logging.LogUtils;

import dev.cqb13.BaritoneController.gui.tabs.BaritoneControllerTab;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.gui.tabs.Tabs;

import org.slf4j.Logger;

public class BaritoneController extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOG.info("Initializing Baritone Controller\n\tBy: cqb13 - https://github.com/cqb13/Baritone-Controller");

        LOG.info("Adding Tabs...");
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
