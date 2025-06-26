package net.silvertide.pmmo_quality_food.utils;

import de.cadentem.quality_food.core.codecs.Quality;
import de.cadentem.quality_food.util.QualityUtils;
import harmonised.pmmo.api.APIUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_quality_food.data.UpgradeQuality;
import net.silvertide.pmmo_quality_food.data.ActionType;
import net.silvertide.pmmo_quality_food.config.Config;

public final class QualityHelper {
    private QualityHelper() {}

    public static Long applyQualityBonus(ItemStack stack, Long xpValue) {
        Quality quality = QualityUtils.getQuality(stack);
        if(quality.level() == 0) return xpValue;

        double bonus = 1.0;
        if (quality.level() == 1) {
            bonus = Config.IRON_QUALITY_BONUS.get();
        } else if (quality.level() == 2) {
            bonus = Config.GOLD_QUALITY_BONUS.get();
        } else if (quality.level() > 2) {
            bonus = Config.DIAMOND_QUALITY_BONUS.get();
        }
        Double xpWithBonus = xpValue.doubleValue() * bonus;
        return xpWithBonus.longValue();
    }

    public static void checkAndApplyUpgrades(ServerPlayer player, ItemStack stack, ActionType actionType) {
        boolean hasUpgraded = false;
        // Check if it should upgrade from no quality to silver quality.
        if (!QualityUtils.hasQuality(stack) && upgradeSucceeds(player, actionType, UpgradeQuality.IRON)) {
            Quality ironQuality = Quality.getRandom(stack, 1);
            QualityUtils.applyQuality(stack, ironQuality, true);
            hasUpgraded = true;
        }

        // If it has quality, check if it should be bumped up.
        if(QualityUtils.hasQuality(stack)) {
            if(hasUpgraded && !Config.UPGRADES_CAN_CHAIN.get()) return;

            double chainMultiplier = hasUpgraded ? Config.CHAIN_MULTIPLIER.get() : 1.0;
            Quality currentQuality = QualityUtils.getQuality(stack);

            // Check for iron -> gold upgrade
            if(currentQuality.level() == 1 && upgradeSucceeds(player, actionType, UpgradeQuality.GOLD, chainMultiplier)) {
                Quality goldQuality = Quality.getRandom(stack, 2);
                QualityUtils.applyQuality(stack, goldQuality, true);
                hasUpgraded = true;
                currentQuality = goldQuality;
            }

            if(hasUpgraded && !Config.UPGRADES_CAN_CHAIN.get()) return;

            if(chainMultiplier != 1.0) {
                chainMultiplier = chainMultiplier * Config.CHAIN_MULTIPLIER.get();
            } else if (hasUpgraded) {
                chainMultiplier = Config.CHAIN_MULTIPLIER.get();
            }
            // Check for gold -> diamond upgrade
            if(currentQuality.level() == 2 && upgradeSucceeds(player, actionType, UpgradeQuality.DIAMOND, chainMultiplier)) {
                Quality diamondQuality = Quality.getRandom(stack, 3);
                QualityUtils.applyQuality(stack, diamondQuality, true);
            }
        }
    }

    private static boolean upgradeSucceeds(ServerPlayer player, ActionType actionType, UpgradeQuality upgradeQuality, double chainMultiplier) {
        return Math.random() < getChanceToSucceed(player, actionType, upgradeQuality, chainMultiplier);
    }

    private static boolean upgradeSucceeds(ServerPlayer player, ActionType actionType, UpgradeQuality upgradeQuality) {
        return upgradeSucceeds(player, actionType, upgradeQuality, 1.0);
    }


    private static double getChanceToSucceed(ServerPlayer player, ActionType actionType, UpgradeQuality upgradeQuality, double chainMultiplier) {
        Long skillLevel = APIUtils.getLevel(getRelevantSkill(actionType), player);
        double chancePerSkillLevel = getUpgradeChancePerLevel(actionType, upgradeQuality);
        return Math.max(0.0, chancePerSkillLevel * skillLevel.doubleValue() * chainMultiplier);
    }

    private static String getRelevantSkill(ActionType actionType) {
        return switch(actionType) {
            case FARMING -> Config.FARMING_SKILL.get();
            case FISHING -> Config.FISHING_SKILL.get();
            case COOKING -> Config.COOKING_SKILL.get();
        };
    }

    private static double getUpgradeChancePerLevel(ActionType actionType, UpgradeQuality upgradeQuality) {
        return switch(actionType) {
            case FARMING -> switch(upgradeQuality) {
                case IRON -> Config.FARMING_IRON_UPGRADE_CHANCE_PER_LEVEL.get();
                case GOLD -> Config.FARMING_GOLD_UPGRADE_CHANCE_PER_LEVEL.get();
                case DIAMOND -> Config.FARMING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL.get();
            };
            case FISHING -> switch(upgradeQuality) {
                case IRON -> Config.FISHING_IRON_UPGRADE_CHANCE_PER_LEVEL.get();
                case GOLD -> Config.FISHING_GOLD_UPGRADE_CHANCE_PER_LEVEL.get();
                case DIAMOND -> Config.FISHING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL.get();
            };
            case COOKING -> switch(upgradeQuality) {
                case IRON -> Config.COOKING_IRON_UPGRADE_CHANCE_PER_LEVEL.get();
                case GOLD -> Config.COOKING_GOLD_UPGRADE_CHANCE_PER_LEVEL.get();
                case DIAMOND -> Config.COOKING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL.get();
            };
        };
    }

}
