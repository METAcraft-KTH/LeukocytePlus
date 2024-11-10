package se.datasektionen.mc.leukocyte_plus.events;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface WitherGriefEvent {

	StimulusEvent<WitherGriefEvent> EVENT = StimulusEvent.create(WitherGriefEvent.class, ctx -> (wither, projectile) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.grief(wither, projectile);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	EventResult grief(WitherEntity wither, @Nullable WitherSkullEntity projectile);

}
