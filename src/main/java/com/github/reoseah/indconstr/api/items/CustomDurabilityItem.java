package com.github.reoseah.indconstr.api.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**
 * An item that provides custom durability value.
 * <p>
 * It's common for items like batteries to override damage bar to show their
 * charge. Using normal damage, however, would allow for different things, e.g.
 * putting Mending in anvil.
 * <p>
 * Taken from Cloth API under Unlicense license by Shedaniel
 */
public interface CustomDurabilityItem {
    @Environment(EnvType.CLIENT)
    double getDurabilityBarProgress(ItemStack stack);

    @Environment(EnvType.CLIENT)
    boolean hasDurabilityBar(ItemStack stack);

    @Environment(EnvType.CLIENT)
    default int getDurabilityBarColor(ItemStack stack) {
        return MathHelper.hsvToRgb(Math.max(0.0F, 1 - (float) getDurabilityBarProgress(stack)) / 3.0F, 1.0F, 1.0F);
    }
}
