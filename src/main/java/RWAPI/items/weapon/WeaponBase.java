package RWAPI.items.weapon;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

public class WeaponBase extends ItemSword  {
	
	public ClassList ClassCode;
	
	public double attack_speed = 0;
	
	public WeaponBase(ToolMaterial material,String name) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		this.maxStackSize = 1;
		this.setMaxDamage(-1);
		// TODO Auto-generated constructor stub
	}
	
	



	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		// TODO Auto-generated method stub
		return super.hitEntity(stack, target, attacker);
	}
	
	


	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		// TODO Auto-generated method stub
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0.5,0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attack_speed, 0));
        }
        return multimap;
	}
	
	private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double amount) {
		// Get the modifiers for the specified attribute
		final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

		// Find the modifier with the specified ID, if any
		final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		if (modifierOptional.isPresent()) { // If it exists,
			final AttributeModifier modifier = modifierOptional.get();
			modifiers.remove(modifier); // Remove it
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() + amount, modifier.getOperation())); // Add the new modifier
		}
	}

	
	
	/*public WeaponBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		this.maxStackSize = 1;
	}*/
	
	
}
