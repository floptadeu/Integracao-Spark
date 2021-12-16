package model;

public class Cars {
	private int code;
	private int year;
	private String name;
	private String manufacturer;
	
	public Cars() {
		this.code = -1;
		this.year = 0;
		this.name = "N/A";
		this.manufacturer = "N/A";
	}
	
	public Cars(int code, int year, String name, String manufacturer) {
		this.code = -1;
		this.year = 0;
		this.name = "N/A";
		this.manufacturer = "N/A";
	}

	public int getCode() {
		return code;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "Cars [code=" + code + ", year=" + year + ", name=" + name + ", manufacturer=" + manufacturer + "]";
	}
}
