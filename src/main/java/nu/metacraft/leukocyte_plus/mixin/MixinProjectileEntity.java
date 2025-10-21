package nu.metacraft.leukocyte_plus.mixin;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nu.metacraft.leukocyte_plus.events.ProjectileBreaksBlocks;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(ProjectileEntity.class)
public class MixinProjectileEntity {

	@Inject(method = "canBreakBlocks", at = @At("HEAD"), cancellable = true)
	public void canBreakBlocks(ServerWorld world, CallbackInfoReturnable<Boolean> cir) {
		var projectile = (ProjectileEntity) (Object) this;
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
