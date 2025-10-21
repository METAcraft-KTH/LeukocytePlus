package nu.metacraft.leukocyte_plus.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nu.metacraft.leukocyte_plus.events.TamedWolfAggroEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(WolfEntity.class)
public class MixinWolfEntity {

	@Inject(method = "canAttackWithOwner", at = @At("HEAD"), cancellable = true)
	public void canAttackWithOwner(LivingEntity target, LivingEntity owner, CallbackInfoReturnable<Boolean> cir) {
		var wolf = (WolfEntity) (Object) this;
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
