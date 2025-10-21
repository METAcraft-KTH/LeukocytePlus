package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import nu.metacraft.leukocyte_plus.EventHelper;

@Mixin(WitherEntity.class)
public abstract class MixinWitherEntity extends HostileEntity {

	protected MixinWitherEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(
		method = "mobTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"
		)
	)
	public boolean mobTick(boolean original) {
		return EventHelper.shouldWitherGrief(original, (WitherEntity) (Object) this, null);
	}

	@ModifyExpressionValue(
		method = "mobTick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/World$ExplosionSourceType;MOB:Lnet/minecraft/world/World$ExplosionSourceType;"
		)
	)
	public World.ExplosionSourceType mobTick(World.ExplosionSourceType original) {
		return EventHelper.shouldWitherGrief(
				true, (WitherEntity) (Object) this, null
		) ? original : World.ExplosionSourceType.NONE;
	}

}
