
package org.jeegen.jee7;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class DslStandaloneSetup extends DslStandaloneSetupGenerated{

	public static void doSetup() {
		new DslStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
