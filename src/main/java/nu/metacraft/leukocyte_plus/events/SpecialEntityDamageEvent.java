package nu.metacraft.leukocyte_plus.events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface SpecialEntityDamageEvent {

	StimulusEvent<SpecialEntityDamageEvent> EVENT = StimulusEvent.create(SpecialEntityDamageEvent.class, ctx -> (entity, source, amount) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.onDamage(entity, source, amount);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	EventResult onDamage(Entity entity, DamageSource source, float amount);

}
