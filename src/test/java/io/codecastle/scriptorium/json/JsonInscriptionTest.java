/*
 * Copyright 2018 Doug Valenta.
 * Licensed under the terms of the MIT License.
 */
package io.codecastle.scriptorium.json;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Doug Valenta
 */
public class JsonInscriptionTest {
	
	@Test
	public void testObject() throws IOException {
		final StringBuilder appendable = new StringBuilder();
		final MockInscribableAppendable inscribable = new MockInscribableAppendable(appendable);
		final JsonObjectNode<MockInscribableAppendable> inscription = inscribable.inscribe(JsonInscription::object);
		Assertions.assertEquals("{", appendable.toString());
		final MockInscribableAppendable result = inscription.then();
		Assertions.assertEquals(inscribable, result);
		Assertions.assertEquals("{}", appendable.toString());
	}
	
	@Test
	public void testArray() throws IOException {
		final StringBuilder appendable = new StringBuilder();
		final MockInscribableAppendable inscribable = new MockInscribableAppendable(appendable);
		final JsonArrayNode<MockInscribableAppendable> inscription = inscribable.inscribe(JsonInscription::array);
		Assertions.assertEquals("[", appendable.toString());
		final MockInscribableAppendable result = inscription.then();
		Assertions.assertEquals(inscribable, result);
		Assertions.assertEquals("[]", appendable.toString());
	}
	
}
