package se.datasektionen.mc.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import se.datasektionen.mc.leukocyte_plus.EventHelper;
import se.datasektionen.mc.leukocyte_plus.events.ExplosionEvents;
import se.datasektionen.mc.leukocyte_plus.events.FireEvents;

@Mixin(FireballEntity.class)
public abstract class MixinFireballEntity extends AbstractFireballEntity {

	public MixinFireballEntity(EntityType<? extends AbstractFireballEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(
			method = "onCollision",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/World$ExplosionSourceType;MOB:Lnet/minecraft/world/World$ExplosionSourceType;"
			)
	)
	public World.ExplosionSourceType replaceSourceType(World.ExplosionSourceType original) {
		return EventHelper.getSourceType(
				original, ExplosionEvents.FIREBALL, this,
				this.getOwner() instanceof LivingEntity living ? living : null
		);
	}

	@ModifyArg(
		method = "onCollision",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"
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
