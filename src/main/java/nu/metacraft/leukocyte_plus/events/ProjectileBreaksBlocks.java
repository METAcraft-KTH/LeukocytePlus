package nu.metacraft.leukocyte_plus.events;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface ProjectileBreaksBlocks {

	StimulusEvent<ProjectileBreaksBlocks> EVENT = StimulusEvent.create(ProjectileBreaksBlocks.class, ctx -> (projectile, world) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.shouldBreakBlock(projectile, world);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	EventResult shouldBreakBlock(Projectile projectile, Level world);

}
