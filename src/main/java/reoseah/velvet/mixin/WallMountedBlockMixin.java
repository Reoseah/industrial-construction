package reoseah.velvet.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import reoseah.velvet.blocks.ExtractorBlock;

@Mixin(WallMountedBlock.class)
public class WallMountedBlockMixin {
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        if ((Object) this instanceof LeverBlock) {
            Direction direction = getDirection(state).getOpposite();
            BlockPos pos2 = pos.offset(direction);
            if (world.getBlockState(pos2).getBlock() instanceof ExtractorBlock) {
                callback.setReturnValue(true);
            }
        }
    }

    @Shadow
    private static Direction getDirection(BlockState state) {
        return null;
    }
}
