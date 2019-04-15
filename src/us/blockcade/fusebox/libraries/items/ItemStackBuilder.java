package us.blockcade.fusebox.libraries.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import us.blockcade.fusebox.libraries.LibraryUtility;

import java.util.ArrayList;
import java.util.List;

/**
 *  A library full of useful resources.
 *  Each resource comes equip with demonstration plugins
 *  and clear documentation. All rights reserved. 2018.
 *  @author Blockcade Studios
 */
public class ItemStackBuilder extends LibraryUtility {

    /**
     *  LibraryUtility regulation information.
     */
    public String getModuleName() { return "ItemStackBuilder"; }
    public String getModuleVersion() { return "BETA v0.1"; }
    public List<Listener> getHandlers() { return new ArrayList<>(); }

    private final ItemStack ITEM_STACK;
    private boolean durability = false;

    public ItemStackBuilder(Material mat) {
        ITEM_STACK = new ItemStack(mat);
    }

    public ItemStackBuilder(ItemStack item) {
        ITEM_STACK = item;
    }

    public ItemStackBuilder withAmount(int amount) {
        ITEM_STACK.setAmount(amount);
        return this;
    }

    public ItemStackBuilder withName(String name) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withLore(String name) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7" + name));
        meta.setLore(lore);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withLore(String[] name) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        for (String s : name)
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7" + s));

        meta.setLore(lore);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withLore(List<String> name) {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        for (String s : name)
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7" + s));

        meta.setLore(lore);
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withDurability(int durability) {
        ITEM_STACK.setDurability((short) durability);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStack withData(int data) {
        ItemStack item = new ItemStack(ITEM_STACK.getType(), ITEM_STACK.getAmount(), (byte) data);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ITEM_STACK.getItemMeta().getDisplayName());
        meta.setLore(ITEM_STACK.getItemMeta().getLore());
        for (Enchantment e : ITEM_STACK.getItemMeta().getEnchants().keySet()) {
            meta.addEnchant(e, ITEM_STACK.getItemMeta().getEnchants().get(e), true);
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment, final int level) {
        ITEM_STACK.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment) {
        ITEM_STACK.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder withType(Material material) {
        ITEM_STACK.setType(material);
        return this;
    }

    public ItemStackBuilder clearLore() {
        final ItemMeta meta = ITEM_STACK.getItemMeta();
        meta.setLore(new ArrayList<String>());
        ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder clearEnchantments() {
        for (Enchantment enchantment : ITEM_STACK.getEnchantments().keySet()) {
            ITEM_STACK.removeEnchantment(enchantment);
        }
        return this;
    }

    public ItemStackBuilder withColor(Color color) {
        Material type = ITEM_STACK.getType();
        if (type == Material.LEATHER_BOOTS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET
                || type == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) ITEM_STACK.getItemMeta();
            meta.setColor(color);
            ITEM_STACK.setItemMeta(meta);
            return this;
        } else {
            throw new IllegalArgumentException("withColor is only applicable for leather armor!");
        }
    }

    public ItemStackBuilder hideEnchantments(boolean hide) {
        ItemMeta meta = ITEM_STACK.getItemMeta();
        if (hide) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        else meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        ITEM_STACK.setItemMeta(meta);

        return this;
    }

    public ItemStackBuilder withDurability() {
        this.durability = true;
        return this;
    }

    public ItemStack build() {
        if (!durability) {
            ItemMeta meta = ITEM_STACK.getItemMeta();
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            ITEM_STACK.setItemMeta(meta);
        }

        return ITEM_STACK;
    }

}