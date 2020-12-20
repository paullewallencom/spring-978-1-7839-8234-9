package com.springessentialsbook.chapter1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BannerService {

	Logger logger = LoggerFactory.getLogger(BannerService.class);

	public void displayBanner() {
		logger.info("=========================================");
		logger.info("======= Welcome to Spring World ! =======");
		logger.info("=========================================");

	}

}
