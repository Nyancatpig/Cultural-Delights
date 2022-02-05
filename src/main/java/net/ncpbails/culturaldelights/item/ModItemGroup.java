package net.ncpbails.culturaldelights.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    //                                                                       mod id
    public static final ItemGroup CULTURAL_GROUP = new ItemGroup("culturalTab")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.AVOCADO.get());
        }
    };

}
