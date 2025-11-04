package nu.metacraft.leukocyte_plus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.Projectile;
import nu.metacraft.leukocyte_plus.events.ProjectileBreaksBlocks;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(Projectile.class)
public class ProjectileMixin {

	@Inject(method = "mayBreak", at = @At("HEAD"), cancellable = true)
	public void canBreakBlocks(ServerLevel world, CallbackInfoReturnable<Boolean> cir) {
		var projectile = (Projectile) (Object) this;
		try (var invokers = Stimuli.select().forEntity(projectile)) {
			var result = invokers.get(ProjectileBreaksBlocks.EVENT).shouldBreakBlock(projectile, world);
			if (result == EventResult.ALLOW) {
				cir.setReturnValue(true);
			} else if (result == EventResult.DENY) {
				cir.setReturnValue(false);
			}
		}
	}

}
