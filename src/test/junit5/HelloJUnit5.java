package test.junit5;

import java.util.Locale;

public class HelloJUnit5 {
	
	private String firstName;
	
	private String lastName;
	
	public HelloJUnit5(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
	}

	public String getFirstName() {
			return firstName;
	}

	public String getLastName() {
			return lastName;
	}
	
	public String getFullName() {
			Locale locale = Locale.getDefault();
			if(locale.getLanguage().equals(Locale.CHINESE.getLanguage())) {
					return lastName + " " + firstName;
			}else {
					return firstName + " " + lastName;
			}
	}
}
