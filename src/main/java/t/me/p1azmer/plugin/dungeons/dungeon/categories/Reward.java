package t.me.p1azmer.plugin.dungeons.dungeon.categories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import t.me.p1azmer.engine.api.manager.ICleanable;
import t.me.p1azmer.engine.api.placeholder.IPlaceholderMap;
import t.me.p1azmer.engine.api.placeholder.PlaceholderMap;
import t.me.p1azmer.engine.utils.NumberUtil;
import t.me.p1azmer.engine.utils.values.UniInt;
import t.me.p1azmer.plugin.dungeons.Placeholders;
import t.me.p1azmer.plugin.dungeons.api.settings.AbstractSettings;
import t.me.p1azmer.plugin.dungeons.dungeon.editor.reward.DungeonRewardMainEditor;
import t.me.p1azmer.plugin.dungeons.dungeon.impl.Dungeon;

import java.util.ArrayList;
import java.util.List;

public class Reward extends AbstractSettings implements ICleanable, IPlaceholderMap {
    private final String id;

    private double chance;
    private ItemStack item;
    private UniInt amount;
    private List<String> commands;

    private DungeonRewardMainEditor editor;
    private final PlaceholderMap placeholderMap;

    public Reward(@NotNull Dungeon dungeon, @NotNull String id) {
        this(dungeon,
                id,
                25,
                UniInt.of(1, 3),
                new ItemStack(Material.DIAMOND),
                new ArrayList<>()
        );
    }

    public Reward(
            @NotNull Dungeon dungeon,
            @NotNull String id,
            double chance,
            UniInt amount,
            @NotNull ItemStack item,
            @NotNull List<String> commands
    ) {
        super(dungeon);
        this.id = id.toLowerCase();

        this.setChance(chance);

        this.setAmount(amount);

        this.setItem(item);
        this.setCommands(commands);

        this.placeholderMap = new PlaceholderMap()
                .add(Placeholders.REWARD_ID, this::getId)
                .add(Placeholders.REWARD_CHANCE, () -> NumberUtil.format(this.getChance()))
                .add(Placeholders.REWARD_MAX_AMOUNT, () -> String.valueOf(this.getAmount().getMaxValue()))
                .add(Placeholders.REWARD_MIN_AMOUNT, () -> String.valueOf(this.getAmount().getMinValue()))
                .add(Placeholders.REWARD_COMMANDS, () -> String.join("\n", this.getCommands()))
        ;
    }

    @NotNull
    public DungeonRewardMainEditor getEditor() {
        if (this.editor == null) {
            this.editor = new DungeonRewardMainEditor(this);
        }
        return this.editor;
    }

    @Override
    public void clear() {
        if (this.editor != null) {
            this.editor.clear();
            this.editor = null;
        }
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    public double getChance() {
        return this.chance;
    }

    @NotNull
    public UniInt getAmount() {
        return amount;
    }

    @NotNull
    public ItemStack getItem() {
        return new ItemStack(item);
    }

    @NotNull
    public List<String> getCommands() {
        return commands;
    }

    @Override
    @NotNull
    public PlaceholderMap getPlaceholders() {
        return this.placeholderMap;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public void setAmount(@NotNull UniInt amount) {
        this.amount = amount;
    }

    public void setItem(@NotNull ItemStack item) {
        this.item = item;
    }

    public void setCommands(@NotNull List<String> commands) {
        this.commands = new ArrayList<>(commands);
        this.commands.removeIf(String::isEmpty);
    }
}
