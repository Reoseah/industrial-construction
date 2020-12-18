package com.github.reoseah.icon;

import net.fabricmc.api.ModInitializer;

public class IConInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ICon.ITEMGROUP.getClass();

        ICon.Blocks.CONDUIT.getClass();
        ICon.Items.CONDUIT.getClass();
        ICon.Enchantments.PARSIMONY.getClass();
        ICon.BlockEntityTypes.CONDUIT.getClass();
        ICon.RecipeSerializers.PAINTROLLER_FILLING.getClass();
    }

}
