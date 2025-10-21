package nu.metacraft.leukocyte_plus.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nu.metacraft.leukocyte_plus.events.SpecialEntityDamageEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(ArmorStandEntity.class)
public class MixinArmorStandEntity {

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	public void onDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		var entity = (Entity) (Object) this;
		try (var invokers = Stimuli.select().forEntity(entity)) {
			var result = invokers.get(SpecialEntityDamageEvent.EVENT).onDamage(entity, source, amount);
			if (result == EventResult.DENY) {
				cir.setReturnValue(false);
			}
		}
	}

}
