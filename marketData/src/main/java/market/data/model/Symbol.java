package market.data.model;

public class Symbol {
	private String Symbol;
	private String Name;
	private String Sector;
	public Symbol(String symbol, String name, String sector) {
		super();
		Symbol = symbol;
		Name = name;
		Sector = sector;
	}
	public String getSymbol() {
		return Symbol;
	}
	public String getName() {
		return Name;
	}
	public String getSector() {
		return Sector;
	}
	@Override
	public String toString() {
		return "Symbol [Symbol=" + Symbol + ", Name=" + Name + ", Sector="
				+ Sector + "]";
	}

}
