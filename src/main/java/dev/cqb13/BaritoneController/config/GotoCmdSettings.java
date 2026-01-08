package dev.cqb13.BaritoneController.config;

import java.util.Objects;

import meteordevelopment.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class GotoCmdSettings implements ISerializable<GotoCmdSettings> {
    private volatile BlockPos blockPos;
    private volatile boolean ignoreY;

    public GotoCmdSettings(BlockPos blockPos, boolean ignoreY) {
        this.blockPos = blockPos;
        this.ignoreY = ignoreY;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public void setBlockPos(BlockPos pos) {
        this.blockPos = pos;
    }

    public boolean getIgnoreY() {
        return this.ignoreY;
    }

    public void setIgnoreY(boolean value) {
        this.ignoreY = value;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putLong("pos", blockPos.asLong());
        tag.putBoolean("ignoreY", ignoreY);

        return tag;
    }

    @Override
    public GotoCmdSettings fromTag(NbtCompound tag) {
        tag.getLong("pos").ifPresentOrElse(
                l -> blockPos = BlockPos.fromLong(l),
                () -> blockPos = new BlockPos(0, 0, 0));

        this.ignoreY = tag.getBoolean("ignoreY").orElse(true);

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GotoCmdSettings otherSetting = (GotoCmdSettings) o;
        return Objects.equals(blockPos, otherSetting.blockPos) && ignoreY == otherSetting.ignoreY;
    }

    @Override
    public int hashCode() {
        return blockPos.hashCode();
    }
}
