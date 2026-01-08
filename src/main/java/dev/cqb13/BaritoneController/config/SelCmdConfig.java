package dev.cqb13.BaritoneController.config;

import baritone.api.selection.ISelection;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class SelCmdConfig extends SectionConfig {
    private BlockPos sel1;
    private BlockPos sel2;
    private ISelection sel;

    public SelCmdConfig(BlockPos sel1, BlockPos sel2, Runnable save) {
        super("sel", true, save);
        this.sel1 = sel1;
        this.sel2 = sel2;
    }

    @Override
    public void reset() {
        this.sel1 = BlockPos.ORIGIN;
        this.sel2 = BlockPos.ORIGIN;
        this.sel = null;
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

    public void setSelection(ISelection sel) {
        this.sel = sel;
    }

    public void setSe1(BlockPos sel1) {
        this.sel1 = sel1;
        super.save();
    }

    public void setSel2(BlockPos sel2) {
        this.sel2 = sel2;
        super.save();
    }

    @Override
    public NbtCompound toTag() {
        var tag = super.toTag();

        tag.putLong("sel1", sel1.asLong());
        tag.putLong("sel2", sel2.asLong());

        return tag;
    }

    @Override
    public SelCmdConfig fromTag(NbtCompound tag) {
        super.fromTag(tag);

        tag.getLong("sel1").ifPresentOrElse(
                l -> sel1 = BlockPos.fromLong(l),
                () -> sel1 = new BlockPos(0, 0, 0));

        tag.getLong("sel2").ifPresentOrElse(
                l -> sel2 = BlockPos.fromLong(l),
                () -> sel2 = new BlockPos(0, 0, 0));

        return this;
    }
}
