package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;
import nu.metacraft.leukocyte_plus.events.SpecialEntityDamageEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin extends Entity {
	public EndCrystalMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@ModifyExpressionValue(
			method = "hurtServer",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;BLOCK:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
			)
	)
	public Level.ExplosionInteraction replaceSourceType(Level.ExplosionInteraction original, ServerLevel world, DamageSource source) {
		return EventHelper.getSourceType(
				original, ExplosionEvents.END_CRYSTAL, this,
				source.getEntity() instanceof LivingEntity living ? living : null
		);
	}

	@Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
	public void onDamage(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		try (var invokers = Stimuli.select().forEntity(this)) {
			var result = invokers.get(SpecialEntityDamageEvent.EVENT).onDamage(this, source, amount);
			if (result == EventResult.DENY) {
				cir.setReturnValue(false);
			}
		}
	}
}
