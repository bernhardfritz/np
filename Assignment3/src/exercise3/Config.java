package exercise3;

public class Config {
	public final int insertOperations;
	public final int readOperations;
	public final int removeOperations;
	
	public Config(int insertOperations, int readOperations, int removeOperations) {
		this.insertOperations = insertOperations;
		this.readOperations = readOperations;
		this.removeOperations = removeOperations;
	}
}
