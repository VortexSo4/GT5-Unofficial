package gregtech.common.tileentities.machines.multi;

import static gregtech.api.enums.HatchElement.Energy;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.HatchElement.Maintenance;
import static gregtech.api.enums.HatchElement.OutputHatch;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_BREWERY;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_BREWERY_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_BREWERY_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_BREWERY_GLOW;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTechAPI;
import gregtech.api.casing.Casings;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.gui.MTEMultiBlockBaseGui;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.common.tileentities.machines.multi.drone.MTEHatchDroneDownLink;
import gregtech.common.tileentities.machines.multi.gui.MTEFusionComputerGUI;

public abstract class MTEFusionBase extends MTEExtendedPowerMultiBlockBase<MTEFusionBase> {

    protected abstract Casings mainCasing();

    protected abstract Casings coilCasing();

    private static final int X_OFFSET = 7;
    private static final int Y_OFFSET = 1;
    private static final int Z_OFFSET = 12;

    public static final String STRUCTURE_PIECE_MAIN = "main";

    private final IStructureDefinition<MTEFusionBase> structureDefinition;

    public MTEFusionBase(String aName) {
        super(aName);
        this.structureDefinition = buildStructureDefinition();
    }

    public MTEFusionBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        this.structureDefinition = buildStructureDefinition();
    }

    private IStructureDefinition<MTEFusionBase> buildStructureDefinition() {
        return StructureDefinition.<MTEFusionBase>builder()
            .addShape(
                STRUCTURE_PIECE_MAIN,
                new String[][] { { "               ", "      OCO      ", "               " },
                    { "      ECE      ", "    CCAAACC    ", "      ECE      " },
                    { "    CC   CC    ", "   EAAOMOAAE   ", "    CC   CC    " },
                    { "   C       C   ", "  EAEC   CEAE  ", "   C       C   " },
                    { "  C         C  ", " CAE       EAC ", "  C         C  " },
                    { "  C         C  ", " CAC       CAC ", "  C         C  " },
                    { " E           E ", "OAO         OAO", " E           E " },
                    { " C           C ", "CAC         CAC", " C           C " },
                    { " E           E ", "OAO         OAO", " E           E " },
                    { "  C         C  ", " CAC       CAC ", "  C         C  " },
                    { "  C         C  ", " CAE       EAC ", "  C         C  " },
                    { "   C       C   ", "  EAEC   CEAE  ", "   C       C   " },
                    { "    CC   CC    ", "   EAAO~OAAE   ", "    CC   CC    " },
                    { "      ECE      ", "    CCAAACC    ", "      ECE      " },
                    { "               ", "      OCO      ", "               " } })
            .addElement('A', coilCasing().asElement())
            .addElement('C', mainCasing().asElement())
            .addElement(
                'I',
                buildHatchAdder(MTEFusionBase.class).atLeast(InputHatch)
                    .casingIndex(mainCasing().textureId)
                    .dot(1)
                    .buildAndChain(mainCasing().asElement()))
            .addElement(
                'E',
                buildHatchAdder(MTEFusionBase.class).atLeast(Energy)
                    .casingIndex(mainCasing().textureId)
                    .dot(2)
                    .buildAndChain(mainCasing().asElement()))
            .addElement(
                'O',
                buildHatchAdder(MTEFusionBase.class).atLeast(OutputHatch)
                    .casingIndex(mainCasing().textureId)
                    .dot(3)
                    .buildAndChain(mainCasing().asElement()))
            .addElement(
                'M',
                buildHatchAdder(MTEFusionBase.class).atLeast(Maintenance.withMteClass(MTEHatchDroneDownLink.class))
                    .casingIndex(mainCasing().textureId)
                    .dot(4)
                    .buildAndChain(mainCasing().asElement()))
            .build();
    }

    @Override
    public IStructureDefinition<MTEFusionBase> getStructureDefinition() {
        return structureDefinition;
    }

    @Override
    public abstract IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);

    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivalBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 1, 2, 0, elementBudget, env, false, true);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        ITexture[] rTexture;
        if (side == aFacing) {
            if (aActive) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings10, 15)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_BREWERY_ACTIVE)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_BREWERY_ACTIVE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings10, 15)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_BREWERY)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_BREWERY_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            }
        } else {
            rTexture = new ITexture[] { Textures.BlockIcons
                .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings10, 15)) };
        }
        return rTexture;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, X_OFFSET, Y_OFFSET, Z_OFFSET);
    }

    @Override
    public boolean forceUseMui2() {
        return true;
    }

    @Override
    protected @NotNull MTEMultiBlockBaseGui getGui() {
        return new MTEFusionComputerGUI(this);
    }

    @Override
    public boolean doesBindPlayerInventory() {
        return false;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        if (!checkPiece(STRUCTURE_PIECE_MAIN, X_OFFSET, Y_OFFSET, Z_OFFSET)) return false;
        if (mMaintenanceHatches.isEmpty()) return false;
        fixAllIssues();
        return true;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic().setSpeedBonus(1F / 1.5F)
            .setMaxParallelSupplier(this::getTrueParallel);
    }

    @Override
    public int getMaxParallelRecipes() {
        return (4 * GTUtility.getTier(this.getMaxInputVoltage()));
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.brewingRecipes;
    }

    @Override
    public boolean supportsVoidProtection() {
        return true;
    }

    @Override
    public boolean supportsBatchMode() {
        return true;
    }

    @Override
    public boolean supportsInputSeparation() {
        return true;
    }

    @Override
    public boolean supportsSingleRecipeLocking() {
        return true;
    }
}
