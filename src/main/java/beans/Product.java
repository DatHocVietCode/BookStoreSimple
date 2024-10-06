package beans;

public class Product  {
	private int id;
	private int number = 0;
	public Product() {}
	public Product(int id, int number)
	{
		this.setId(id);
		this.setNumber(number);
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
