package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import nu.metacraft.leukocyte_plus.EventHelper;

@Mixin(WitherSkull.class)
public abstract class MixinWitherSkullEntity extends AbstractHurtingProjectile {

	protected MixinWitherSkullEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(
		method = "onHit",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;MOB:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
		)
	)
	public Level.ExplosionInteraction mobTick(Level.ExplosionInteraction original) {
		if (getOwner() instanceof WitherBoss wither) {
			return EventHelper.shouldWitherGrief(
					true, wither,  (WitherSkull) (Object) this
			) ? original : Level.ExplosionInteraction.NONE;
		}
		return original;
	}

}
