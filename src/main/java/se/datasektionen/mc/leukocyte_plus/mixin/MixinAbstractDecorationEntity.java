package se.datasektionen.mc.leukocyte_plus.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.datasektionen.mc.leukocyte_plus.events.SpecialEntityDamageEvent;
import xyz.nucleoid.stimuli.Stimuli;

@Mixin(AbstractDecorationEntity.class)
public class MixinAbstractDecorationEntity {

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if ((Object) this instanceof ItemFrameEntity) {
			return;
		}
		var entity = (Entity) (Object) this;
		try (var invokers = Stimuli.select().forEntity(entity)) {
			var result = invokers.get(SpecialEntityDamageEvent.EVENT).onDamage(entity, source, amount);
			if (result == ActionResult.FAIL) {
				cir.setReturnValue(false);
			}
		}
	}

}
