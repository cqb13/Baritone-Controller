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

    public boolean getIgnoreY() {
        return this.ignoreY;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        if (blockPos != null) {
            NbtCompound posTag = new NbtCompound();
            posTag.putInt("x", blockPos.getX());
            posTag.putInt("y", blockPos.getY());
            posTag.putInt("z", blockPos.getZ());
            tag.put("pos", posTag);
        }

        tag.putBoolean("ignoreY", ignoreY);
        return tag;
    }

    @Override
    public GotoCmdSettings fromTag(NbtCompound tag) {
        this.blockPos = tag.getCompound("pos")
                .flatMap(pos -> pos.getInt("x")
                        .flatMap(x -> pos.getInt("y").flatMap(y -> pos.getInt("z").map(z -> new BlockPos(x, y, z)))))
                .orElse(null);

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
