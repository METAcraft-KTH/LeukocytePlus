package nu.metacraft.leukocyte_plus;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemFrameItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.HitResult;
import nu.metacraft.leukocyte_plus.events.*;
import nu.metacraft.leukocyte_plus.mixin.BucketItemAccessor;
import nu.metacraft.leukocyte_plus.mixin.ItemAccessor;
import nu.metacraft.leukocyte_plus.mixin.ChunkMapAccessor;
import xyz.nucleoid.leukocyte.Leukocyte;
import xyz.nucleoid.leukocyte.authority.IndexedAuthorityMap;
import xyz.nucleoid.leukocyte.rule.ProtectionRule;
import xyz.nucleoid.leukocyte.rule.ProtectionRuleMap;
import xyz.nucleoid.leukocyte.rule.enforcer.ProtectionRuleEnforcer;
import xyz.nucleoid.stimuli.EventSource;
import xyz.nucleoid.stimuli.event.EventRegistrar;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.block.BlockUseEvent;
import xyz.nucleoid.stimuli.event.entity.EntityUseEvent;
import xyz.nucleoid.stimuli.event.projectile.ProjectileHitEvent;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LeukocytePlusEnforcer implements ProtectionRuleEnforcer {

	public static final LeukocytePlusEnforcer INSTANCE = new LeukocytePlusEnforcer();
	private LeukocytePlusEnforcer() {}

	@Override
	public void applyTo(ProtectionRuleMap ruleMap, EventRegistrar events) {
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_EXPLOSION)).applySimple(ExplosionEvents.TNT_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_EXPLOSION)).applySimple(ExplosionEvents.TNT_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_FUSE_EXPLOSION)).applySimple(ExplosionEvents.TNT_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_INSTANT_EXPLOSION)).applySimple(ExplosionEvents.TNT_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_FUSE_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_INSTANT_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.BED_EXPLOSION)).applySimple(ExplosionEvents.BED, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.BED_FIRE)).applySimple(FireEvents.BED, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.RESPAWN_ANCHOR_EXPLOSION)).applySimple(ExplosionEvents.RESPAWN_ANCHOR, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.RESPAWN_ANCHOR_FIRE)).applySimple(FireEvents.RESPAWN_ANCHOR, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.END_CRYSTAL_EXPLOSION)).applySimple(ExplosionEvents.END_CRYSTAL, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.CREEPER_EXPLOSION)).applySimple(ExplosionEvents.CREEPER, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.FIREBALL_EXPLOSION)).applySimple(ExplosionEvents.FIREBALL, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.EXPLOSIVE_FIREBALL_FIRE)).applySimple(FireEvents.EXPLOSIVE_FIREBALL_FIRE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.LIGHTNING_FIRE)).applySimple(FireEvents.LIGHTNING, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.WITHER_GRIEFING)).applySimple(WitherGriefEvent.EVENT, rule -> {
			return (wither, projectile) -> {
				return rule;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ARMOUR_STAND_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(Items.ARMOR_STAND, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ARMOUR_STAND_EDIT).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_EDIT)
		)).applySimple(EntityUseEvent.EVENT, rule -> {
			return (player, entity, hand, hitResult) -> {
				if (entity instanceof ArmorStand) {
					return rule == EventResult.ALLOW ? EventResult.PASS : rule;
				}
				return EventResult.PASS;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ARMOUR_STAND_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking(ArmorStand.class, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(stack -> stack.getItem() instanceof ItemFrameItem, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_EDIT).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_EDIT)
		)).applySimple(EntityUseEvent.EVENT, rule -> {
			return (player, entity, hand, hitResult) -> {
				if (entity instanceof ItemFrame) {
					return rule == EventResult.ALLOW ? EventResult.PASS : rule;
				}
				return EventResult.PASS;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_EDIT).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_EDIT)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking((entity, source) -> {
				return entity instanceof ItemFrame frame && !frame.getItem().isEmpty() && source.getDirectEntity() instanceof ServerPlayer;
			}, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking((entity, source) -> {
				return entity instanceof ItemFrame frame && (frame.getItem().isEmpty() || !(source.getDirectEntity() instanceof ServerPlayer));
			}, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PAINTING_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(Items.PAINTING, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PAINTING_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking(Painting.class, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.END_CRYSTAL_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(Items.END_CRYSTAL, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.END_CRYSTAL_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking(EndCrystal.class, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.WATER_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PLACE)
		)).applySimple(ItemUseOnBlockEvent.EXCLUDE_FLUIDS, rule -> {
			return preventUse((player, hand, pos) -> {
				var stack = player.getItemInHand(hand);
				if (stack.getItem() instanceof BucketItemAccessor bucket && bucket.getContent().isSame(Fluids.WATER)) {
					if (stack.getItem() instanceof MobBucketItem) {
						return !player.level().getBlockState(pos).is(Blocks.WATER);
					}
					return true;
				}
				return false;
			}, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.WATER_PICKUP).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PICKUP)
		)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventFluidPickup(Blocks.WATER, Blocks.WATER_CAULDRON, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.LAVA_PICKUP).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PICKUP)
		)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventFluidPickup(Blocks.LAVA, Blocks.LAVA_CAULDRON, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.LAVA_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PLACE)
		)).applySimple(ItemUseOnBlockEvent.EXCLUDE_FLUIDS, rule -> {
			return preventUse(stack -> stack.getItem() instanceof BucketItemAccessor bucket && bucket.getContent().isSame(Fluids.LAVA), rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PICKUP_FISH)).applySimple(EntityUseEvent.EVENT, rule -> {
			return (player, entity, hand, hitResult) -> {
				if (
						entity instanceof Bucketable &&
						player.getItemInHand(hand).getItem() == Items.WATER_BUCKET &&
						entity.isAlive()
				) {
					if (rule == EventResult.DENY) {
						syncHandStack(player, hand);
						//Re-add the entity since the client will delete it even if we cancel the event.
						restoreEntityForClient(entity, player);
					}
					return rule == EventResult.ALLOW ? EventResult.PASS : rule;
				}
				return EventResult.PASS;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PLACE_FISH)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventUse(stack -> stack.getItem() instanceof MobBucketItem, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PLACE_FIRE)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(stack -> stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE), rule);
		});

		this.forRule(
				events, ruleMap.test(LeukocytePlusRules.PROJECTILES_BREAK_BLOCKS).orElse(
						ruleMap.test(ProtectionRule.BREAK)
				)
		).applySimple(ProjectileBreaksBlocks.EVENT, rule -> {
			return (projectile, world) -> rule;
		});

		//This is just for consistency, vanilla leukocyte does prevent placement of powder snow with the place rule, but not picking it up with a bucket.
		this.forRule(events, ruleMap.test(ProtectionRule.BREAK)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventFluidPickup(Blocks.POWDER_SNOW, Blocks.POWDER_SNOW_CAULDRON, rule);
		});


		this.forRule(events, ruleMap.test(ProtectionRule.PVP)).applySimple(TamedWolfAggroEvent.EVENT, rule -> {
			return (wolf, owner, target) -> target instanceof Player ? rule : EventResult.PASS;
		});
		this.forRule(events, ruleMap.test(ProtectionRule.ATTACK)).applySimple(TamedWolfAggroEvent.EVENT, rule -> {
			return (wolf, owner, target) -> rule;
		});


		this.forRule(events, ruleMap.test(ProtectionRule.PVP)).applySimple(ProjectileHitEvent.ENTITY, rule -> {
			return (projectile, result) -> fixProjectile(projectile, result.getEntity() instanceof Player ? rule : EventResult.PASS);
		});
		this.forRule(events, ruleMap.test(ProtectionRule.ATTACK)).applySimple(ProjectileHitEvent.ENTITY, rule -> {
			return (projectile, result) -> fixProjectile(projectile, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.KEEP_INVENTORY)).applySimple(
				KeepInventoryEvent.EVENT, rule -> player -> rule
		);

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ARMOR_DAMAGE)).applySimple(
				ArmorDamageEvent.EVENT, rule -> (player, source, damage, itemDamage, slot, item) -> rule
		);

	}

	protected void restoreEntityForClients(Entity entity) {
		if (entity.level() instanceof ServerLevel world) {
			var tracker = ((ChunkMapAccessor) world.getChunkSource().chunkMap).getEntityMap().get(
					entity.getId()
			);
			restoreEntityForClients(
					entity,
					((ChunkMapAccessor.TrackedEntity) tracker).getSeenBy().stream().map(
							ServerPlayerConnection::getPlayer
					),
					tracker
			);
		}
	}

	protected void restoreEntityForClients(
			Entity entity, Stream<ServerPlayer> players,
			ChunkMap.TrackedEntity tracker
	) {
		var entry = ((ChunkMapAccessor.TrackedEntity) tracker).getServerEntity();
		players.forEach(player -> player.connection.send(entity.getAddEntityPacket(entry)));
	}

	protected void restoreEntityForClients(Entity entity, ServerLevel world, Stream<ServerPlayer> players) {
		var tracker = ((ChunkMapAccessor) world.getChunkSource().chunkMap).getEntityMap().get(
				entity.getId()
		);
		restoreEntityForClients(entity, players, tracker);
	}

	protected void restoreEntityForClient(Entity entity, ServerPlayer player) {
		restoreEntityForClients(entity, player.level(), Stream.of(player));
	}

	protected EventResult fixProjectile(Projectile projectile, EventResult rule) {
		if (rule == EventResult.DENY && projectile instanceof AbstractArrow) {
			restoreEntityForClients(projectile);
		}
		return rule;
	}

	protected SpecialEntityDamageEvent preventBreaking(BiPredicate<Entity, DamageSource> predicate, EventResult rule) {
		return (entity, source, amount) -> {
			if (predicate.test(entity, source)) {
				if (source.getEntity() instanceof ServerPlayer player) {
					//Allow admins to break the entity.
					var authorities = Leukocyte.get(player.level().getServer()).getAuthorities();
					if (authorities instanceof IndexedAuthorityMap auth) {
						for (var authority : auth.select(player.level().dimension(), SpecialEntityDamageEvent.EVENT)) {
							if (authority.getEventFilter().accepts(EventSource.forEntity(player))) {
								return rule;
							}
						}
						return EventResult.PASS;
					}
				}
				return rule;
			} else {
				return EventResult.PASS;
			}
		};
	}

	protected SpecialEntityDamageEvent preventBreaking(Predicate<Entity> predicate, EventResult rule) {
		return preventBreaking((entity, source) -> predicate.test(entity), rule);
	}

	protected SpecialEntityDamageEvent preventBreaking(Class<? extends Entity> entityClass, EventResult rule) {
		return preventBreaking(entityClass::isInstance, rule);
	}

	protected ItemUseOnBlockEvent preventUse(Item item, EventResult rule) {
		return preventUse(stack -> stack.is(item), rule);
	}

	protected ItemUseOnBlockEvent preventFluidPickup(Block fluid, Block cauldron, EventResult rule) {
		return preventUse((player, hand, pos) -> {
			var handStack = player.getItemInHand(hand);
			if (handStack.is(Items.BUCKET) && rule == EventResult.DENY) {
				var result = ItemAccessor.callGetPlayerPOVHitResult(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
				if (
						result.getType() != HitResult.Type.MISS &&
						(
								player.level().getBlockState(result.getBlockPos()).is(fluid) ||
										player.level().getBlockState(result.getBlockPos()).is(cauldron)
						)
				) {
					if (handStack.getCount() > 1) {
						//Synchronise the slot where the bucket would be placed.
						syncInventorySlot(player, player.getInventory().getFreeSlot());
					} else {
						syncHandStack(player, hand);
					}
					return true;
				}
				return false;
			}
			return false;
		}, rule);
	}

	protected ItemUseOnBlockEvent preventUse(PlayerHandPredicate checker, EventResult rule) {
		return (player, hand, pos) -> {
			if (checker.test(player, hand, pos) && rule == EventResult.DENY) {
				syncHandStack(player, hand);
				return rule.asActionResult();
			}
			return InteractionResult.PASS;
		};
	}

	public interface PlayerHandPredicate {
		boolean test(ServerPlayer player, InteractionHand hand, BlockPos pos);
	}

	protected ItemUseOnBlockEvent preventUse(Predicate<ItemStack> checker, EventResult rule) {
		return preventUse((player, hand, pos) -> checker.test(player.getItemInHand(hand)), rule);
	}

	protected BlockUseEvent preventPlace(PlayerHandPredicate checker, EventResult rule) {
		return (player, hand, hitResult) -> {
			if (checker.test(player, hand, hitResult.getBlockPos())) {
				if (rule == EventResult.DENY) {
					syncHandStack(player, hand);
				}
				return rule == EventResult.ALLOW ? InteractionResult.PASS : rule.asActionResult();
			} else {
				return InteractionResult.PASS;
			}
		};
	}

	protected BlockUseEvent preventPlace(Predicate<ItemStack> checker, EventResult rule) {
		return preventPlace((player, hand, pos) -> checker.test(player.getItemInHand(hand)), rule);
	}

	protected BlockUseEvent preventPlace(Item item, EventResult rule) {
		return preventPlace(stack -> stack.is(item), rule);
	}

	protected void syncInventorySlot(ServerPlayer player, int slot) {
		player.connection.send(
				player.getInventory().createInventoryUpdatePacket(slot)
		);
	}

	protected void syncHandStack(ServerPlayer player, InteractionHand hand) {
		//Synchronise the given hand with the client, since the client will assume the event was not cancelled and use up the item.
		if (hand.equals(InteractionHand.MAIN_HAND)) {
			syncInventorySlot(player, player.getInventory().getSelectedSlot());
		} else {
			syncInventorySlot(player, 40); //TODO Don't hardcode this (maybe merge into METAmods?)
		}
	}
}
