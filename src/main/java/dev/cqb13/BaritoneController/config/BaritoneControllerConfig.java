package dev.cqb13.BaritoneController.config;

import java.util.ArrayList;
import java.util.Optional;

import meteordevelopment.meteorclient.systems.System;
import meteordevelopment.meteorclient.systems.Systems;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class BaritoneControllerConfig extends System<BaritoneControllerConfig> {
    private ArrayList<Section> collapsedSections = new ArrayList<>();
    private GotoCmdSettings gotoCmdSettings = new GotoCmdSettings(new BlockPos(0, 0, 0), true);

    public BaritoneControllerConfig() {
        super("baritone-controller");
    }

    public static BaritoneControllerConfig get() {
        return Systems.get(BaritoneControllerConfig.class);
    }

    public boolean addCollapsedSection(Section section) {
        if (collapsedSections.contains(section)) {
            return false;
        }

        collapsedSections.add(section);
        save();

        return true;
    }

    public boolean removeCollapsedSection(Section section) {
        if (collapsedSections.remove(section)) {
            save();
            return true;
        }

        return false;
    }

    public boolean isCollapsedSection(Section section) {
        return collapsedSections.contains(section);
    }

    public GotoCmdSettings getGotoCmdSettings() {
        return gotoCmdSettings;
    }

    public void setGotoCmdSettings(GotoCmdSettings newSettings) {
        this.gotoCmdSettings = newSettings;
        save();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        var list = new net.minecraft.nbt.NbtList();
        for (Section section : collapsedSections) {
            list.add(net.minecraft.nbt.NbtString.of(section.name()));
        }
        tag.put("collapsed-sections", list);

        tag.put("goto-cmd", this.gotoCmdSettings.toTag());

        return tag;
    }

    @Override
    public BaritoneControllerConfig fromTag(NbtCompound tag) {
        collapsedSections.clear();
        tag.getList("collapsed-sections")
                .ifPresent(list -> {
                    for (int i = 0; i < list.size(); i++) {
                        list.getString(i)
                                .flatMap(Section::fromString)
                                .ifPresent(collapsedSections::add);
                    }
                });

        tag.getCompound("goto-cmd").ifPresentOrElse(
                gotoTag -> this.gotoCmdSettings.fromTag(gotoTag),
                () -> this.gotoCmdSettings = new GotoCmdSettings(new BlockPos(0, 0, 0), true));

        return this;
    }

    public enum Section {
        Goto;

        public static Optional<Section> fromString(String value) {
            if (value == null)
                return Optional.empty();

            try {
                return Optional.of(Section.valueOf(value));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }

}
