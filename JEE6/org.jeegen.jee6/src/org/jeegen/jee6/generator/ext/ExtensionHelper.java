package org.jeegen.jee6.generator.ext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.xtext.EcoreUtil2;

import org.jeegen.jee6.jee6.Entity;
import org.jeegen.jee6.jee6.EntityRef;
import org.jeegen.jee6.jee6.History;

public class ExtensionHelper
{
	public static String asType(final String jndiName)
	{
		final String [] elements = jndiName.split("/");
		final StringBuffer buffer = new StringBuffer(elements[0]);
		
		for (int i = 1;i < elements.length;i++)
		{
			final char [] array = elements[i].toCharArray();
			array[0] = Character.toUpperCase(array[0]);

			buffer.append(array);
		}
		return buffer.toString();
	}

	private static void add(final Set<Entity> set, final Entity entity)
	{
		final List<EntityRef> entities  = EcoreUtil2.typeSelect(entity.getTypes(), EntityRef.class);
		final List<History> histories = EcoreUtil2.typeSelect(entity.getTypes(), History.class);

		set.add(entity);

		for (EntityRef ref : entities)
		{
			final Entity type = ref.getType();

			if (ref.isMany() && (entity != type))
			{
				add(set, type);
			}
		}
		for (History history : histories)
		{
			add(set, history.getType());
		}
	}

	/**
	 * This method collects all entities and 1:n sub entities of one process into a {@link Set}.
	 * 
	 * @param process The process from which the {@link Entity} beans are collected.
	 * @return A {@link Set} of {@link Entity} beans.
	 */
	public static Set<Entity> getManyEntities(final List<Entity> entities)
	{
		final Set<Entity> set = new HashSet<Entity>();

		for (Entity entity : entities)
		{
			add(set, entity);
		}
		return set;
	}

	private static void addUiEntities(final Set<Entity> set, final Entity entity)
	{
		final List<EntityRef> entities  = EcoreUtil2.typeSelect(entity.getTypes(), EntityRef.class);
		final List<History> histories = EcoreUtil2.typeSelect(entity.getTypes(), History.class);

		for (EntityRef ref : entities)
		{
			if (ref.isMany())
			{
				final Entity type = ref.getType();

				if (entity.getClass() != type.getClass())
				{
					addUiEntities(set, type);
				}
				set.add(entity);
			}
		}
		for (History history : histories)
		{
			addUiEntities(set, history.getType());
			set.add(entity);
		}
	}

	/**
	 * This method collects all entities and 1:n sub entities of one process into a {@link Set}.
	 * 
	 * @param process The process from which the {@link Entity} beans are collected.
	 * @return A {@link Set} of {@link Entity} beans.
	 */
	public static Set<Entity> getUiEntities(final org.jeegen.jee6.jee6.Process process)
	{
		final Set<Entity> set = new HashSet<Entity>();

		for (Entity entity : process.getEntities())
		{
			set.add(entity);
			addUiEntities(set, entity);
		}
		return set;
	}
}
