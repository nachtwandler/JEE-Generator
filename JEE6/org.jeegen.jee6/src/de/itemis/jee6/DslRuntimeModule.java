/*
 * generated by Xtext
 */
package de.itemis.jee6;

import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;

import de.itemis.jee6.formatting.Jee6LinkingDiagnosticMessageProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class DslRuntimeModule extends de.itemis.jee6.AbstractDslRuntimeModule
{
	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider()
	{
		return Jee6LinkingDiagnosticMessageProvider.class;
	}
}