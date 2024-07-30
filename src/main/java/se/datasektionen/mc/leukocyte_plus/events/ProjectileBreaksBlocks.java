package se.datasektionen.mc.leukocyte_plus.events;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface ProjectileBreaksBlocks {

	StimulusEvent<ProjectileBreaksBlocks> EVENT = StimulusEvent.create(ProjectileBreaksBlocks.class, ctx -> (projectile, world) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.shouldBreakBlock(projectile, world);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	ActionResult shouldBreakBlock(ProjectileEntity projectile, World world);

}
