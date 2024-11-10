package se.datasektionen.mc.leukocyte_plus.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.datasektionen.mc.leukocyte_plus.events.SpecialEntityDamageEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(ItemFrameEntity.class)
public class MixinItemFrameEntity {

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
