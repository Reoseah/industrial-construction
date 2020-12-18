package com.github.reoseah.icon.items;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.github.reoseah.icon.ICon;
import com.github.reoseah.icon.api.blocks.PaintableBlock;

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

public class PaintRollerItem extends SimpleCustomDurabilityItem {
    public static final Map<DyeColor, PaintRollerItem> INSTANCES = new EnumMap<>(DyeColor.class);

    protected final DyeColor color;

    public static PaintRollerItem byColor(DyeColor color) {
        return INSTANCES.get(color);
    }

    public PaintRollerItem(DyeColor color, Item.Settings settings) {
        super(settings);
        this.color = color;
        INSTANCES.put(color, this);
    }

    @Override
    public String getTranslationKey() {
        return ICon.Items.PAINT_ROLLER.getTranslationKey();
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
        Block block = state.getBlock();

        if (block == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) > 0 && context.getPlayer() != null) {
            world.setBlockState(pos, state.with(CauldronBlock.LEVEL, state.get(CauldronBlock.LEVEL) - 1));
            context.getPlayer().setStackInHand(context.getHand(), new ItemStack(ICon.Items.PAINT_ROLLER));

            return ActionResult.SUCCESS;
        }

        if (block instanceof PaintableBlock) {
            PaintableBlock paintable = (PaintableBlock) block;

            if (paintable.canPaintBlock(this.color, state, world, pos)) {
                int amount = paintable.getPaintConsumption(color, state, world, pos);
                if (amount <= this.getCustomMaxDamage() - this.getDamage(context.getStack())) {
                    paintable.onPainted(this.color, state, world, pos);

                    this.damage(context.getStack(), amount, context.getPlayer(), player -> {
                        player.sendToolBreakStatus(context.getHand());
                        player.setStackInHand(context.getHand(), new ItemStack(ICon.Items.PAINT_ROLLER));
                    });

                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.FAIL;
        }

        if (block == Blocks.GLASS) {
            world.setBlockState(pos, getStainedGlass(this.color).getDefaultState());
            this.damage(context.getStack(), 1, context.getPlayer(), player -> {
                player.sendToolBreakStatus(context.getHand());
                player.setStackInHand(context.getHand(), new ItemStack(ICon.Items.PAINT_ROLLER));
            });
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

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public int getCustomMaxDamage() {
        return 32;
    }
}
