package net.silvertide.pmmo_quality_food.events;

import de.cadentem.quality_food.util.QualityUtils;
import de.cadentem.quality_food.util.Utils;
import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.api.enums.EventType;
import harmonised.pmmo.api.enums.ReqType;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.features.party.PartyUtils;
import harmonised.pmmo.util.Messenger;
import harmonised.pmmo.util.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.silvertide.pmmo_quality_food.PMMOQualityFood;
import net.silvertide.pmmo_quality_food.data.ActionType;
import net.silvertide.pmmo_quality_food.utils.QualityHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid= PMMOQualityFood.MODID, bus=EventBusSubscriber.Bus.GAME)
public class GameEvents {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack craftedStack = event.getCrafting();
        if(event.getEntity() instanceof ServerPlayer serverPlayer && Utils.isValidItem(craftedStack)) {
            // Upgrade quality based on cooking skill
            QualityHelper.checkAndApplyUpgrades(serverPlayer, craftedStack, ActionType.COOKING);

            if(QualityUtils.hasQuality(craftedStack)) {

                Core core = Core.get(serverPlayer.level());
                CompoundTag eventHookOutput = new CompoundTag();
                eventHookOutput = core.getEventTriggerRegistry().executeEventListeners(EventType.CRAFT, event, new CompoundTag());
                //Process perks
                CompoundTag perkOutput = TagUtils.mergeTags(eventHookOutput, core.getPerkRegistry().executePerk(EventType.CRAFT, event.getEntity(), eventHookOutput));

                Map<String, Long> xpAward = new HashMap<>();
                core.getExperienceAwards(EventType.CRAFT, event.getCrafting(), event.getEntity(), perkOutput).forEach((skill, value) -> {
                    Long modifiedValue = QualityHelper.applyQualityBonus(craftedStack, value);
                    long difference = modifiedValue - value;
                    if(difference > 0) {
                        xpAward.merge(skill, difference, Long::sum);
                    }
                });

                if(!xpAward.isEmpty()) {
                    List<ServerPlayer> partyMembersInRange = PartyUtils.getPartyMembersInRange((ServerPlayer) event.getEntity());
                    core.awardXP(partyMembersInRange, xpAward);
                }
            }
        }
    }

    // This event is a slightly modified version of Project MMO's Fish Event Handler.
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemFished(ItemFishedEvent event) {
        if(event.isCanceled()) return;

        Player player = event.getEntity();
        Core core = Core.get(player.level());

        // These next few blocks check if the event should be canceled. We have to recreate this here
        // because we can't know if this or Project MMO's event is going to fire first and we want to cancel
        // it as soon as possible.
        if (!core.isActionPermitted(ReqType.TOOL, player.getMainHandItem(), player)) {
            event.setCanceled(true);
            Messenger.sendDenialMsg(ReqType.TOOL, player, player.getMainHandItem().getDisplayName());
            return;
        }

        boolean serverSide = !player.level().isClientSide;
        CompoundTag eventHookOutput = new CompoundTag();

        if (serverSide) {
            eventHookOutput = core.getEventTriggerRegistry().executeEventListeners(EventType.FISH, event, new CompoundTag());
            if (eventHookOutput.getBoolean(APIUtils.IS_CANCELLED)) {
                event.setCanceled(true);
                return;
            }
        }

        CompoundTag perkOutput = TagUtils.mergeTags(eventHookOutput, core.getPerkRegistry().executePerk(EventType.FISH, player, eventHookOutput));
        if (serverSide) {
            Map<String, Long> xpAward = new HashMap<>();
            for (ItemStack stack : event.getDrops()) {
                // Calculate if the quality should be upgraded and upgrade it if so.
                QualityHelper.checkAndApplyUpgrades((ServerPlayer) player, stack, ActionType.FISHING);

                // Award bonus experience based on the quality.
                core.getExperienceAwards(EventType.FISH, stack, event.getEntity(), perkOutput).forEach((skill, value) -> {
                    // Only award xp for an item that has quality on it.
                    if(QualityUtils.hasQuality(stack)) {
                        Long modifiedValue = QualityHelper.applyQualityBonus(stack, value);
                        long difference = modifiedValue - value;
                        if(difference > 0) {
                            xpAward.merge(skill, difference, Long::sum);
                        }
                    }
                });
            }
            List<ServerPlayer> partyMembersInRange = PartyUtils.getPartyMembersInRange((ServerPlayer) player);
            core.awardXP(partyMembersInRange, xpAward);
        }
    }
}
