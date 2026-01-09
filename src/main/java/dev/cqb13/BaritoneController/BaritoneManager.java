package dev.cqb13.BaritoneController;

import baritone.api.IBaritone;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
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

    // Based on:
    // https://github.com/cabaletta/baritone/blob/670dba0772cb46a0036bd3662982c7e8b7da1ce4/src/main/java/baritone/command/defaults/TunnelCommand.java#L39
    public static boolean digTunnel(IBaritone b, ClientWorld world, ClientPlayerEntity player, int width, int height,
            int depth) {
        if (world == null || player == null || width < 1 || height < 1 || depth < 1 || height > world.getHeight()) {
            return false;
        }

        BlockPos corner1;
        BlockPos corner2;

        width--;
        height--;

        int originX = player.getBlockX();
        int originY = player.getBlockY();
        int originZ = player.getBlockZ();

        int addition = ((width % 2 == 0) ? 0 : 1);
        switch (player.getFacing()) {
            case NORTH:
                corner1 = new BlockPos(originX - width / 2, originY, originZ);
                corner2 = new BlockPos(originX + width / 2 + addition, originY + height, originZ - depth);
                break;
            case EAST:
                corner1 = new BlockPos(originX, originY, originZ - width / 2);
                corner2 = new BlockPos(originX + depth, originY + height, originZ + width / 2 + addition);
                break;
            case SOUTH:
                corner1 = new BlockPos(originX + width / 2, originY, originZ);
                corner2 = new BlockPos(originX - width / 2 + addition, originY + height, originZ + depth);
                break;
            case WEST:
                corner1 = new BlockPos(originX, originY, originZ + width / 2 + addition);
                corner2 = new BlockPos(originX - depth, originY + height, originZ - width / 2);
                break;
            default:
                return false;
        }

        // kinda hacky, but whatever
        var tunnelSel = createSelection(b, corner1, corner2);
        clearSelectionArea(b, tunnelSel);
        removeSelection(b, tunnelSel);

        return true;
    }
}
