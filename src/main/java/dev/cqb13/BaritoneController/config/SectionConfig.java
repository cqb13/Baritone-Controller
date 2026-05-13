package dev.cqb13.BaritoneController.config;

import meteordevelopment.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.CompoundTag;

public abstract class SectionConfig implements ISerializable<SectionConfig> {
    private String sectionTag;
    private boolean expanded;
    private final Runnable save;

    public SectionConfig(String tag, boolean expanded, Runnable save) {
        this.sectionTag = tag;
        this.expanded = expanded;
        this.save = save;
    }

    protected void save() {
        this.save.run();
    }

    public String getSectionTag() {
        return this.sectionTag;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        save();
    }

    public abstract void reset();

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("expanded", expanded);

        return tag;
    }

    @Override
    public SectionConfig fromTag(CompoundTag tag) {
        this.expanded = tag.getBoolean("expanded").orElse(true);

        return this;
    }
}
