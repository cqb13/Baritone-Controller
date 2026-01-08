package dev.cqb13.BaritoneController.config;

import meteordevelopment.meteorclient.systems.System;
import meteordevelopment.meteorclient.systems.Systems;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class BaritoneControllerConfig extends System<BaritoneControllerConfig> {
    private final GotoCmdConfig gotoCmdSettings;
    private final SelCmdConfig selCmdSettings;

    public BaritoneControllerConfig() {
        super("baritone-controller");

        Runnable saveCallback = this::save;

        this.gotoCmdSettings = new GotoCmdConfig(
                BlockPos.ORIGIN,
                true,
                saveCallback);

        this.selCmdSettings = new SelCmdConfig(
                BlockPos.ORIGIN,
                BlockPos.ORIGIN,
                saveCallback);
    }

    public static BaritoneControllerConfig get() {
        return Systems.get(BaritoneControllerConfig.class);
    }

    public GotoCmdConfig getGotoCmdSettings() {
        return gotoCmdSettings;
    }

    public SelCmdConfig getSelCmdSettings() {
        return selCmdSettings;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.put("goto-cmd", this.gotoCmdSettings.toTag());
        tag.put("sel-cmd", this.selCmdSettings.toTag());

        return tag;
    }

    @Override
    public BaritoneControllerConfig fromTag(NbtCompound tag) {
        tag.getCompound("goto-cmd").ifPresentOrElse(
                gotoTag -> this.gotoCmdSettings.fromTag(gotoTag),
                () -> this.gotoCmdSettings.reset());

        tag.getCompound("sel-cmd").ifPresentOrElse(
                selTag -> this.selCmdSettings.fromTag(selTag),
                () -> this.selCmdSettings.reset());

        return this;
    }
}
