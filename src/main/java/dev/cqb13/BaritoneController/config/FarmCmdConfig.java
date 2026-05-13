package dev.cqb13.BaritoneController.config;

import net.minecraft.nbt.CompoundTag;

public class FarmCmdConfig extends SectionConfig {
    private boolean useRange;
    private int range;

    public FarmCmdConfig(int range, boolean useRange, Runnable save) {
        super("farm", true, save);
        this.range = range;
        this.useRange = useRange;
    }

    @Override
    public void reset() {
        this.useRange = false;
        this.range = 50;
    }

    public boolean isUseRange() {
        return useRange;
    }

    public void setUseRange(boolean useRange) {
        this.useRange = useRange;
        super.save();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
        super.save();
    }

    @Override
    public CompoundTag toTag() {
        var tag = super.toTag();

        tag.putInt("range", this.range);
        tag.putBoolean("useRange", this.useRange);

        return tag;
    }

    @Override
    public FarmCmdConfig fromTag(CompoundTag tag) {
        super.fromTag(tag);

        this.range = tag.getIntOr("range", 50);
        this.useRange = tag.getBooleanOr("useRange", false);

        return this;
    }
}
