package jade;

import jade.core.*;
import jade.lang.acl.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Utilities for Message Templates Building.
 * 
 * @author Davide Canton
 */
public final class MTUtils
{
	// method signatures
	private static Map<String, Class> signatures = new HashMap<String, Class>();
	// mapping Constant Name -> Value (referred to ACLMessage constants)
	private static Map<String, Integer> constants = new HashMap<String, Integer>();

	private MTUtils()
	{
	}

	// initialization
	static
	{
		// search for methods starting with Match and with non-zero parameters
		for (Method m : MessageTemplate.class.getDeclaredMethods())
		{
			if (m.getName().startsWith("Match") && m.getParameterTypes().length > 0)
			{
				String x = m.getName().substring(5);
				signatures.put(x, m.getParameterTypes()[0]);
			}
		}
		// searching for all constant int fields
		for (Field f : ACLMessage.class.getDeclaredFields())
		{
			try
			{
				if (f.getType().equals(int.class) && Modifier.isFinal(f.getType().getModifiers()))
				{
					f.setAccessible(true);
					constants.put(f.getName(), (Integer) f.get(null));
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	/**
	 * Returns the value of the constant field in class {@link ACLMessage} with field name
	 * <code>name</code> or <code>null</code> if name not present. Name is case-sensitive.
	 * 
	 * @param name
	 *            The name of the constant.
	 * @return The value associated to the name or null if name not present.
	 */
	public static Integer getValue(String name)
	{
		return constants.get(name);
	}

	/**
	 * Creates a {@link MessageTemplate} from a method named "Match" + <code>name</code> and
	 * parameter value <code>value</code>. <br/>
	 * The method must be declared as:<br/>
	 * <code>public {@link MessageTemplate} MatchXXX(String arg)</code>
	 * 
	 * @param methodName
	 *            The name of the method.
	 * @param value
	 *            The value used as parameter of the method.
	 * @return The {@link MessageTemplate} returned by the method.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static MessageTemplate makeTemplateString(String methodName, String value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		Method m = MessageTemplate.class.getMethod("Match" + methodName, String.class);
		return (MessageTemplate) m.invoke(null, value);
	}

	/**
	 * Creates a {@link MessageTemplate} from a method named "Match" + <code>name</code> and
	 * parameter value <code>value</code>. <br/>
	 * The method must be declared as:<br/>
	 * <code>public {@link MessageTemplate} MatchXXX(AID arg)</code>
	 * 
	 * @param methodName
	 *            The name of the method.
	 * @param value
	 *            The value used as parameter of the method.
	 * @return The {@link MessageTemplate} returned by the method.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static MessageTemplate makeTemplateSingleAID(String methodName, AID aid) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		Method m = MessageTemplate.class.getMethod("Match" + methodName, AID.class);
		return (MessageTemplate) m.invoke(null, aid);
	}

	/**
	 * Creates a {@link MessageTemplate} from a method named "Match" + <code>name</code> and
	 * parameter value <code>value</code>. <br/>
	 * The method must be declared as:<br/>
	 * <code>public {@link MessageTemplate} MatchXXX(AID[] arg)</code>
	 * 
	 * @param methodName
	 *            The name of the method.
	 * @param value
	 *            The value used as parameter of the method.
	 * @return The {@link MessageTemplate} returned by the method.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static MessageTemplate makeTemplateAIDArray(String methodName, AID[] value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		Method m = MessageTemplate.class.getMethod("Match" + methodName, AID[].class);
		return (MessageTemplate) m.invoke(null, new Object[] { value });
	}

	/**
	 * Creates a {@link MessageTemplate} from a method named "Match" + <code>name</code> and
	 * parameter value <code>value</code>. <br/>
	 * The method must be declared as:<br/>
	 * <code>public {@link MessageTemplate} MatchXXX(int arg)</code>
	 * 
	 * @param methodName
	 *            The name of the method.
	 * @param value
	 *            The value used as parameter of the method.
	 * @return The {@link MessageTemplate} returned by the method.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static MessageTemplate makeTemplateInt(String methodName, int value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		Method m = MessageTemplate.class.getMethod("Match" + methodName, int.class);
		return (MessageTemplate) m.invoke(null, value);
	}

	/**
	 * Creates a {@link MessageTemplate} from a method named "Match" + <code>name</code> and
	 * parameter value <code>value</code>. <br/>
	 * The method must be declared as:<br/>
	 * <code>public {@link MessageTemplate} MatchXXX({@link java.util.Date} arg)</code>
	 * 
	 * @param methodName
	 *            The name of the method.
	 * @param value
	 *            The value used as parameter of the method.
	 * @return The {@link MessageTemplate} returned by the method.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static MessageTemplate makeTemplateDate(String methodName, Date value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		Method m = MessageTemplate.class.getMethod("Match" + methodName, Date.class);
		return (MessageTemplate) m.invoke(null, value);
	}

	/**
	 * Returns <code>true</code> only if exists a method named "Match" + <code>name</code> that is
	 * declared as:<br/>
	 * public {@link MessageTemplate} MatchXXX({@link java.util.Date} date)
	 * 
	 * @param name
	 *            The name of the method.
	 * @return <code>true</code> only if a valid method exists.
	 */
	public static boolean needsDate(String name)
	{
		return needs(name, Date.class);
	}

	/**
	 * Returns <code>true</code> only if exists a method named "Match" + <code>name</code> that is
	 * declared as:<br/>
	 * public {@link MessageTemplate} MatchXXX(int arg)
	 * 
	 * @param name
	 *            The name of the method.
	 * @return <code>true</code> only if a valid method exists.
	 */
	public static boolean needsInt(String name)
	{
		return needs(name, int.class);
	}

	/**
	 * Returns <code>true</code> only if exists a method named "Match" + <code>name</code> that is
	 * declared as:<br/>
	 * public {@link MessageTemplate} MatchXXX(AID[] arg)
	 * 
	 * @param name
	 *            The name of the method.
	 * @return <code>true</code> only if a valid method exists.
	 */
	public static boolean needsAIDArray(String name)
	{
		return needs(name, AID[].class);
	}

	/**
	 * Returns <code>true</code> only if exists a method named "Match" + <code>name</code> that is
	 * declared as:<br/>
	 * public {@link MessageTemplate} MatchXXX(AID arg)
	 * 
	 * @param name
	 *            The name of the method.
	 * @return <code>true</code> only if a valid method exists.
	 */
	public static boolean needsSingleAID(String name)
	{
		return needs(name, AID.class);
	}

	// private method used for searching in the map
	private static boolean needs(String name, Class c)
	{
		Class x = signatures.get(name);
		return x != null ? x.equals(c) : false;
	}
}
