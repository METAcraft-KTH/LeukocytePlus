package nu.metacraft.leukocyte_plus.events;

import net.minecraft.world.entity.player.Player;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface KeepInventoryEvent {

	StimulusEvent<KeepInventoryEvent> EVENT = StimulusEvent.create(KeepInventoryEvent.class, ctx -> (player) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.keepItem(player);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	EventResult keepItem(Player player);


	static Boolean checkKeepInventory(Player player, Boolean original) {
		try (var invokers = Stimuli.select().forEntity(player)) {
			var result = invokers.get(KeepInventoryEvent.EVENT).keepItem(player);
			return switch (result) {
				case DENY -> false;
				case ALLOW -> true;
				case PASS -> original;
			};
		}
	}
}
