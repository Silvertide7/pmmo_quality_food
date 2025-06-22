package net.silvertide.pmmo_quality_food.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> FISHING_SKILL = BUILDER
            .comment("The skill that determines quality upgrades when fishing.")
            .define("fishingSkill", "fishing");

    public static final ModConfigSpec.DoubleValue FISHING_IRON_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("The chance per fishing skill level that an item with no quality will be upgraded to iron quality.")
            .comment("Every level of the configured skill will increase the chance to upgrade a no quality item to iron by this amount.")
            .comment("By default this is 0.5% per level. This means that if you have a fishing skill of 100, you will have a 30% chance of the item upgrading.")
            .comment("from having no quality to iron quality. At level 200 the item will always be iron quality at minimum.")
            .defineInRange("fishingIronUpgradeChancePerLevel", 0.003, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FISHING_GOLD_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("The chance per fishing skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade an iron quality item to gold by this amount.")
            .comment("By default this is 0.1% per level. This means that if you have a fishing skill of 100, you will have a 2% chance of the item upgrading.")
            .comment("from having iron quality to gold quality.")
            .defineInRange("fishingGoldUpgradeChancePerLevel", 0.001, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FISHING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("The chance per fishing skill level that an item with gold quality will be upgraded to diamond quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade a gold quality item to diamond by this amount.")
            .comment("By default this is 0.05% per level. This means that if you have a fishing skill of 100, you will have a 0.2% chance of the item upgrading.")
            .comment("from having gold quality to diamond quality.")
            .defineInRange("fishingDiamondUpgradeChancePerLevel", 0.0005, 0.0, 10000);

    public static final ModConfigSpec.ConfigValue<String> FARMING_SKILL = BUILDER
            .comment("The skill that determines quality upgrades when farming.")
            .define("farmingSkill", "farming");

    public static final ModConfigSpec.DoubleValue FARMING_IRON_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("The chance per farming skill level that an item with no quality will be upgraded to iron quality.")
            .comment("Every level of the configured skill will increase the chance to upgrade a no quality item to iron by this amount.")
            .comment("By default this is 0.5% per level. This means that if you have a farming skill of 100, you will have a 10% chance of the item upgrading.")
            .comment("from having no quality to iron quality. At level 200 the item will always be iron quality at minimum.")
            .defineInRange("farmingIronUpgradeChancePerLevel", 0.003, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FARMING_GOLD_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("The chance per farming skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade an iron quality item to gold by this amount.")
            .comment("By default this is 0.1% per level. This means that if you have a farming skill of 100, you will have a 2% chance of the item upgrading.")
            .comment("from having iron quality to gold quality.")
            .defineInRange("farmingGoldUpgradeChancePerLevel", 0.001, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue FARMING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL = BUILDER
            .comment("The chance per farming skill level that an item with iron quality will be upgraded to gold quality.")
            .comment("Every level of the configured skill will increases the chance to upgrade a no quality item to gold by this amount.")
            .comment("By default this is 0.05% per level. This means that if you have a farming skill of 100, you will have a 0.2% chance of the item upgrading.")
            .comment("from having gold quality to diamond quality.")
            .defineInRange("farmingDiamondUpgradeChancePerLevel", 0.0005, 0.0, 10000);

    public static final ModConfigSpec.BooleanValue UPGRADES_CAN_CHAIN = BUILDER
            .comment("If an upgrade can chain into the next upgrade.")
            .comment("If true then when an item's quality is upgraded it will do another roll to see if it can upgrade again.")
            .comment("If false then when an item's quality is upgraded it will not roll to see if it upgrades again, it will only upgrade one time.")
            .define("upgradesCanChain", false);

    public static final ModConfigSpec.DoubleValue IRON_QUALITY_BONUS = BUILDER
            .comment("Bonus experience multiplier for iron quality items.")
            .comment("The default is 1.20, or a 20% bonus. An item that normally grants 100 xp will grant 120 if it is a iron quality.")
            .defineInRange("ironyQualityBonus", 1.20, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue GOLD_QUALITY_BONUS = BUILDER
            .comment("Bonus experience multiplier for gold quality items.")
            .comment("The default is 1.40, or a 40% bonus. An item that normally grants 100 xp will grant 140 instead if it is gold quality.")
            .defineInRange("goldQualityBonus", 1.40, 0.0, 10000);

    public static final ModConfigSpec.DoubleValue DIAMOND_QUALITY_BONUS = BUILDER
            .comment("Bonus experience multiplier for diamond quality items.")
            .comment("The default is 1.75, or a 75% bonus. An item that normally grants 100 xp will grant 175 instead if it is diamond quality.")
            .defineInRange("diamondQualityBonus", 1.75, 0.0, 10000);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
