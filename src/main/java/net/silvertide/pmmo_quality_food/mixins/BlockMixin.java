package net.silvertide.pmmo_quality_food.mixins;

import de.cadentem.quality_food.util.DropData;
import de.cadentem.quality_food.util.QualityUtils;
import de.cadentem.quality_food.util.Utils;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.api.enums.EventType;
import harmonised.pmmo.api.enums.ObjectType;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.util.RegistryUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.LogicalSide;
import net.silvertide.pmmo_quality_food.data.ActionType;
import net.silvertide.pmmo_quality_food.utils.QualityHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(
            method = {"popResource(Lnet/minecraft/world/level/Level;Ljava/util/function/Supplier;Lnet/minecraft/world/item/ItemStack;)V"},
            at = @At("HEAD")
    )
    private static void pmmo_quality_food$upgradeQualityAndAddBonus(Level level, Supplier<ItemEntity> itemEntitySupplier, ItemStack stack, CallbackInfo ci) {
        if (!level.isClientSide && !stack.isEmpty() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !level.restoringBlockSnapshots && Utils.isValidItem(stack)) {
            DropData dropData = (DropData) DropData.current.get();
            if (dropData != null && dropData.player() instanceof ServerPlayer serverPlayer) {
                // Upgrade the quality based on the players farming designated skill
                QualityHelper.checkAndApplyUpgrades(serverPlayer, stack, ActionType.FARMING);

                // Award bonus XP to the player based on the quality
                Map<String, Long> xpAward = new HashMap<>();
                APIUtils.getXpAwardMap(ObjectType.BLOCK, EventType.BLOCK_BREAK, RegistryUtil.getId(dropData.state()), LogicalSide.SERVER, serverPlayer).forEach((skill, value) -> {
                    // Only award xp for an item that has quality on it.
                    if(QualityUtils.hasQuality(stack)) {
                        Long modifiedValue = QualityHelper.applyQualityBonus(stack, value);
                        long difference = modifiedValue - value;
                        if(difference > 0) {
                            xpAward.merge(skill, difference, Long::sum);
                        }
                    }
                });

                Core core = Core.get(level);
                core.awardXP(List.of(serverPlayer), xpAward);
            }
        }
    }
}
