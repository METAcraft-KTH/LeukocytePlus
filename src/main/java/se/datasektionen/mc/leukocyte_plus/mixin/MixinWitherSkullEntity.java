package se.datasektionen.mc.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import se.datasektionen.mc.leukocyte_plus.EventHelper;

@Mixin(WitherSkullEntity.class)
public abstract class MixinWitherSkullEntity extends ExplosiveProjectileEntity {

	protected MixinWitherSkullEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(
		method = "onCollision",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/World$ExplosionSourceType;MOB:Lnet/minecraft/world/World$ExplosionSourceType;"
		)
	)
	public World.ExplosionSourceType mobTick(World.ExplosionSourceType original) {
		if (getOwner() instanceof WitherEntity wither) {
			return EventHelper.shouldWitherGrief(
					true, wither,  (WitherSkullEntity) (Object) this
			) ? original : World.ExplosionSourceType.NONE;
		}
		return original;
	}

}
