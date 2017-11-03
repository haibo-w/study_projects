/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package com.test.dukeetf;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

/* Updates price and volume information every second */

public class PriceVolumeBean {
    /* Use the container's timer service */
    private Random random;
    private DukeETFServlet servlet;
    private volatile double price = 100.0;
    private volatile int volume = 300000;
    private static final Logger logger = Logger.getLogger("PriceVolumeBean");
    
    
    
    public void registerServlet(DukeETFServlet servlet) {
        /* Associate a servlet to send updates to */
        this.servlet = servlet;
        random = new Random();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				timeout();
			}
		}, 0,1000);
    }
    

    public void timeout() {
        /* Adjust price and volume and send updates */
        price += 1.0*(random.nextInt(100)-50)/100.0;
        volume += random.nextInt(5000) - 2500;
        if (servlet != null)
        	System.out.println(" time out is invoke ");
            servlet.send(price, volume);
    }
}

