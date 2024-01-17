package se.datasektionen.mc.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.datasektionen.mc.leukocyte_plus.EventHelper;
import se.datasektionen.mc.leukocyte_plus.events.ExplosionEvents;
import se.datasektionen.mc.leukocyte_plus.events.SpecialEntityDamageEvent;
import xyz.nucleoid.stimuli.Stimuli;

@Mixin(EndCrystalEntity.class)
public abstract class MixinEndCrystalEntity extends Entity {
	public MixinEndCrystalEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyExpressionValue(
			method = "damage",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/World$ExplosionSourceType;BLOCK:Lnet/minecraft/world/World$ExplosionSourceType;"
			)
	)
	public World.ExplosionSourceType replaceSourceType(World.ExplosionSourceType original, DamageSource source) {
		return EventHelper.getSourceType(
				original, ExplosionEvents.END_CRYSTAL, this,
				source.getAttacker() instanceof LivingEntity living ? living : null
		);
	}

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		try (var invokers = Stimuli.select().forEntity(this)) {
			var result = invokers.get(SpecialEntityDamageEvent.EVENT).onDamage(this, source, amount);
			if (result == ActionResult.FAIL) {
				cir.setReturnValue(false);
			}
		}
	}
}
