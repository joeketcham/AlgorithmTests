package prj3;

public class Fuel {
  final private String id;
  private boolean used;
  
  public Fuel(String id) {
    this.id = id;
    this.used = false;
  }
  public String getId() {
    return id;
  }
  
  public void setUsed(boolean inUsed){
	  this.used = inUsed;
  }
  
  public boolean getUsed(){
	  return used;
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
  

  @Override
  public String toString() {
    return id;
  }
  
} 