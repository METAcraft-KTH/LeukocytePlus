package nu.metacraft.leukocyte_plus.events;

import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
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

	EventResult grief(WitherBoss wither, @Nullable WitherSkull projectile);

}
