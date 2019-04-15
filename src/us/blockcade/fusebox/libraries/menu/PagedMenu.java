package us.blockcade.fusebox.libraries.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.blockcade.fusebox.libraries.items.ItemStackBuilder;

import java.util.ArrayList;
import java.util.List;

public class PagedMenu {

    private String title;
    private String[] description = new String[] { "Description here." };

    private List<ItemStack> contents = new ArrayList<>();

    public PagedMenu(String title) { this.title = title; }
    public PagedMenu(String title, String[] description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String[] getDescription() { return description; }

    public ItemStack getInfoIcon() {
        return new ItemStackBuilder(Material.SIGN).withName("&aAbout " + getTitle())
                .withLore(description).build();
    }

    public double getPages() {
        return Math.ceil(contents.size() / 21) + 1;
    }

    public List<ItemStack> getItemsOnPage(int page) {
        if (page > getPages()) return new ArrayList<>();

        int index = page - 1;
        System.out.println("[Menu] Sublisting from " + (index * 21) + " to " + ((index * 21) + contents.size() % 21));
        return contents.subList(index * 21, (index * 21) + contents.size() % 21);
    }

    public PagedMenu withContents(List<ItemStack> contents) {
        this.contents = contents;
        return this;
    }

    public PagedMenu withItem(ItemStack item) {
        this.contents.add(item);
        return this;
    }

    public List<InventoryMenu> build() {
        List<InventoryMenu> pages = new ArrayList<>();

        for (int i = 1; i < getPages() + 1; i++) {
            String invTitle = getTitle();
            if (i > 1) invTitle = getTitle() + " (Page " + i + ")";

            System.out.println("[Menu] There are " + getItemsOnPage(1).size() + " item(s) on page " + i + " of " + getPages());

            InventoryMenu menu = new InventoryMenu(invTitle, 6);
            menu.lockColumn(1).lockColumn(9).lockRow(1).lockRow(4).lockRow(5);

            for (ItemStack item : getItemsOnPage(i))
                menu.add(item);

            menu.set(5, 4, getInfoIcon(), true);
            menu.freezeItems(true);

            pages.add(menu);
        }

        return pages;
    }

    public void display(Player player) {
        build().get(0).display(player);
    }

}
