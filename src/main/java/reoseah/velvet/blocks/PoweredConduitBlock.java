package reoseah.velvet.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.world.BlockView;
import reoseah.velvet.blocks.state.OptionalDirection;

public class PoweredConduitBlock extends BareConduitBlock implements BlockEntityProvider {
    public static final EnumProperty<OptionalDirection> DIRECTION = EnumProperty.of("direction", OptionalDirection.class);
    public static final BooleanProperty LIT = Properties.LIT;

    public PoweredConduitBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DIRECTION, OptionalDirection.NONE).with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DIRECTION, LIT);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return null;
    }
}
