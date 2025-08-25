package gregtech.common.tileentities.machines.multi.gui;

import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import gregtech.api.metatileentity.implementations.gui.MTEMultiBlockBaseGui;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.tileentities.machines.multi.MTEFusionBase;

import java.util.HashMap;
import java.util.Map;

public class MTEFusionComputerGUI extends MTEMultiBlockBaseGui {

    public MTEFusionComputerGUI(MTEFusionBase base) {
        super(base);
    }

    private static final int guiWidth = 252;
    private static final int guiHeight = 198;
    private static final int pixelSize = 6;
    private static final int pixelMapPosX = 10;
    private static final int pixelMapPosY = 10;

    @Override
    public ModularPanel build(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        ModularPanel panel = new ModularPanel("fusion").size(guiWidth, guiHeight);

        panel.child(
            GTGuiTextures.BACKGROUND_TEXT_FIELD.asWidget()
                .size(guiWidth - 8, guiHeight - 8)
                .margin(4, 4)
        );

        //spotless:off
        String[][] map = new String[][] {
            {" "," "," "," "," "," ","O","C","O"," "," "," "," "," "," "},
            {" "," "," "," ","C","C","X","A","X","C","C"," "," "," "," "},
            {" "," "," ","E","A","A","O","~","O","A","A","E"," "," "," "},
            {" "," ","E","A","E","C"," "," "," ","C","E","A","E"," "," "},
            {" ","C","A","E"," "," "," ","Y"," "," "," ","E","A","C"," "},
            {" ","C","A","C"," "," "," "," "," "," "," ","C","A","C"," "},
            {"O","X","O"," "," "," "," "," "," "," "," "," ","O","X","O"},
            {"C","A","C"," "," "," "," "," "," "," "," "," ","C","A","C"},
            {"O","X","O"," "," "," "," "," "," "," "," "," ","O","X","O"},
            {" ","C","A","C"," "," "," "," "," "," "," ","C","A","C"," "},
            {" ","C","A","E"," "," "," "," "," "," "," ","E","A","C"," "},
            {" "," ","E","A","E","C"," "," "," ","C","E","A","E"," "," "},
            {" "," "," ","E","A","A","O","M","O","A","A","E"," "," "," "},
            {" "," "," "," ","C","C","X","A","X","C","C"," "," "," "," "},
            {" "," "," "," "," "," ","O","C","O"," "," "," "," "," "," "}
        };
        //spotless:on

        Map<Character, UITexture> legend = new HashMap<>();
        legend.put('C', GTGuiTextures.PICTURE_FUSION_COMPUTER_HULL);
        legend.put('A', GTGuiTextures.PICTURE_FUSION_COMPUTER_HULLS_COIL);
        legend.put('~', GTGuiTextures.PICTURE_FUSION_COMPUTER_CONTROLLER);
        legend.put('E', GTGuiTextures.PICTURE_FUSION_COMPUTER_ENERGY_INJECTOR);
        legend.put('Y', GTGuiTextures.PICTURE_FUSION_COMPUTER_YOU);
        legend.put('X', GTGuiTextures.PICTURE_FUSION_COMPUTER_MATERIAL_INJECTOR);
        legend.put('O', GTGuiTextures.PICTURE_FUSION_COMPUTER_PLASMA_EXTRACTOR);

        Map<Character, String> tooltips = new HashMap<>();
        tooltips.put('C', "Hull");
        tooltips.put('A', "Hulls + coil");
        tooltips.put('~', "Controller");
        tooltips.put('E', "Energy Injector");
        tooltips.put('Y', "yep, that's you :D");
        tooltips.put('X', "Material Injector");
        tooltips.put('O', "Plasma Extractor");

        drawPixelMap(panel, map, legend, tooltips);

        return panel;
    }

    private void drawPixelMap(ModularPanel panel, String[][] map,
                              Map<Character, UITexture> legend, Map<Character, String> tooltips) {
        int rows = map.length;
        int cols = map[0].length;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                String cell = map[y][x];
                if (cell == null || cell.isEmpty()) continue;

                char c = cell.charAt(0);
                if (!legend.containsKey(c)) continue;

                UITexture texture = legend.get(c);
                String tooltip = tooltips.getOrDefault(c, "");

                panel.child(
                    texture.asWidget()
                        .margin(pixelMapPosX + x * pixelSize, pixelMapPosY + y * pixelSize)
                        .size(pixelSize - 1, pixelSize - 1)
                        .tooltip(t -> t.add(tooltip))
                );
            }
        }
    }
}
