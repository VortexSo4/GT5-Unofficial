package gregtech.common.tileentities.machines.multi.gui;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.Widget;
import com.cleanroommc.modularui.widgets.TextWidget;

import gregtech.api.metatileentity.implementations.gui.MTEMultiBlockBaseGui;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.tileentities.machines.multi.MTEFusionBase;

public class MTEFusionComputerGUI extends MTEMultiBlockBaseGui {

    public MTEFusionComputerGUI(MTEFusionBase base) {
        super(base);
    }

    private static final int guiWidth = 252;
    private static final int guiHeight = 198;
    private static final int screenBorderSize = 4;

    @Override
    public ModularPanel build(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = new ModularPanel("fusion").size(guiWidth, guiHeight);

        panel.child(backgroundField())
            .child(controllerPicture())
            .child(createText(base.getLocalName(), 0));
        return panel;
    }

    private Widget<?> backgroundField() {
        return GTGuiTextures.BACKGROUND_TEXT_FIELD.asWidget()
            .size(guiWidth - 2 * screenBorderSize, guiHeight - 2 * screenBorderSize)
            .margin(screenBorderSize, screenBorderSize);
    }

    private Widget<?> controllerPicture() {
        return GTGuiTextures.PICTURE_FUSION_COMPUTER_CONTROLLER.asWidget()
            .size(89, 89)
            .center();
    }

    private TextWidget createText(String value, int marginTop) {
        return new TextWidget(IKey.str(value)).margin(screenBorderSize + 4, marginTop + screenBorderSize + 4);
    }
}
