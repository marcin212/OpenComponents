package li.cil.oc.driver.vanilla;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverBlock;
import li.cil.oc.driver.ManagedTileEntityEnvironment;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public final class DriverSign extends DriverBlock {
    DriverSign() {
        super(new ItemStack(Item.sign, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(Block.signPost, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(Block.signWall, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public ManagedEnvironment createEnvironment(final World world, final int x, final int y, final int z) {
        return new Environment((TileEntitySign) world.getBlockTileEntity(x, y, z));
    }

    public static final class Environment extends ManagedTileEntityEnvironment<TileEntitySign> {
        public Environment(final TileEntitySign tileEntity) {
            super(tileEntity, "sign");
        }

        @Callback
        public Object[] getValue(final Context context, final Arguments args) {
            return new Object[]{getSignText()};
        }

        @Callback
        public Object[] setValue(final Context context, final Arguments args) {
            final String value = args.checkString(0);
            final String[] lines = value.split("\n");
            for (int i = 0; i < 4; ++i) {
                if (lines.length <= i || lines[i] == null || lines[i].isEmpty()) {
                    tileEntity.signText[i] = "";
                } else if (lines[i].length() > 15) {
                    tileEntity.signText[i] = lines[i].substring(0, 15);
                } else {
                    tileEntity.signText[i] = lines[i];
                }
            }
            tileEntity.getWorldObj().markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            return new Object[]{getSignText()};
        }

        private String getSignText() {
            final StringBuilder value = new StringBuilder();
            boolean first = true;
            for (String line : tileEntity.signText) {
                if (first) {
                    first = false;
                } else {
                    value.append("\n'");
                }
                value.append(line);
            }
            return value.toString();
        }
    }
}