package com.revbingo.capulet;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.NoSuchElementException;

import org.junit.Test;

public class TestCapuletParser {

	@Test
	public void canGetASimpleKey() throws Exception {
		CapuletParser unit = new CapuletParser("{\"key\":\"value\"}");
		
		String value = unit.get("key");
		
		assertThat(value, is("value"));
		
	}
	
	@Test
	public void canGetNestedKey() throws Exception {
		CapuletParser unit = new CapuletParser("{\"key\":{\"nested\":\"value\"}}");
		
		String value = unit.get("key.nested");
		
		assertThat(value, is("value"));
	}
	
	@Test(expected=ParseException.class)
	public void throwsExceptionIfKeyNotFound() throws Exception {
		CapuletParser unit = new CapuletParser("{\"key\":\"value\"}");
		
		String value = unit.get("noSuchKey");
	}
	
	@Test(expected=ParseException.class)
	public void throwsExceptionIfNestedKeyNotFound() throws Exception {
		CapuletParser unit = new CapuletParser("{\"key\":{\"nested\":\"value\"}}");
		
		String value = unit.get("key.notNested");
		
	}
	
	@Test
	public void getArrayElement() throws Exception {
		CapuletParser unit = new CapuletParser("{\"array\":[{\"key\":\"value1\"}, {\"key\":\"value2\"}]}");
		
		String value = unit.get("array[0].key");
		
		assertThat(value, is("value1"));
	}
	
	@Test(expected=ParseException.class)
	public void throwsExceptionIfKeyWithArrayIndexIsNotFound() throws Exception {
		CapuletParser unit = new CapuletParser("{\"array\":[{\"key\":\"value1\"}, {\"key\":\"value2\"}]}");
		
		String value = unit.get("notAnArray[0].key");
	}
	
	@Test(expected=ParseException.class)
	public void throwsExceptionIfArrayIndexOutOfBounds() throws Exception {
		CapuletParser unit = new CapuletParser("{\"array\":[{\"key\":\"value1\"}, {\"key\":\"value2\"}]}");
		
		String value = unit.get("array[3].key");
	}
}
