package nu.metacraft.leukocyte_plus.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface ItemUseOnBlockEvent {

	StimulusEvent<ItemUseOnBlockEvent> INCLUDE_FLUIDS = create();
	StimulusEvent<ItemUseOnBlockEvent> EXCLUDE_FLUIDS = create();

	private static StimulusEvent<ItemUseOnBlockEvent> create() {
		return StimulusEvent.create(ItemUseOnBlockEvent.class, ctx -> (player, hand, pos) -> {
			try {
				for (var listener : ctx.getListeners()) {
					var result = listener.interact(player, hand, pos);
					if (result != InteractionResult.PASS) {
						return result;
					}
				}
			} catch (Throwable t) {
				ctx.handleException(t);
			}
			return InteractionResult.PASS;
		});
	}

	InteractionResult interact(ServerPlayer player, InteractionHand hand, BlockPos pos);

}
