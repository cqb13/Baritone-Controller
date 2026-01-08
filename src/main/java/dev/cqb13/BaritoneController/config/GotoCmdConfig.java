package dev.cqb13.BaritoneController.config;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class GotoCmdConfig extends SectionConfig {
    private BlockPos blockPos;
    private boolean ignoreY;

    public GotoCmdConfig(BlockPos blockPos, boolean ignoreY, Runnable save) {
        super("goto", true, save);
        this.blockPos = blockPos;
        this.ignoreY = ignoreY;
    }

    @Override
    public void reset() {
        this.blockPos = BlockPos.ORIGIN;
        this.ignoreY = true;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public void setBlockPos(BlockPos pos) {
        this.blockPos = pos;
        super.save();
    }

    public boolean getIgnoreY() {
        return this.ignoreY;
    }

    public void setIgnoreY(boolean value) {
        this.ignoreY = value;
        super.save();
    }

    @Override
    public NbtCompound toTag() {
        var tag = super.toTag();

        tag.putLong("pos", blockPos.asLong());
        tag.putBoolean("ignoreY", ignoreY);

        return tag;
    }

    @Override
    public GotoCmdConfig fromTag(NbtCompound tag) {
        super.fromTag(tag);

        tag.getLong("pos").ifPresentOrElse(
                l -> blockPos = BlockPos.fromLong(l),
                () -> blockPos = new BlockPos(0, 0, 0));

        this.ignoreY = tag.getBoolean("ignoreY").orElse(true);

        return this;
    }
}
