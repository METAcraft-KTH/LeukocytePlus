package nu.metacraft.leukocyte_plus.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface TamedWolfAggroEvent {

	StimulusEvent<TamedWolfAggroEvent> EVENT = StimulusEvent.create(TamedWolfAggroEvent.class, ctx -> (wolf, target, owner) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.shouldAggro(wolf, target, owner);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	EventResult shouldAggro(Wolf wolf, LivingEntity target, LivingEntity owner);

}
