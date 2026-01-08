package dev.cqb13.BaritoneController;

import baritone.api.IBaritone;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class BaritoneManager {
    public static ISelection createSelection(IBaritone b, BlockPos sel1, BlockPos sel2) {
        if (b == null || sel1 == null || sel2 == null) {
            return null;
        }

        ISelectionManager selectionManager = b.getSelectionManager();
        return selectionManager.addSelection(new BetterBlockPos(sel1), new BetterBlockPos(sel2));
    }

    public static boolean removeSelection(IBaritone b, ISelection sel) {
        if (b == null || sel == null) {
            return false;
        }

        ISelectionManager selectionManager = b.getSelectionManager();
        selectionManager.removeSelection(sel);

        return true;
    }

    public static boolean clearSelectionArea(IBaritone b, ISelection sel) {
        if (b == null || sel == null) {
            return false;
        }
        Vec3i size = sel.size();

        ISchematic schematic = new FillSchematic(size.getX(), size.getY(), size.getZ(),
                new BlockOptionalMeta(Blocks.AIR));

        b.getBuilderProcess().build("Fill", schematic, sel.min());

        return true;

    }
}
