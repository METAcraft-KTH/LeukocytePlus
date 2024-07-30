package se.datasektionen.mc.leukocyte_plus.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ActionResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface TamedWolfAggroEvent {

	StimulusEvent<TamedWolfAggroEvent> EVENT = StimulusEvent.create(TamedWolfAggroEvent.class, ctx -> (wolf, target, owner) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.shouldAggro(wolf, target, owner);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	ActionResult shouldAggro(WolfEntity wolf, LivingEntity target, LivingEntity owner);

}
