package li.cil.occ.mods.ic2;

import li.cil.oc.api.Driver;
import li.cil.occ.mods.IMod;

public final class ModIndustrialCraft2 implements IMod {
    @Override
    public String getModId() {
        return "IC2";
    }

    @Override
    public void initialize() {
        Driver.add(new DriverEnergyConductor());
        Driver.add(new DriverEnergySink());
        Driver.add(new DriverEnergySource());
        Driver.add(new DriverEnergyStorage());
        Driver.add(new DriverMassFab());
        Driver.add(new DriverReactor());
        Driver.add(new DriverReactorChamber());

        Driver.add(new ConverterElectricItem());
    }
}
