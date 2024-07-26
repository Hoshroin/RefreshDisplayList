package com.hoshroin.clientfixer.redisplaylist.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderGlobal;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.ATIMeminfo;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.FMLLog;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal_reCreateDisplayLists {
	
    @Shadow
	private int glRenderListBase;

    @Inject(method = "loadRenderers", at = @At("HEAD"))
    public void reloadDisplayLists(CallbackInfo ci) {
    	//checking free memory value then log
    	int gpuFreeRenderBufferMem = GL11.glGetInteger(ATIMeminfo.GL_RENDERBUFFER_FREE_MEMORY_ATI);
    	int gpuFreeVBOMem = GL11.glGetInteger(ATIMeminfo.GL_VBO_FREE_MEMORY_ATI);
    	int gpuFreeTextureMem = GL11.glGetInteger(ATIMeminfo.GL_TEXTURE_FREE_MEMORY_ATI);
    	FMLLog.log("RedisplayList", Level.INFO, "Free GPU resource info: RenderBuffer: " + gpuFreeRenderBufferMem + " Texture: " + gpuFreeTextureMem + " VBO: " + gpuFreeVBOMem);
    	
    	Minecraft.getMinecraft().renderGlobal.deleteAllDisplayLists();
        byte b0 = 32 * 2 + 2;
        byte b1 = 16;
        glRenderListBase = GLAllocation.generateDisplayLists(b0 * b0 * b1 * 3);
    }
}