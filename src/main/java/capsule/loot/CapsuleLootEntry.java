/**
 * 
 */
package capsule.loot;

import java.util.Collection;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import capsule.Config;
import capsule.StructureSaver;
import capsule.items.CapsuleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;

/**
 * @author Lythom
 *
 */		
public class CapsuleLootEntry extends LootEntry {

	private String templatesPath = null;
	private static Random random = new Random();

	/**
	 * 
	 * @param templatesPath
	 * @param weightIn
	 * @param qualityIn
	 * @param conditionsIn
	 * @param entryName
	 */
	protected CapsuleLootEntry(String path, int weightIn, int qualityIn, LootCondition[] conditionsIn, String entryName) {
		super(weightIn, qualityIn, conditionsIn, entryName);
		this.templatesPath  = path;
	}

	public static String[] COLOR_PALETTE = new String[] {
			"0xCCCCCC", "0x549b57", "0xe08822", "0x5e8eb7", "0x6c6c6c", "0xbd5757", "0x99c33d", "0x4a4cba", "0x7b2e89", "0x95d5e7", "0xffffff"
	};

	public static int getRandomColor() {
		return Integer.decode(COLOR_PALETTE[(int) (Math.random() * COLOR_PALETTE.length)]);
	}

	/**
	 * Add all eligible capsules to the list to be picked from.
	 */
	@Override
	public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
		if(this.templatesPath == null) return;
		
		if (LootConditionManager.testAllConditions(this.conditions, rand, context) && Config.lootTemplatesData.containsKey(this.templatesPath))
        {
			
			Pair<String,Template> templatePair = getRandomTemplate(context);

			if (templatePair != null) {
				Template template = templatePair.getRight();
				String templatePath = templatePair.getLeft();
				int size = Math.max(template.getSize().getX(), Math.max(template.getSize().getY(), template.getSize().getZ()));
				String[] path = templatePath.split("/");
				if (path.length == 0)
					return;

				ItemStack capsule = CapsuleItem.createRewardCapsule(
						templatePath,
						getRandomColor(),
						getRandomColor(),
						size,
						path[path.length - 1],
						template.getAuthor());
				
				stacks.add(capsule);
			}

		}

	}

	public Pair<String,Template> getRandomTemplate(LootContext context) {
		LootPathData lpd = Config.lootTemplatesData.get(this.templatesPath);
		if(lpd == null || lpd.files == null) {
			StructureSaver.loadLootList(context.getWorld().getMinecraftServer());
			lpd = Config.lootTemplatesData.get(this.templatesPath);
		}
		if(lpd == null || lpd.files == null || lpd.files.isEmpty()) return null;
		
		int size = lpd.files.size();
		int initRand = random.nextInt(size);
		
		for (int i = 0; i < lpd.files.size(); i++) {
			int ri = (initRand + i) % lpd.files.size();
			String structureName = lpd.files.get(ri);
			Template template = StructureSaver.getTemplateForReward(context.getWorld().getMinecraftServer(), this.templatesPath + "/" + structureName).getRight();
			if(template != null) return Pair.of(this.templatesPath + "/" + structureName, template);
		}
		return null;
	}

	@Override
	protected void serialize(JsonObject json, JsonSerializationContext context) {
		
	}

}
