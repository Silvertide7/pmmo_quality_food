package net.silvertide.pmmo_quality_food.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // COOKING
    public static final ModConfigSpec.ConfigValue<String> COOKING_SKILL = BUILDER
            .comment("")
            .comment("The skill that determines quality upgrades when cooking.")
            .define("cookingSkill", "cooking");

    public static final ModConfigSpec.DoubleValue COOKING_IRON_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per cooking skill level that an item with no quality will be upgraded to iron quality.")
            .comment("Every level of the configured skill will increase the chance to upgrade a no quality item to iron by this amount.")
            .comment("By default this is 0.5% per level. This means that if you have a cooking skill of 100, you will have a 10% chance of the item upgrading.")
            .comment("from having no quality to iron quality. At level 100 the item will have a 30% of upgrading to iron from no quality.")
            .defineInRange("cookingIronUpgradeChancePerLevel", 0.05, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue COOKING_GOLD_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per cooking skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade an iron quality item to gold by this amount.")
            .comment("By default this is 0.1% per level. This means that if you have a cooking skill of 100 you will have a 10% chance of the item upgrading.")
            .comment("from having iron quality to gold quality.")
            .defineInRange("cookingGoldUpgradeChancePerLevel", 0.05, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue COOKING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per cooking skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade a no quality item to gold by this amount.")
            .comment("By default this is 0.05% per level. This means that if you have a cooking skill of 100, you will have a 0.2% chance of the item upgrading.")
            .comment("from having gold quality to diamond quality.")
            .defineInRange("cookingDiamondUpgradeChancePerLevel", 0.05, 0.0, 10000);

    // FARMING
    public static final ModConfigSpec.ConfigValue<String> FARMING_SKILL = BUILDER
            .comment("")
            .comment("The skill that determines quality upgrades when farming.")
            .define("farmingSkill", "farming");

    public static final ModConfigSpec.DoubleValue FARMING_IRON_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per farming skill level that an item with no quality will be upgraded to iron quality.")
            .comment("Every level of the configured skill will increase the chance to upgrade a no quality item to iron by this amount.")
            .comment("By default this is 0.5% per level. This means that if you have a farming skill of 100, you will have a 10% chance of the item upgrading.")
            .comment("from having no quality to iron quality. At level 200 the item will always be iron quality at minimum.")
            .defineInRange("farmingIronUpgradeChancePerLevel", 0.003, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FARMING_GOLD_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per farming skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade an iron quality item to gold by this amount.")
            .comment("By default this is 0.1% per level. This means that if you have a farming skill of 100, you will have a 2% chance of the item upgrading.")
            .comment("from having iron quality to gold quality.")
            .defineInRange("farmingGoldUpgradeChancePerLevel", 0.001, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FARMING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per farming skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade a no quality item to gold by this amount.")
            .comment("By default this is 0.05% per level. This means that if you have a farming skill of 100, you will have a 0.2% chance of the item upgrading.")
            .comment("from having gold quality to diamond quality.")
            .defineInRange("farmingDiamondUpgradeChancePerLevel", 0.0005, 0.0, 10000);

    // FISHING
    public static final ModConfigSpec.ConfigValue<String> FISHING_SKILL = BUILDER
            .comment("")
            .comment("The skill that determines quality upgrades when fishing.")
            .define("fishingSkill", "fishing");

    public static final ModConfigSpec.DoubleValue FISHING_IRON_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per fishing skill level that an item with no quality will be upgraded to iron quality.")
            .comment("Every level of the configured skill will increase the chance to upgrade a no quality item to iron by this amount.")
            .comment("By default this is 0.5% per level. This means that if you have a fishing skill of 100, you will have a 30% chance of the item upgrading.")
            .comment("from having no quality to iron quality. At level 200 the item will always be iron quality at minimum.")
            .defineInRange("fishingIronUpgradeChancePerLevel", 0.003, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FISHING_GOLD_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per fishing skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade an iron quality item to gold by this amount.")
            .comment("By default this is 0.1% per level. This means that if you have a fishing skill of 100, you will have a 2% chance of the item upgrading.")
            .comment("from having iron quality to gold quality.")
            .defineInRange("fishingGoldUpgradeChancePerLevel", 0.001, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FISHING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("")
            .comment("The chance per fishing skill level that an item with gold quality will be upgraded to diamond quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade a gold quality item to diamond by this amount.")
            .comment("By default this is 0.05% per level. This means that if you have a fishing skill of 100, you will have a 0.2% chance of the item upgrading.")
            .comment("from having gold quality to diamond quality.")
            .defineInRange("fishingDiamondUpgradeChancePerLevel", 0.0005, 0.0, 10000);

    public static final ModConfigSpec.BooleanValue UPGRADES_CAN_CHAIN = BUILDER
            .comment("")
            .comment("If an upgrade can chain into the next upgrade.")
            .comment("If true then when an item's quality is upgraded it will then move on to the next quality and see if it can upgrade again.")
            .comment("If false then when an item's quality is upgraded it will not roll to see if it upgrades again, it will only upgrade the one time.")
            .define("upgradesCanChain", false);

    public static final ModConfigSpec.DoubleValue CHAIN_MULTIPLIER = BUILDER
            .comment("")
            .comment("This modifies the chance of an item upgrading to the next quality if it is being chained.")
            .comment("This only applies if upgrades can chain is set to true.")
            .comment("When an item is upgraded to the next quality and chaining is true it will try to upgrade the quality again.")
            .comment("The chance for it to upgrade a second time (or chain) will be multiplied by this modifier.")
            .comment("If normally there is a 10% chance to upgrade from iron to gold, and this chain modifier is set to 0.50 (or 50%), then it will result in")
            .comment("only a 5% chance for it to upgrade since it is being chained (0.1 * 0.5 = 0.05). This reduces the power creep of chaining as a players level gets higher.")
            .comment("This chain multiplier is multiplied by itself if the iron -> gold upgrade succeeds and it then tries to chain into diamond.")
            .comment("This means the iron -> gold 0.5 will be multiplied by 0.5 again, resulting in the diamond upgrade chance being reduced to only 25% of its normal value.")
            .comment("The default is 1.0, which means there is no chain multiplier. This will have no effect at 1.0, the values will remain the same during chaining.")
            .defineInRange("chainMultiplier", 1.0, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue IRON_QUALITY_BONUS = BUILDER
            .comment("")
            .comment("Bonus experience multiplier for iron quality items.")
            .comment("The default is 1.20, or a 20% bonus. An item that normally grants 100 xp will grant 120 if it is iron quality.")
            .defineInRange("ironyQualityBonus", 1.20, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue GOLD_QUALITY_BONUS = BUILDER
            .comment("")
            .comment("Bonus experience multiplier for gold quality items.")
            .comment("The default is 1.40, or a 40% bonus. An item that normally grants 100 xp will grant 140 instead if it is gold quality.")
            .defineInRange("goldQualityBonus", 1.40, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue DIAMOND_QUALITY_BONUS = BUILDER
            .comment("")
            .comment("Bonus experience multiplier for diamond quality items.")
            .comment("The default is 1.75, or a 75% bonus. An item that normally grants 100 xp will grant 175 instead if it is diamond quality.")
            .defineInRange("diamondQualityBonus", 1.75, 0.0, 10000);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
