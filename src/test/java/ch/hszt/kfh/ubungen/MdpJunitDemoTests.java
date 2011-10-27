package ch.hszt.kfh.ubungen;

import org.junit.Test;

import static org.junit.Assert.*;

public class MdpJunitDemoTests {
	
	private IMdpJunitDemo instance = new MdpJunitDemoImpl();
	
	@Test
	public void isEmptyWithNull() {
		assertEquals(true, instance.isEmpty(null));
	}
	
	@Test
	public void isEmptyWithEmpty() {
		assertEquals(true, instance.isEmpty(""));
	}

	@Test
	public void isEmptyWithNotEmpty() {
		assertEquals(false, instance.isEmpty("Dorff"));
	}
	
	@Test
	public void isEmptyWithSpace() {
		assertEquals(false, instance.isEmpty(" "));
	}
	
	@Test
	public void capitalizeWithTestCasesFromInterfaceDefinition() {
		assertEquals("Hello", instance.capitalize("hello"));
		assertEquals("Hello", instance.capitalize("HELLO"));
		assertEquals("123abc", instance.capitalize("123ABC"));
	}
	
	@Test
	public void capitalizeWithNull() {
		assertEquals(null, instance.capitalize(null));
	}
	
	@Test
	public void capitalizeWithEmpty() {
		assertEquals("", instance.capitalize(""));
	}
	
	@Test
	public void capitalizeWithSpace() {
		assertEquals(" ", instance.capitalize(" "));
	}
	
	@Test
	public void capitalizeWithSizeOneLower() {
		assertEquals("A", instance.capitalize("a"));
	}
	
	@Test
	public void capitalizeWithSizeOneUpper() {
		assertEquals("A", instance.capitalize("A"));
	}
	
	@Test
	public void capitalizeWithSizeTwoLowerLower() {
		assertEquals("Ab", instance.capitalize("ab"));
	}
	
	@Test
	public void capitalizeWithSizeTwoUpperUpper() {
		assertEquals("Ab", instance.capitalize("AB"));
	}
	
	@Test
	public void capitalizeWithSizeTwoLowerUpper() {
		assertEquals("Ab", instance.capitalize("aB"));
	}
	
	@Test
	public void capitalizeWithSizeTwoUpperLower() {
		assertEquals("Ab", instance.capitalize("Ab"));
	}
	
	@Test
	public void reverseWithNull() {
		try {
			instance.reverse(null);
			fail("Expected exception not thrown.");
		} catch (NullPointerException e) {
			// that's intended.
		}
	}
	
	@Test
	public void reverseWithEmpty() {
		assertEquals("", instance.reverse(""));
	}
	
	@Test
	public void reverseWithTestHsz() {
		// Test case aus Interface-Definition scheint falsch zu sein => adaptiert
		assertEquals("Test Hsz", instance.reverse("zsH tseT"));
	}
	
	@Test
	public void joinWithEmptyList() {
		assertEquals("", instance.join());
	}
	
	@Test
	public void joinWithOne() {
		assertEquals("Dorff", instance.join("Dorff"));
	}
	
	@Test
	public void joinWithTestCasesFromInterfaceDefinition() {
		assertEquals("a b c", instance.join("a", "b", "c"));
	}
	
}
