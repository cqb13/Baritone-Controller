package dev.cqb13.BaritoneController.config;

import java.util.Objects;

import baritone.api.IBaritone;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import meteordevelopment.meteorclient.utils.misc.ISerializable;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class SelCmdSettings implements ISerializable<SelCmdSettings> {
    private volatile BlockPos sel1;
    private volatile BlockPos sel2;
    private volatile ISelection sel;

    public SelCmdSettings(BlockPos sel1, BlockPos sel2) {
        this.sel1 = sel1;
        this.sel2 = sel2;
    }

    public BlockPos getSel1() {
        return this.sel1;
    }

    public BlockPos getSel2() {
        return this.sel2;
    }

    public ISelection getSelection() {
        return this.sel;
    }

    public void setSe1(BlockPos sel1) {
        this.sel1 = sel1;
    }

    public void setSel2(BlockPos sel2) {
        this.sel2 = sel2;
    }

    public boolean createBaritoneSelection(IBaritone b) {
        if (b == null || this.sel1 == null || this.sel2 == null) {
            return false;
        }

        if (sel != null) {
            removeSellection(b);
        }

        ISelectionManager selectionManager = b.getSelectionManager();
        sel = selectionManager.addSelection(new BetterBlockPos(this.sel1), new BetterBlockPos(this.sel2));

        return true;
    }

    public boolean removeSellection(IBaritone b) {
        if (b == null || sel == null) {
            return false;
        }

        ISelectionManager selectionManager = b.getSelectionManager();
        selectionManager.removeSelection(this.sel);
        this.sel = null;

        return true;
    }

    public boolean clearArea(IBaritone b) {
        if (b == null || sel == null) {
            return false;
        }
        Vec3i size = sel.size();

        ISchematic schematic = new FillSchematic(size.getX(), size.getY(), size.getZ(),
                new BlockOptionalMeta(Blocks.AIR));

        b.getBuilderProcess().build("Fill", schematic, sel.min());

        return true;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putLong("sel1", sel1.asLong());
        tag.putLong("sel2", sel2.asLong());

        return tag;
    }

    @Override
    public SelCmdSettings fromTag(NbtCompound tag) {
        tag.getLong("sel1").ifPresentOrElse(
                l -> sel1 = BlockPos.fromLong(l),
                () -> sel1 = new BlockPos(0, 0, 0));

        tag.getLong("sel2").ifPresentOrElse(
                l -> sel2 = BlockPos.fromLong(l),
                () -> sel2 = new BlockPos(0, 0, 0));

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SelCmdSettings otherSetting = (SelCmdSettings) o;
        return Objects.equals(sel1, otherSetting.sel1) && Objects.equals(sel2, otherSetting.sel2);
    }

    @Override
    public int hashCode() {
        return sel1.hashCode() + sel2.hashCode();
    }
}
