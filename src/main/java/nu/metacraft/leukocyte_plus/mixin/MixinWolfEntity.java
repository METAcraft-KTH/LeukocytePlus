package nu.metacraft.leukocyte_plus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import nu.metacraft.leukocyte_plus.events.TamedWolfAggroEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(Wolf.class)
public class MixinWolfEntity {

	@Inject(method = "wantsToAttack", at = @At("HEAD"), cancellable = true)
	public void canAttackWithOwner(LivingEntity target, LivingEntity owner, CallbackInfoReturnable<Boolean> cir) {
		var wolf = (Wolf) (Object) this;
		try (var invokers = Stimuli.select().forEntity(wolf)) {
			var result = invokers.get(TamedWolfAggroEvent.EVENT).shouldAggro(wolf, target, owner);
			if (result == EventResult.ALLOW) {
				cir.setReturnValue(true);
			} else if (result == EventResult.DENY) {
				cir.setReturnValue(false);
			}
		}
	}

}
