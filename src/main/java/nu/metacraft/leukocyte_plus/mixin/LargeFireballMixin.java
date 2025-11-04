package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;
import nu.metacraft.leukocyte_plus.events.FireEvents;

@Mixin(LargeFireball.class)
public abstract class LargeFireballMixin extends Fireball {

	public LargeFireballMixin(EntityType<? extends Fireball> entityType, Level world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(
			method = "onHit",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;MOB:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
			)
	)
	public Level.ExplosionInteraction replaceSourceType(Level.ExplosionInteraction original) {
		return EventHelper.getSourceType(
				original, ExplosionEvents.FIREBALL, this,
				this.getOwner() instanceof LivingEntity living ? living : null
		);
	}

	@ModifyArg(
		method = "onHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
		),
		index = 5
	)
	public boolean setCreateFire(
			boolean createFire
	) {
		return EventHelper.shouldPlaceFire(
				createFire, FireEvents.EXPLOSIVE_FIREBALL_FIRE, this,
				this.getOwner() instanceof LivingEntity living ? living : null
		);
	}

}
