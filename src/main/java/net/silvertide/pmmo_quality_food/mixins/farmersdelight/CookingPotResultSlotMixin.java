package net.silvertide.pmmo_quality_food.mixins.farmersdelight;

import de.cadentem.quality_food.util.Utils;
import harmonised.pmmo.api.enums.EventType;
import harmonised.pmmo.core.Core;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.silvertide.pmmo_quality_food.data.ActionType;
import net.silvertide.pmmo_quality_food.utils.QualityHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotResultSlot;

import java.util.Map;

@Mixin({CookingPotResultSlot.class})
public abstract class CookingPotResultSlotMixin {
    @Shadow(remap = false)
    @Final
    private Player player;

    public CookingPotResultSlotMixin() {}

    @Inject(
            method = {"checkTakeAchievements"},
            at = {@At("RETURN")},
            order = 1100
    )
    private void pmmo_quality_food$upgradeAndGrantXp(ItemStack stack, CallbackInfo callback) {
        if (this.player instanceof ServerPlayer serverPlayer) {
            if(Utils.isValidItem(stack)) {
                //Check and upgrade stack
                QualityHelper.checkAndApplyUpgrades(serverPlayer, stack, ActionType.COOKING);

                // Calculate bonus xp awarded
                Core core = Core.get(serverPlayer.level());
                Map<String, Long> xpAward = core.getExperienceAwards(EventType.SMELT, stack, serverPlayer, new CompoundTag());
                QualityHelper.grantXpDifference(core, serverPlayer, stack, xpAward);
            }
        }
    }
}
