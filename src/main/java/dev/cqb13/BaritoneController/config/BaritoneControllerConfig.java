package dev.cqb13.BaritoneController.config;

import meteordevelopment.meteorclient.systems.System;
import meteordevelopment.meteorclient.systems.Systems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class BaritoneControllerConfig extends System<BaritoneControllerConfig> {
    private final GotoCmdConfig gotoCmdConfig;
    private final SelCmdConfig selCmdConfig;
    private final TunnelCmdConfig tunnelCmdConfig;
    private final FarmCmdConfig farmCmdConfig;

    public BaritoneControllerConfig() {
        super("baritone-controller");

        Runnable saveCallback = this::save;

        this.gotoCmdConfig = new GotoCmdConfig(
                BlockPos.ZERO,
                true,
                saveCallback);

        this.selCmdConfig = new SelCmdConfig(
                BlockPos.ZERO,
                BlockPos.ZERO,
                saveCallback);

        this.tunnelCmdConfig = new TunnelCmdConfig(3, 3, 5, saveCallback);

        this.farmCmdConfig = new FarmCmdConfig(50, false, saveCallback);
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

    public FarmCmdConfig getFarmCmdConfig() {
        return farmCmdConfig;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.put("goto-cmd", this.gotoCmdConfig.toTag());
        tag.put("sel-cmd", this.selCmdConfig.toTag());
        tag.put("tunnel-cmd", this.tunnelCmdConfig.toTag());
        tag.put("farm-cmd", this.farmCmdConfig.toTag());

        return tag;
    }

    @Override
    public BaritoneControllerConfig fromTag(CompoundTag tag) {
        tag.getCompound("goto-cmd").ifPresentOrElse(
                gotoTag -> this.gotoCmdConfig.fromTag(gotoTag),
                () -> this.gotoCmdConfig.reset());

        tag.getCompound("sel-cmd").ifPresentOrElse(
                selTag -> this.selCmdConfig.fromTag(selTag),
                () -> this.selCmdConfig.reset());

        tag.getCompound("tunnel-cmd").ifPresentOrElse(
                tunnelTag -> this.tunnelCmdConfig.fromTag(tunnelTag),
                () -> this.tunnelCmdConfig.reset());

        tag.getCompound("farm-cmd").ifPresentOrElse(
                farmTag -> this.farmCmdConfig.fromTag(farmTag),
                () -> this.farmCmdConfig.reset());

        return this;
    }
}
