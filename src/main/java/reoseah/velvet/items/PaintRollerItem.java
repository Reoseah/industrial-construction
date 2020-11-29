package reoseah.velvet.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reoseah.velvet.Velvet;

public class PaintRollerItem extends Item {
    protected final DyeColor color;

    public PaintRollerItem(DyeColor color, Item.Settings settings) {
        super(settings);
        this.color = color;
    }

    @Override
    public String getTranslationKey() {
        return Velvet.Items.PAINT_ROLLER.getTranslationKey();
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText(this.getTranslationKey() + "." + this.color.asString()).formatted(Formatting.GRAY));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == Blocks.GLASS || state.getBlock() == Blocks.TERRACOTTA) {
            Block stained = state.getBlock() == Blocks.GLASS ? getStainedGlass(this.color) : getColoredTerracota(this.color);
            world.setBlockState(pos, stained.getDefaultState());
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getStack().damage(1, context.getPlayer(), player -> {
                    player.sendToolBreakStatus(context.getHand());
                    player.setStackInHand(context.getHand(), new ItemStack(Velvet.Items.PAINT_ROLLER));
                });
            }
            return ActionResult.SUCCESS;
        } else if (state.getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) > 0 && context.getPlayer() != null) {
            world.setBlockState(pos, state.with(CauldronBlock.LEVEL, state.get(CauldronBlock.LEVEL) - 1));
            context.getPlayer().setStackInHand(context.getHand(), new ItemStack(Velvet.Items.PAINT_ROLLER));
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    public static Block getStainedGlass(DyeColor color) {
        switch (color) {
        case WHITE:
            return Blocks.WHITE_STAINED_GLASS;
        case ORANGE:
            return Blocks.ORANGE_STAINED_GLASS;
        case MAGENTA:
            return Blocks.MAGENTA_STAINED_GLASS;
        case LIGHT_BLUE:
            return Blocks.LIGHT_BLUE_STAINED_GLASS;
        case YELLOW:
            return Blocks.YELLOW_STAINED_GLASS;
        case LIME:
            return Blocks.LIME_STAINED_GLASS;
        case PINK:
            return Blocks.PINK_STAINED_GLASS;
        case GRAY:
            return Blocks.GRAY_STAINED_GLASS;
        case LIGHT_GRAY:
            return Blocks.LIGHT_GRAY_STAINED_GLASS;
        case CYAN:
            return Blocks.CYAN_STAINED_GLASS;
        case PURPLE:
            return Blocks.PURPLE_STAINED_GLASS;
        case BLUE:
            return Blocks.BLUE_STAINED_GLASS;
        case BROWN:
            return Blocks.BROWN_STAINED_GLASS;
        case GREEN:
            return Blocks.GREEN_STAINED_GLASS;
        case RED:
            return Blocks.RED_STAINED_GLASS;
        case BLACK:
            return Blocks.BLACK_STAINED_GLASS;
        default:
            throw new UnsupportedOperationException();
        }
    }

    public static Block getColoredTerracota(DyeColor color) {
        switch (color) {
        case WHITE:
            return Blocks.WHITE_TERRACOTTA;
        case ORANGE:
            return Blocks.ORANGE_TERRACOTTA;
        case MAGENTA:
            return Blocks.MAGENTA_TERRACOTTA;
        case LIGHT_BLUE:
            return Blocks.LIGHT_BLUE_TERRACOTTA;
        case YELLOW:
            return Blocks.YELLOW_TERRACOTTA;
        case LIME:
            return Blocks.LIME_TERRACOTTA;
        case PINK:
            return Blocks.PINK_TERRACOTTA;
        case GRAY:
            return Blocks.GRAY_TERRACOTTA;
        case LIGHT_GRAY:
            return Blocks.LIGHT_GRAY_TERRACOTTA;
        case CYAN:
            return Blocks.CYAN_TERRACOTTA;
        case PURPLE:
            return Blocks.PURPLE_TERRACOTTA;
        case BLUE:
            return Blocks.BLUE_TERRACOTTA;
        case BROWN:
            return Blocks.BROWN_TERRACOTTA;
        case GREEN:
            return Blocks.GREEN_TERRACOTTA;
        case RED:
            return Blocks.RED_TERRACOTTA;
        case BLACK:
            return Blocks.BLACK_TERRACOTTA;
        default:
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public DyeColor getColor() {
        return color;
    }
}
