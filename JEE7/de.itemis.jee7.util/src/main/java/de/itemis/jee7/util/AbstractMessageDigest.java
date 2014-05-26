package de.itemis.jee7.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

abstract public class AbstractMessageDigest
{
	protected enum DIGEST
	{
		MD5("MD5"),
		SHA1("SHA-1"),
		SHA256("SHA-256");

		final private String digest;
		DIGEST(final String digest)
		{
			this.digest = digest;
		}

		@Override
		public String toString()
		{
			return this.digest;
		}
	};

	private MessageDigest crypt;
	
	protected AbstractMessageDigest(final DIGEST digest)
	{
		try
		{
			crypt = MessageDigest.getInstance(digest.toString());
		}
		catch(NoSuchAlgorithmException nsae)
		{
			crypt = null;
		}
	}

	/**
	 * This method computes the hash of a text.
	 *  
	 * @param input The text to hash.
	 * @return The  hash.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String encode(final String input) throws UnsupportedEncodingException
	{
		return encode(input, "ISO8859-1");
	}

	/**
	 * This method computes the SHA1 hash of a text.
	 *  
	 * @param input The text to hash.
	 * @return The hash.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String encode(final String input, final String encoding) throws UnsupportedEncodingException
	{
		return encode(input.getBytes(encoding));
	}

	/**
	 * This method computes the hash of a byte array.
	 * 
	 * @param buffer The byte array to hash.
	 * @return The hash.
	 * @throws NoSuchAlgorithmException
	 */

	synchronized public String encode(final byte[] buffer)
	{
		final Formatter formatter = new Formatter();
		
		crypt.reset();
		crypt.update(buffer);
		for (byte b : crypt.digest())
		{
		    formatter.format("%02x", b);
		}
		final String result = formatter.toString();
		formatter.close();
		return result;
	}
}