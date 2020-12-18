package com.github.reoseah.icon.items;

import com.github.reoseah.icon.api.blocks.WrenchableBlock;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrenchItem extends Item {
    protected final Tag<Item> material;

    public WrenchItem(Tag<Item> material, Item.Settings settings) {
        super(settings);
        this.material = material;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof WrenchableBlock
                && ((WrenchableBlock) state.getBlock()).useWrench(state, world, pos, context.getSide(), context.getPlayer(), context.getHand(), context.getHitPos())) {
            context.getStack().damage(1, context.getPlayer(), player -> {
                player.sendToolBreakStatus(context.getHand());
            });

            return ActionResult.SUCCESS;

        }
        return super.useOnBlock(context);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.contains(ingredient.getItem());
    }
}
