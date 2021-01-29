package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Recurvebow extends ItemBase {

	private final double plusdmg = 10;

	public Recurvebow(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.Dagger;
		down_item[1] = ModItems.Dagger;
		
		phase = 2;
		this.name = "곡궁";
		this.gold = 1000;
		refund_gold = 700;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	0,	0.2,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","기본 공격 시, 추가로 "+String.format("%.0f",plusdmg)+"의 피해를 입힙니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends ItemBase.basic_handler{

		EventClass eventClass;
		PlayerData data;

		private handler(PlayerData data, ItemStack stack){
			super(data,stack);
			this.data = data;
			registerAttackEvent();
		}

		private void registerAttackEvent() {
			this.eventClass = new EventClass(data);
			main.game.getEventHandler().register(this.eventClass);
		}

		@Override
		public void removeHandler() {
			main.game.getEventHandler().unregister(this.eventClass);
		}

		private class EventClass extends PlayerAttackEventHandle {
			PlayerData data;

			public EventClass(PlayerData data) {
				super();
				this.data = data;
			}

			@Override
			public void EventListener(AbstractBaseEvent event) {
				DamageSource source = ((PlayerAttackEvent)event).getSource();
				double damage = source.getDamage();

				EntityData data = source.getAttacker();

				if(data.equals(this.data) && source.getAttackType() == DamageSource.AttackType.ATTACK){
					double aDamage = source.getTarget().getCurrentHealth() - plusdmg > 0 ?
							plusdmg : source.getTarget().getCurrentHealth();
					DamageSource sourcee;
					if(source.isRanged()){
						sourcee = DamageSource.causeUnknownRangedPhysics(source.getAttacker(),source.getTarget(),aDamage);
					}
					else{
						sourcee = DamageSource.causeUnknownMeleePhysics(source.getAttacker(),source.getTarget(),aDamage);
					}
					DamageSource.attackDamage(sourcee,false);
					//source.getTarget().setCurrentHealth(source.getTarget().getCurrentHealth() - aDamage);
					//System.out.println("추가 데미지 : " + aDamage);

				}
			}

			@Override
			public EventPriority getPriority() {
				return EventPriority.NORMAL;
			}

			@Override
			public code getEventCode() {
				return code.attacker;
			}

			@Override
			public EntityData getAttacker() {
				return data;
			}

			@Override
			public EntityData getTarget() {
				return null;
			}
		}
	}
}
