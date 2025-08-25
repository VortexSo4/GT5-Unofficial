package gregtech.common.tileentities.machines.multi.gui;

import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import gregtech.api.metatileentity.implementations.gui.MTEMultiBlockBaseGui;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.tileentities.machines.multi.MTEFusionBase;

public class MTEFusionComputerGUI extends MTEMultiBlockBaseGui {

    public MTEFusionComputerGUI(MTEFusionBase base) {
        super(base);
    }

    private static final int guiWidth = 252;
    private static final int guiHeight = 198;
    private static final int fusionPictureMarginX = 10;
    private static final int fusionPictureMarginY = 10;

    @Override
    public ModularPanel build(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = new ModularPanel("fusion").size(guiWidth, guiHeight);
        assignFusionChild(panel);
        return panel;
    }

    private IWidget createFusionPicture(UITexture texture, String tooltip) {
        return texture.asWidget()
            .margin(fusionPictureMarginX, fusionPictureMarginY)
            .size(89, 89)
            .tooltip(t -> t.add(tooltip));
    }

    private void assignFusionChild(ModularPanel panel) {
        panel.child(
            GTGuiTextures.BACKGROUND_TEXT_FIELD.asWidget()
                .size(guiWidth - 8, guiHeight - 8)
                .margin(4, 4));
        panel.child(
            createFusionPicture(
                GTGuiTextures.PICTURE_FUSION_COMPUTER_MATERIAL_INJECTOR,
                "Material Injector + coil in between"));
        panel.child(createFusionPicture(GTGuiTextures.PICTURE_FUSION_COMPUTER_PLASMA_EXTRACTOR, "Plasma Extractor"));
        panel.child(createFusionPicture(GTGuiTextures.PICTURE_FUSION_COMPUTER_ENERGY_INJECTOR, "Energy Injector"));
        panel.child(createFusionPicture(GTGuiTextures.PICTURE_FUSION_COMPUTER_HULLS_COIL, "Hulls + coil in between"));
        panel.child(createFusionPicture(GTGuiTextures.PICTURE_FUSION_COMPUTER_CONTROLLER, "Controller"));
        panel.child(createFusionPicture(GTGuiTextures.PICTURE_FUSION_COMPUTER_HULL, "Hull"));
        panel.child(createFusionPicture(GTGuiTextures.PICTURE_FUSION_COMPUTER_YOU, "You :)"));
    }
}
