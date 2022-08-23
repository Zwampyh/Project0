package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class test {
	
	Calculator calculator;
	
	@BeforeAll
	public void init() {
		calculator = new Calculator();
	}
	
	
	@Test
	public void additionTest() {
		assertEquals(2, calculator.add(1, 1));
	}

}
