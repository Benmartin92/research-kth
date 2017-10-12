
public class functionF {
	private String vertex;
	private int value;
	
	public functionF(String vertex, int maxValue) {
		super();
		this.vertex = vertex;
		this.value = maxValue;
	}
	public functionF() {
		super();
		this.vertex = "";
		this.value = 0;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int value){
		this.value=value;
	}
	
	public String getVertex(){
		return this.vertex;
	}
	
	public boolean isThisVertex(String vertex){
		if (vertex==this.vertex)
			return true;
		else
			return false;
	}
	
	
}
