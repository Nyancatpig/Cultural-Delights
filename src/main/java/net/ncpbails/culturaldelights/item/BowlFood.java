package net.ncpbails.culturaldelights.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class BowlFood extends Item {

    public BowlFood(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemstack, World world, LivingEntity entity) {
        ItemStack retval = new ItemStack(Items.BOWL);
        super.onItemUseFinish(itemstack, world, entity);
        if (itemstack.isEmpty()) {
            return retval;
        } else {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (!player.isCreative() && !player.inventory.addItemStackToInventory(retval))
                    player.dropItem(retval, false);
            }
            return itemstack;
        }
    }
}
