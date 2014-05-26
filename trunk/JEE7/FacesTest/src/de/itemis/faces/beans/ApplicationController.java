/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.naming.directory.DirContext;

import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.dao.InfoDaoBean;
import de.itemis.jee7.util.LogUtil;

@ManagedBean(eager=true)
@ApplicationScoped
public class ApplicationController extends AbstractApplicationController
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@EJB
	private AdminDaoBean dao;

	@EJB
	private InfoDaoBean info;

	@EJB
	private MultipleBean multiple;

	@PostConstruct
	@Override
	public void init()
	{
		log.log(Level.FINE, ">init()");
		try
		{
			final DirContext ldap = info.getLdapItemis();
			final String     ns   = ldap.getNameInNamespace();

			log.log(Level.FINE, ldap.toString());
			LogUtil.debug(log, " url        = %s", dao.getLdapUrl());
			LogUtil.debug(log, " baseDN     = %s", dao.getLdapBaseDN());
			LogUtil.debug(log, " productive = %s", info.isProductive());
			LogUtil.debug(log, " namespace  = %s", ns);

			super.init();
			multiple.xaAccess();
			multiple.checkSsl();
			log.info("\n" + LogUtil.banner("de.itemis.faces.version", "Faces Test Application"));
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		log.log(Level.FINE, "<init()");
	}
}