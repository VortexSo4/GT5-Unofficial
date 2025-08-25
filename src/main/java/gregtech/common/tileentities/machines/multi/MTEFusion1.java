package gregtech.common.tileentities.machines.multi;

import gregtech.api.casing.Casings;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.MultiblockTooltipBuilder;

public class MTEFusion1 extends MTEFusionBase {

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEFusion1(this.mName);
    }

    public MTEFusion1(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEFusion1(String aName) {
        super(aName);
    }

    @Override
    protected Casings mainCasing() {
        return Casings.SterileFarmCasing;
    }

    @Override
    protected Casings coilCasing() {
        return Casings.ThermalContainmentCasing;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Fusion Reactor")
            .addInfo("It's over 9000!!!")
            .addInfo("§b2,048§7 EU/t and §b10M§7 EU capacity per Energy Hatch")
            .addInfo("If the recipe has a startup cost greater than the")
            .addInfo("number of energy hatches * cap, you can't do it")
            .beginStructureBlock(15, 3, 15, false)
            .addController("See diagram when placed")
            .addCasingInfoRange("LuV Machine Casing", 79, 123, false)
            .addStructureInfo("Cover the coils with casing")
            .addOtherStructurePart("Superconducting Coil Block", "Center part of the ring")
            .addEnergyHatch("1-16, Specified casings", 2)
            .addInputHatch("2-16, Specified casings", 1)
            .addOutputHatch("1-16, Specified casings", 3)
            .addStructureInfo("ALL Hatches must be LuV or better")
            .toolTipFinisher();
        return tt;
    }
}
