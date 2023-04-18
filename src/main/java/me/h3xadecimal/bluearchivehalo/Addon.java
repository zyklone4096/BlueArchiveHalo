package me.h3xadecimal.bluearchivehalo;

import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.ingamegui.enums.EnumModuleAlignment;
import net.labymod.main.LabyMod;
import net.labymod.main.lang.LanguageManager;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.minecraft.block.material.Material;

import java.math.BigDecimal;
import java.util.List;

public class Addon extends LabyModAddon {
    public static Addon INSTANCE;

    public Renderer renderer;

    // Halo Type
    private DropDownMenu<HaloType> typeDropDownMenu;
    private DropDownElement<HaloType> typeDropDownElement;

    // Halo Position
    private SliderElement yOffsetSlider;
    private SliderElement angleSlider;


    @Override
    public void onEnable() {
        INSTANCE = this;

        // Halo types
        typeDropDownMenu = new DropDownMenu<HaloType>("Type", 0, 0, 0, 0).fill(HaloType.values());
        typeDropDownElement = new DropDownElement<HaloType>("Type", typeDropDownMenu);
        typeDropDownMenu.setSelected(HaloType.None);
        typeDropDownElement.setChangeListener(new Consumer<HaloType>() {
            @Override
            public void accept(HaloType haloType) {
                Configuration.type = haloType;
                getConfig().addProperty("type", haloType.name());
            }
        });
        typeDropDownMenu.setEntryDrawer(new DropDownMenu.DropDownEntryDrawer() {
            @Override
            public void draw(Object o, int i, int i1, String s) {
                String entry = o.toString();
                LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), i, i1);
            }
        });



        getApi().registerForgeListener(renderer = new Renderer());
    }

    @Override
    public void loadConfig() {
        try {
//            getConfig().addProperty("yOffset", yOffset);
//            getConfig().addProperty("angle", angle);
            Configuration.type = getConfig().has("type") ? HaloType.valueOf(getConfig().get("type").getAsString()) : HaloType.None;
            Configuration.yOffset = getConfig().has("yOffset") ? getConfig().get("yOffset").getAsInt() : 2;
            Configuration.angle = getConfig().has("angle") ? getConfig().get("angle").getAsInt() : 20;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        typeDropDownMenu.setSelected(Configuration.type);
        getSubSettings().add(typeDropDownElement);

        // Halo Position
        yOffsetSlider = new SliderElement("Y Offset (*10)", this, new ControlElement.IconData(), "yOffset", Configuration.yOffset)
                .setRange(1, 10);
        getSubSettings().add(yOffsetSlider);
        angleSlider = new SliderElement("Angle", this, new ControlElement.IconData(), "angle", Configuration.angle)
                .setRange(20, 90);
        getSubSettings().add(angleSlider);
    }
}
