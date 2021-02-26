package com.github.reoseah.iconstruct;

import net.fabricmc.api.ModInitializer;

public class IConstructInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        IConstruct.ITEMGROUP.getClass();

        IConstruct.Blocks.CONDUIT.getClass();
        IConstruct.Items.CONDUIT.getClass();
        IConstruct.BlockEntityTypes.CONDUIT.getClass();
        IConstruct.RecipeSerializers.PAINTROLLER_FILLING.getClass();
    }

}
