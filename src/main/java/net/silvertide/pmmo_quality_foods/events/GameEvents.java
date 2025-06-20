package net.silvertide.pmmo_quality_foods.events;

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
import net.silvertide.pmmo_quality_foods.PMMOQualityFoods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid= PMMOQualityFoods.MODID, bus=EventBusSubscriber.Bus.GAME)
public class GameEvents {

    // This event is copied from Project MMO's event.
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemFished(ItemFishedEvent event) {
        PMMOQualityFoods.LOGGER.info("My Event");
        Player player = event.getEntity();
        Core core = Core.get(player.level());

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
                core.getExperienceAwards(EventType.FISH, stack, (Player) event.getEntity(), perkOutput).forEach((skill, value) -> {
                    xpAward.merge(skill, value, Long::sum);
                });;
            }
            List<ServerPlayer> partyMembersInRange = PartyUtils.getPartyMembersInRange((ServerPlayer) player);
            core.awardXP(partyMembersInRange, xpAward);
        }
    }
}
