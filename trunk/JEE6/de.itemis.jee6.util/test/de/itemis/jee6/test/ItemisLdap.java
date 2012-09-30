package de.itemis.jee6.test;

import javax.naming.NamingException;

import de.itemis.jee6.util.AbstractLdapConnector;

class ItemisLdap extends AbstractLdapConnector {
	ItemisLdap() throws NamingException
	{
		super("ldaps://master.itemis.de:636/", "dc=itemis,dc=de", null, null);
	}

	@Override
	protected String getUserDn()
	{
		return "ou=users";
	}

	@Override
	protected String getGroupDn()
	{
		return "ou=groups";
	}

}
