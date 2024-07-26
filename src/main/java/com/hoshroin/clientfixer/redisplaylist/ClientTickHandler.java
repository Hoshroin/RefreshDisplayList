package com.hoshroin.clientfixer.redisplaylist;

import org.lwjgl.opengl.ATIMeminfo;
import org.lwjgl.opengl.GL11;
import org.apache.logging.log4j.Level;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;

@SideOnly(Side.CLIENT)
public class ClientTickHandler {
	
	public int tickCounter = 0;
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		tickCounter = tickCounter + 1;
		
		if (Redisplaylist.cEnableAutoReset && tickCounter >= 100) {
    		int gpuFreeRenderBufferMem = GL11.glGetInteger(ATIMeminfo.GL_RENDERBUFFER_FREE_MEMORY_ATI);
        	int gpuFreeVBOMem = GL11.glGetInteger(ATIMeminfo.GL_VBO_FREE_MEMORY_ATI);
        	int gpuFreeTextureMem = GL11.glGetInteger(ATIMeminfo.GL_TEXTURE_FREE_MEMORY_ATI);
        	
        	if (gpuFreeRenderBufferMem <= Redisplaylist.cMinAutoResetMemLimit || gpuFreeVBOMem <= Redisplaylist.cMinAutoResetMemLimit || gpuFreeTextureMem <= Redisplaylist.cMinAutoResetMemLimit) {
        		FMLLog.log("RedisplayList", Level.INFO, "Called RenderGlobal.loadRenderers()");
        		RenderGlobal rg = new RenderGlobal(Minecraft.getMinecraft());
        		rg.loadRenderers();
        	}
        	
        	tickCounter = 0;
    	}
	}
}
