/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */
package io.codecastle.scriptorium.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import io.codecastle.scriptorium.json.scribe.JsonScribe;

/**
 * Represents a JSON key currently being output within a containing {@link JsonObject}.
 * 
 * @author Doug Valenta
 * @param <P> the type of the containing {@link JsonObject}
 * @see JsonObject#key()
 */
public final class JsonKey<P> extends AbstractJsonAppendable<JsonKey<P>> {

	private final P parent;
	
	JsonKey(final JsonScribe scribe, final P parent) {
		super(scribe);
		this.parent = parent;
	}

	/**
	 * Closes this JSON key, then begins a new string literal value assigned to it
	 * and returns a {@link JsonValue} that can be used to assign characters to it.
	 * 
	 * <p>
	 * Calling the {@link JsonValue#then()} method of the returned JsonValue will
	 * return the {@link JsonObject} that contains this key.
	 * 
	 * <p>
	 * Calling any method of this object's parent before closing the returned JsonValue may 
	 * result in invalid output and/or an unchecked exception.
	 * 
	 * <p>
	 * When this method returns, a closing quote, colon, and opening quote
	 * will have already been appended to the underlying {@link Appendable}.
	 * 
	 * @return a JsonValue representing a new string literal value assigned to this key
	 * @throws IOException if an I/O error occurs
	 * @see JsonValue
	 * @see #value(char)
	 * @see #value(Character)
	 * @see #value(CharSequence)
	 */
	public JsonValue<P> value() throws IOException {
		scribe.pop().pushValue();
		return new JsonValue(scribe, parent);
	}
	
	/**
	 * Closes this JSON key, then begins a new string literal value assigned to it,
	 * beginning with the provided character,
	 * and returns a {@link JsonValue} that can be used to assign characters to it.
	 * 
	 * <p>
	 * Calling the {@link JsonValue#then()} method of the returned JsonValue will
	 * return the {@link JsonObject} that contains this key.
	 * 
	 * <p>
	 * Calling any method of this object's parent before closing the returned JsonValue may 
	 * result in invalid output and/or an unchecked exception.
	 * 
	 * <p>
	 * The provided character will be escaped.
	 * 
	 * <p>
	 * When this method returns, a closing quote, colon, opening quote, and the provided character
	 * will have already been appended to the underlying {@link Appendable}.
	 * 
	 * @param value the first character of the new string literal value
	 * @return a JsonValue representing a new string literal value assigned to this key
	 * @throws IOException if an I/O error occurs
	 * @see JsonValue
	 * @see #value()
	 * @see #value(char)
	 * @see #value(CharSequence)
	 */
	public JsonValue<P> value(final char value) throws IOException {
		scribe.pop().pushValue().append(value);
		return new JsonValue(scribe, parent);
	}
	
	/**
	 * Closes this JSON key, then begins a new string literal value assigned to it,
	 * beginning with the provided {@link Character},
	 * and returns a {@link JsonValue} that can be used to assign characters to it.
	 * 
	 * <p>
	 * Calling the {@link JsonValue#then()} method of the returned JsonValue will
	 * return the {@link JsonObject} that contains this key.
	 * 
	 * <p>
	 * Calling any method of this object's parent before closing the returned JsonValue may 
	 * result in invalid output and/or an unchecked exception.
	 * 
	 * <p>
	 * The provided character will be escaped.
	 * 
	 * <p>
	 * If the provided character is null, nothing will be prepended to the value.
	 * I.e., when this method returns only a closing quote, colon, and opening quote
	 * will be appended to the underlying {@link Appendable}
	 * 
	 * @param value the first character of the new string literal value
	 * @return a JsonValue representing a new string literal value assigned to this key
	 * @throws IOException if an I/O error occurs
	 * @see JsonValue
	 * @see #value()
	 * @see #value(char)
	 * @see #value(CharSequence)
	 */
	public JsonValue<P> value(final Character value) throws IOException {
		if (value == null) return value();
		return value((char) value);
	}
	
	/**
	 * Closes this JSON key, then begins a new string literal value assigned to it,
	 * beginning with the provided {@link CharSequence},
	 * and returns a {@link JsonValue} that can be used to assign characters to it.
	 * 
	 * <p>
	 * Calling the {@link JsonValue#then()} method of the returned JsonValue will
	 * return the {@link JsonObject} that contains this key.
	 * 
	 * <p>
	 * Calling any method of this object's parent before closing the returned JsonValue may 
	 * result in invalid output and/or an unchecked exception.
	 * 
	 * <p>
	 * The contents of the provided CharSequence will be escaped.
	 * 
	 * <p>
	 * If the provided CharSequence is null, nothing will be prepended to the value.
	 * I.e., when this method returns only a closing quote, colon, and opening quote
	 * will be appended to the underlying {@link Appendable}
	 * 
	 * @param value the beginning characters of the new string literal value
	 * @return a JsonValue representing a new string literal value assigned to this key
	 * @throws IOException if an I/O error occurs
	 * @see JsonValue
	 * @see #value()
	 * @see #value(char)
	 * @see #value(Character)
	 */
	public JsonValue<P> value(final CharSequence value) throws IOException {
		scribe.pop().pushValue();
		if (value != null) scribe.append(value);
		return new JsonValue(scribe, parent);
	}
	
	/**
	 * Assigns this key a {@code null} literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P thenNull() throws IOException {
		scribe.pop().nullValue();
		return parent;
	}
	
	/**
	 * Assigns this key a {@code true} literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P thenTrue() throws IOException {
		scribe.pop().trueValue();
		return parent;
	}
	
	/**
	 * Assigns this key a {@code false} literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P thenFalse() throws IOException {
		scribe.pop().falseValue();
		return parent;
	}
	
	/**
	 * Assigns this key an empty JSON array value and returns the containing 
	 * {@link JsonObject}.
	 * 
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P thenEmptyArray() throws IOException {
		scribe.pop().emptyArray();
		return parent;
	}
	
	/**
	 * Assigns this key an empty JSON object value and returns the containing 
	 * {@link JsonObject}.
	 * 
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P thenEmptyObject() throws IOException {
		scribe.pop().emptyObject();
		return parent;
	}
	
	/**
	 * Assigns this key a string literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * The contents of the provided {@link CharSequence} will be quoted and escaped.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final CharSequence value) throws IOException {
		if (value == null) return thenNull();
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a single-character string literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * The provided character will be quoted and escaped.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final char value) throws IOException {
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a single-character string literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * The provided character will be quoted and escaped.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Character value) throws IOException {
		if (value == null) return thenNull();
		return then((char) value);
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final BigInteger value) throws IOException {
		if (value == null) return thenNull();
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final BigDecimal value) throws IOException {
		if (value == null) return thenNull();
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Byte value) throws IOException {
		if (value == null) return thenNull();
		return then((int) value);
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Short value) throws IOException {
		if (value == null) return thenNull();
		return then((int) value);
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final int value) throws IOException {
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Integer value) throws IOException {
		if (value == null) return thenNull();
		return then((int) value);
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is non-finite, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final float value) throws IOException {
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null or non-finite, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Float value) throws IOException {
		if (value == null) return thenNull();
		return then((float) value);
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final long value) throws IOException {
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Long value) throws IOException {
		if (value == null) return thenNull();
		return then((long) value);
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is non-finite, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final double value) throws IOException {
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a numeric literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null or non-finite, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Double value) throws IOException {
		if (value == null) return thenNull();
		return then((double) value);
	}
	
	/**
	 * Assigns this key a Boolean literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final boolean value) throws IOException {
		scribe.pop().value(value);
		return parent;
	}
	
	/**
	 * Assigns this key a Boolean literal value and returns the containing
	 * {@link JsonObject}.
	 * 
	 * <p>
	 * If the provided value is null, a {@code null} literal value
	 * will be assigned.
	 * 
	 * @param value the value to assign
	 * @return the containing {@link JsonObject}
	 * @throws IOException if an I/O error occurs
	 */
	public P then(final Boolean value) throws IOException {
		if (value == null) return thenNull();
		return then((boolean) value);
	}
	
	/**
	 * Begins a new JSON object value assigned to this key and returns a 
	 * {@link JsonObjectNode} that can be used to append key-value pairs to it.
	 * 
	 * <p>
	 * Calling the {@link JsonObjectNode#then()} method of the returned JsonObjectNode will
	 * return this key's containing {@link JsonObject}.
	 * 
	 * <p>
	 * When this method returns, a closing quote, colon, and opening brace
	 * will have already been appended to the underlying {@link Appendable}
	 * 
	 * @return a {@link JsonObjectNode} representing the new JSON object value
	 * @throws IOException if an I/O error occurs
	 * @see #thenEmptyObject()
	 */
	public JsonObjectNode<P> object() throws IOException {
		scribe.pop().pushObject();
		return new JsonObjectNode(scribe, parent);
	}
	
	/**
	 * Begins a new JSON array value assigned to this key and returns a 
	 * {@link JsonArrayNode} that can be used to append elements to it.
	 * 
	 * <p>
	 * Calling the {@link JsonArrayNode#then()} method of the returned JsonObjectNode will
	 * return this key's containing {@link JsonObject}.
	 * 
	 * <p>
	 * When this method returns, a closing quote, colon, and opening brace
	 * will have already been appended to the underlying {@link Appendable}
	 * 
	 * @return a {@link JsonArrayNode} representing the new JSON array value
	 * @throws IOException if an I/O error occurs
	 * @see #thenEmptyArray()
	 */
	public JsonArrayNode<P> array() throws IOException {
		scribe.pop().pushArray();
		return new JsonArrayNode(scribe, parent);
	}
	
}
