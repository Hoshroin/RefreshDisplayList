package com.hoshroin.clientfixer.redisplaylist;

import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {
	@Override
	public void initialize() {
		super.initialize();
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
	}
}
