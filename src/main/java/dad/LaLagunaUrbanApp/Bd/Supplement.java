package dad.LaLagunaUrbanApp.Bd;

public class Supplement {
	
    private String name;
    private String grams;
    
    public Supplement(String name, String grams) {
        this.name = name;
        this.grams = grams;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGrams() {
        return grams;
    }
    
    public void setGrams(String grams) {
        this.grams = grams;
    }
}
