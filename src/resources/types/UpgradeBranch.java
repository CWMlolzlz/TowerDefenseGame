package resources.types;

public class UpgradeBranch{
	
	public int ID;
	private int[] branch = new int[3];
	
	public UpgradeBranch(String[] line){ //optimise
		ID = Integer.parseInt(line[0]);
		branch[0] = Integer.parseInt(line[1]);
		branch[1] = Integer.parseInt(line[2]);
		branch[2] = Integer.parseInt(line[3]);
	}
	
	public int getBranch(int index){
		return branch[index];
	}
	
}