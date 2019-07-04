package com.blamejared.compat.tcomplement.highoven;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.blamejared.ModTweaker;
import com.blamejared.compat.mantle.RecipeMatchIIngredient;
import com.blamejared.compat.tcomplement.highoven.recipes.MixRecipeTweaker;
import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseAction;
import com.google.common.collect.ImmutableList;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import knightminer.tcomplement.library.TCompRegistry;
import knightminer.tcomplement.library.steelworks.IMixRecipe;
import knightminer.tcomplement.library.steelworks.MixAdditive;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.tcomplement.highoven.IMixRecipe")
@ZenRegister
@ModOnly("tcomplement")
public class MixRecipeHelper {

	private ILiquidStack output, input;
	private int temp;
	private Map<IIngredient, Integer> oxidizers, reducers, purifiers;

	public MixRecipeHelper(ILiquidStack output, ILiquidStack input, int temp) {
		this.output = output;
		this.input = input;
		this.temp = temp;
		this.oxidizers = new LinkedHashMap<IIngredient, Integer>();
		this.reducers = new LinkedHashMap<IIngredient, Integer>();
		this.purifiers = new LinkedHashMap<IIngredient, Integer>();
	}

	@ZenGetter("output")
	public ILiquidStack getOutput() {
		return this.output;
	}

	@ZenSetter("output")
	public void setOutput(ILiquidStack output) {
		this.output = output;
	}

	@ZenGetter("input")
	public ILiquidStack getInput() {
		return this.input;
	}

	@ZenSetter("input")
	public void setInput(ILiquidStack input) {
		this.input = input;
	}

	@ZenGetter("temp")
	public int getTemp() {
		return this.temp;
	}

	@ZenSetter("temp")
	public void setTemp(int temp) {
		this.temp = temp;
	}

	@ZenGetter("oxidizers")
	public List<IIngredient> getOxidizers() {
		return ImmutableList.copyOf(this.oxidizers.keySet());
	}

	@ZenGetter("reducers")
	public List<IIngredient> getReducers() {
		return ImmutableList.copyOf(this.reducers.keySet());
	}

	@ZenGetter("purifiers")
	public List<IIngredient> getPurifiers() {
		return ImmutableList.copyOf(this.purifiers.keySet());
	}

	@ZenMethod
	public int getOxidizerChance(IIngredient oxidizer) {
		return this.oxidizers.getOrDefault(oxidizer, -1);
	}

	@ZenMethod
	public int getReducerChance(IIngredient reducer) {
		return this.reducers.getOrDefault(reducer, -1);
	}

	@ZenMethod
	public int getPurifierChance(IIngredient purifier) {
		return this.purifiers.getOrDefault(purifier, -1);
	}

	@ZenMethod
	public MixRecipeHelper addOxidizer(IIngredient oxidizer, int consumeChance) {
		this.oxidizers.put(oxidizer, consumeChance);
		return this;
	}

	@ZenMethod
	public MixRecipeHelper addReducer(IIngredient reducer, int consumeChance) {
		this.reducers.put(reducer, consumeChance);
		return this;
	}

	@ZenMethod
	public MixRecipeHelper addPurifier(IIngredient purifier, int consumeChance) {
		this.purifiers.put(purifier, consumeChance);
		return this;
	}

	@ZenMethod
	public MixRecipeHelper removeOxidizer(IIngredient oxidizer) {
		this.oxidizers.remove(oxidizer);
		return this;
	}

	@ZenMethod
	public MixRecipeHelper removeOReducer(IIngredient reducer) {
		this.oxidizers.remove(reducer);
		return this;
	}

	@ZenMethod
	public MixRecipeHelper removePurifier(IIngredient purifier) {
		this.oxidizers.remove(purifier);
		return this;
	}

	@ZenMethod
	public MixRecipeHelper removeAllOxidizer() {
		this.oxidizers = new LinkedHashMap<IIngredient, Integer>();
		return this;
	}

	@ZenMethod
	public MixRecipeHelper removeAllReducer() {
		this.reducers = new LinkedHashMap<IIngredient, Integer>();
		return this;
	}

	@ZenMethod
	public MixRecipeHelper removeAllPurifier() {
		this.purifiers = new LinkedHashMap<IIngredient, Integer>();
		return this;
	}

	@ZenMethod
	public void register() {
		ModTweaker.LATE_ADDITIONS.add(
				new AddMixRecipe(this.output, this.input, this.temp, this.oxidizers, this.reducers, this.purifiers));
	}

	private static class AddMixRecipe extends BaseAction {
		private FluidStack input, output;
		private int temp;
		private Map<RecipeMatch, MixAdditive> additives;

		public AddMixRecipe(ILiquidStack output, ILiquidStack input, int temp, Map<IIngredient, Integer> oxidizers,
				Map<IIngredient, Integer> reducers, Map<IIngredient, Integer> purifiers) {
			super("HighOven.MixRecipe");
			this.input = InputHelper.toFluid(input);
			this.output = InputHelper.toFluid(output);
			this.temp = temp;
			this.additives = new LinkedHashMap<>();
			oxidizers.forEach((IIngredient ingredient, Integer chance) -> this.additives
					.put(new RecipeMatchIIngredient(ingredient, chance), MixAdditive.OXIDIZER));
			reducers.forEach((IIngredient ingredient, Integer chance) -> this.additives
					.put(new RecipeMatchIIngredient(ingredient, chance), MixAdditive.REDUCER));
			purifiers.forEach((IIngredient ingredient, Integer chance) -> this.additives
					.put(new RecipeMatchIIngredient(ingredient, chance), MixAdditive.PURIFIER));
		}

		@Override
		public void apply() {
			IMixRecipe recipe = TCompRegistry.registerMix(new MixRecipeTweaker(this.input, this.output, this.temp));
			for (Map.Entry<RecipeMatch, MixAdditive> entry : this.additives.entrySet()) {
				recipe.addAdditive(entry.getValue(), entry.getKey());
			}
		}

		@Override
		public String describe() {
			return String.format("Adding %s Recipe for %s", this.name, this.getRecipeInfo());
		}

		@Override
		protected String getRecipeInfo() {
			return LogHelper.getStackDescription(output) + " from " + LogHelper.getStackDescription(input);
		}
	}
}
