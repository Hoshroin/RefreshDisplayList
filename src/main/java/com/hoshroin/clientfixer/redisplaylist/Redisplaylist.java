package com.hoshroin.clientfixer.redisplaylist;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

@Mod(modid = Redisplaylist.MODID, version = Redisplaylist.VERSION)
public class Redisplaylist
{
    public static final String MODID = "redisplaylist";
    public static final String VERSION = "@VERSION@";
    
    public static Configuration config;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
        
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.initialize();
        
        FMLLog.log("RedisplayList", Level.INFO, "Read configs: cMinAutoResetMemLimit: " + cMinAutoResetMemLimit + " cEnableAutoReset: " + Boolean.toString(cEnableAutoReset));
    }
    
    public static boolean cEnableAutoReset = true;
    public static int cMinAutoResetMemLimit = 620000;
    
    public static void syncConfig() {
    	try {
    		config.load();
    		
    		Property cEnableAutoResetProp = config.get(Configuration.CATEGORY_GENERAL, "cEnableAutoReset", "true", "Enable the auto reset feature");
    		Property cMinAutoResetMemLimitProp = config.get(Configuration.CATEGORY_GENERAL, "cMinAutoResetMemLimit", "620000", "The minimal value of free GPU memory for the auto reset to trigger");
    		cEnableAutoReset = cEnableAutoResetProp.getBoolean();
    		cMinAutoResetMemLimit = cMinAutoResetMemLimitProp.getInt();
    	} catch (Exception e) {
    		
    	} finally {
    		if (config.hasChanged()) {
    			config.save();
    		}
    	}
    }
}