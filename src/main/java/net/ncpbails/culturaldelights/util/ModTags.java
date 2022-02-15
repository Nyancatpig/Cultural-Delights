package net.ncpbails.culturaldelights.util;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.ncpbails.culturaldelights.CulturalDelights;

public class ModTags {

    public static class Items {

        public static final Tags.IOptionalNamedTag<Item> BOWL_FOODS = createForgeTag("rollmatout/bowl_foods");
        //public static final Tags.IOptionalNamedTag<Item> BOTTLE_FOODS = createForgeTag("rollmatout/bottle_foods");
        //public static final Tags.IOptionalNamedTag<Item> BUCKET_FOODS = createForgeTag("rollmatout/bucket_foods");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(CulturalDelights.MOD_ID, name));
        }
        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}
