/*Copyright © 2018. TIBCO Software Inc. All Rights Reserved.*/

package com.tibco.bw.prometheus.monitor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private final static Logger logger = LoggerFactory.getLogger(Activator.class);
	

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;


		ConfigurationManager config = ConfigurationManager.getInstance(); 

		
		if (config.isPrometheusEnabled()) {
			logger.info("Starting the Prometheus Monitoring Bundle");
			PrometheusCollector.run();
		} else {
			// Unregister service
			logger.info("Prometheus Monitoring Disabled");
			ServiceReference<BWEventHandler> serviceReference = bundleContext.getServiceReference(BWEventHandler.class);
			if (serviceReference != null) {
				context.ungetService(serviceReference);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("Prometheus Monitoring Bundle Stopped.");
		Activator.context = null;
	}

}


