package se.datasektionen.mc.leukocyte_plus.mixin;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.datasektionen.mc.leukocyte_plus.events.ProjectileBreaksBlocks;
import xyz.nucleoid.stimuli.Stimuli;

@Mixin(ProjectileEntity.class)
public class MixinProjectileEntity {

	@Inject(method = "canBreakBlocks", at = @At("HEAD"), cancellable = true)
	public void canBreakBlocks(World world, CallbackInfoReturnable<Boolean> cir) {
		var projectile = (ProjectileEntity) (Object) this;
		try (var invokers = Stimuli.select().forEntity(projectile)) {
			var result = invokers.get(ProjectileBreaksBlocks.EVENT).shouldBreakBlock(projectile, world);
			if (result.isAccepted()) {
				cir.setReturnValue(true);
			} else if (result != ActionResult.PASS) {
				cir.setReturnValue(false);
			}
		}
	}

}
