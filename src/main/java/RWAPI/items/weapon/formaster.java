package RWAPI.items.weapon;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.PlayerData;
import RWAPI.init.ModWeapons;
import RWAPI.main;
import RWAPI.util.ClassList;
import RWAPI.util.EntityStatus;
import RWAPI.util.GameStatus;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class formaster extends WeaponBase{

    public formaster(String name) {
        super(ToolMaterial.DIAMOND,name);
        this.ClassCode = ClassList.MasterYi; //임시
        ModWeapons.weapon.add(this);
        basename = "기공사";
        this.attack_speed = -200;
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        //System.out.println("swing");
        return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerin, EnumHand handIn){
        if(main.game.start == GameStatus.START){
            PlayerData data = main.game.getPlayerData(playerin.getUniqueID());
            if(data.getStatus() == EntityStatus.ALIVE){
                ForceMaster forceMaster = (ForceMaster) data.get_class();

                forceMaster.getAllskill(5).skillExecute(playerin);
            }
        }
        return super.onItemRightClick(worldIn, playerin, handIn);
    }
}
