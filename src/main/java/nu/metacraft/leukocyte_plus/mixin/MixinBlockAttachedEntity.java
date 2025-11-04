package nu.metacraft.leukocyte_plus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import nu.metacraft.leukocyte_plus.events.SpecialEntityDamageEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(BlockAttachedEntity.class)
public class MixinBlockAttachedEntity {

	@Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
	public void onDamage(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if ((Object) this instanceof ItemFrame) {
			return;
		}
		var entity = (Entity) (Object) this;
		try (var invokers = Stimuli.select().forEntity(entity)) {
			var result = invokers.get(SpecialEntityDamageEvent.EVENT).onDamage(entity, source, amount);
			if (result == EventResult.DENY) {
				cir.setReturnValue(false);
			}
		}
	}

}
