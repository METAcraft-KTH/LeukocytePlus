package se.datasektionen.mc.leukocyte_plus.events;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface ItemUseOnBlockEvent {

	StimulusEvent<ItemUseOnBlockEvent> INCLUDE_FLUIDS = create();
	StimulusEvent<ItemUseOnBlockEvent> EXCLUDE_FLUIDS = create();

	private static StimulusEvent<ItemUseOnBlockEvent> create() {
		return StimulusEvent.create(ItemUseOnBlockEvent.class, ctx -> (player, hand, pos) -> {
			try {
				for (var listener : ctx.getListeners()) {
					var result = listener.interact(player, hand, pos);
					if (result != ActionResult.PASS) {
						return result;
					}
				}
			} catch (Throwable t) {
				ctx.handleException(t);
			}
			return ActionResult.PASS;
		});
	}

	ActionResult interact(ServerPlayerEntity player, Hand hand, BlockPos pos);

}
