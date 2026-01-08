package dev.cqb13.BaritoneController.config;

import meteordevelopment.meteorclient.systems.System;
import meteordevelopment.meteorclient.systems.Systems;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class BaritoneControllerConfig extends System<BaritoneControllerConfig> {
    private final GotoCmdConfig gotoCmdConfig;
    private final SelCmdConfig selCmdConfig;
    private final TunnelCmdConfig tunnelCmdConfig;

    public BaritoneControllerConfig() {
        super("baritone-controller");

        Runnable saveCallback = this::save;

        this.gotoCmdConfig = new GotoCmdConfig(
                BlockPos.ORIGIN,
                true,
                saveCallback);

        this.selCmdConfig = new SelCmdConfig(
                BlockPos.ORIGIN,
                BlockPos.ORIGIN,
                saveCallback);

        this.tunnelCmdConfig = new TunnelCmdConfig(3, 3, 5, saveCallback);
    }

    public static BaritoneControllerConfig get() {
        return Systems.get(BaritoneControllerConfig.class);
    }

    public GotoCmdConfig getGotoCmdConfig() {
        return gotoCmdConfig;
    }

    public SelCmdConfig getSelCmdConfig() {
        return selCmdConfig;
    }

    public TunnelCmdConfig getTunnelCmdConfig() {
        return tunnelCmdConfig;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.put("goto-cmd", this.gotoCmdConfig.toTag());
        tag.put("sel-cmd", this.selCmdConfig.toTag());
        tag.put("tunnel-cmd", this.tunnelCmdConfig.toTag());

        return tag;
    }

    @Override
    public BaritoneControllerConfig fromTag(NbtCompound tag) {
        tag.getCompound("goto-cmd").ifPresentOrElse(
                gotoTag -> this.gotoCmdConfig.fromTag(gotoTag),
                () -> this.gotoCmdConfig.reset());

        tag.getCompound("sel-cmd").ifPresentOrElse(
                selTag -> this.selCmdConfig.fromTag(selTag),
                () -> this.selCmdConfig.reset());

        tag.getCompound("tunnel-cmd").ifPresentOrElse(
                tunnelTag -> this.tunnelCmdConfig.fromTag(tunnelTag),
                () -> this.tunnelCmdConfig.reset());

        return this;
    }
}
