package me.h3xadecimal.bluearchivehalo;

import com.google.gson.Gson;
import net.labymod.main.LabyModForge;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

@Mod(modid = Forge.MODID, version = Forge.VERSION)
public class Forge
{
    public static final boolean DEBUG = false;
    public static Forge INSTANCE;
    public static final String MODID = "halo";
    public static final String VERSION = "1.0";
    public static  File configFile;

    public Forge() {
        INSTANCE = this;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        configFile = new File(Minecraft.getMinecraft().mcDataDir, "config" + File.separator + "halo.json");
        loadConfig();
        MinecraftForge.EVENT_BUS.register(new Renderer());
    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new ForgeCommand());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void loadConfig() throws IOException {
        if (!configFile.exists()) {
            configFile.createNewFile();
            saveConfig();
            return;
        }
        HashMap<String, Object> configMap = new HashMap<String, Object>();
        configMap = new Gson().fromJson(
                new String(IOUtils.toByteArray(new FileInputStream(configFile))),
                configMap.getClass()
        );
        try {
            if (configMap.containsKey("type")) Configuration.type = HaloType.valueOf((String) configMap.get("type"));
        }catch (Throwable t) {
            t.printStackTrace();
        }
        try {
            if (configMap.containsKey("yOffset")) Configuration.yOffset = new BigDecimal((Double) configMap.get("yOffset")).intValue();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        try {
            if (configMap.containsKey("angle")) Configuration.angle = new BigDecimal((Double) configMap.get("angle")).intValue();
        }catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void saveConfig() throws IOException {
        if (!configFile.exists()) configFile.createNewFile();
        HashMap<String, Object> configMap = new HashMap<String, Object>();
        configMap.put("type", Configuration.type.name());
        configMap.put("angle", Configuration.angle);
        configMap.put("yOffset", Configuration.yOffset);

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(configFile));
        bos.write(new Gson().toJson(configMap).getBytes());
        bos.close();
    }
}
