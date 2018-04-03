package com.blamejared.compat.betterwithmods.base.bulkrecipes;

import betterwithmods.common.registry.bulk.manager.CraftingManagerBulk;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenMethod;

public class CookingPotBuilder extends BulkRecipeBuilder<CookingPotRecipe> {
    public static final int UNSTOKED = 1;
    public static final int STOKED = 2;

    private int heat = 1;

    protected CookingPotBuilder(CraftingManagerBulk<CookingPotRecipe> registry, String name) {
        super(registry, name);
    }

    @ZenMethod
    @Override
    public void build() {
        addRecipe(new CookingPotRecipe(inputs, outputs, heat).setPriority(priority));
    }

    @ZenMethod
    public CookingPotBuilder setHeat(int heat) {
        this.heat = heat;
        return this;
    }

    @ZenMethod
    @Override
    public CookingPotBuilder setPriority(int priority) {
        return (CookingPotBuilder) super.setPriority(priority);
    }

    @ZenMethod
    @Override
    public CookingPotBuilder buildRecipe(IIngredient[] inputs, IItemStack[] outputs) {
        return (CookingPotBuilder) super.buildRecipe(inputs, outputs);
    }


}