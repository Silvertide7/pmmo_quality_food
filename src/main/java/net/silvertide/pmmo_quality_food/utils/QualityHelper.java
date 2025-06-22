package net.silvertide.pmmo_quality_food.utils;

import de.cadentem.quality_food.core.codecs.Quality;
import de.cadentem.quality_food.util.QualityUtils;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_quality_food.data.UpgradeQuality;
import net.silvertide.pmmo_quality_food.data.UpgradeType;
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

    public static void upgradeQualityFromSkills(ItemStack stack, UpgradeType upgradeType) {

        boolean hasUpgraded = false;
        // Check if it should upgrade from no quality to silver quality.
        if (!QualityUtils.hasQuality(stack)) {

        }

        // If it has quality, check if it should be bumped up.
        if(QualityUtils.hasQuality(stack)) {
            Quality quality = QualityUtils.getQuality(stack);
        }
    }

//    private static double getUpgradeChancePerLevel(UpgradeType upgradeType, UpgradeQuality upgradeQuality) {
//        return switch(upgradeType) {
//            case FARMING -> switch(upgradeQuality) {
//                case IRON -> Config.FARMING_IRON_UPGRADE_CHANCE_PER_LEVEL.get();
//                case GOLD -> Config.FARMING_GOLD_UPGRADE_CHANCE_PER_LEVEL.get();
//                case DIAMOND -> Config.FARMING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL.get();
//            };
//            case FISHING -> switch(upgradeQuality) {
//                case IRON -> Config.FISHING_IRON_UPGRADE_CHANCE_PER_LEVEL.get();
//                case GOLD -> Config.FISHING_GOLD_UPGRADE_CHANCE_PER_LEVEL.get();
//                case DIAMOND -> Config.FISHING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL.get();
//            };
//            case COOKING -> switch(upgradeQuality) {
//                case IRON -> Config.COOKING_IRON_UPGRADE_CHANCE_PER_LEVEL.get();
//                case GOLD -> Config.COOKING_GOLD_UPGRADE_CHANCE_PER_LEVEL.get();
//                case DIAMOND -> Config.COOKING_DIAMOND_UPGRADE_CHANCE_PER_LEVEL.get();
//            };
//        };
//    }


    private static boolean upgradeChanceSucceeds(double chance) {
        return Math.random() < chance;
    }
}
