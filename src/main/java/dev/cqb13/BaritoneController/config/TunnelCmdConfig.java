package dev.cqb13.BaritoneController.config;

import net.minecraft.nbt.NbtCompound;

public class TunnelCmdConfig extends SectionConfig {
    private int width;
    private int height;
    private int depth;

    public TunnelCmdConfig(int width, int height, int depth, Runnable save) {
        super("goto", true, save);
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        super.save();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        super.save();
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
        super.save();
    }

    @Override
    public void reset() {
        this.width = 3;
        this.height = 3;
        this.depth = 5;
    }

    @Override
    public NbtCompound toTag() {
        var tag = super.toTag();

        tag.putInt("width", this.width);
        tag.putInt("height", this.height);
        tag.putInt("depth", this.depth);

        return tag;
    }

    @Override
    public TunnelCmdConfig fromTag(NbtCompound tag) {
        super.fromTag(tag);

        tag.getInt("width").ifPresentOrElse(
                w -> this.width = w,
                () -> this.width = 3);

        tag.getInt("height").ifPresentOrElse(
                h -> this.height = h,
                () -> this.height = 3);

        tag.getInt("depth").ifPresentOrElse(
                d -> this.depth = d,
                () -> this.depth = 3);

        return this;
    }
}
