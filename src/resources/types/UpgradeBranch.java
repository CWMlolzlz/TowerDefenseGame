package resources.types;

public class UpgradeBranch{
	
	public int ID;
	private int[] branches;
	
	public UpgradeBranch(String[] line){ //optimise
		ID = Integer.parseInt(line[0]);
		branches = new int[line.length];
		for(int i = 0; i < branches.length; i++){
			branches[i] = Integer.parseInt(line[i+1]);
		}
	}
	
	public int[] getBranches(){
		return branches;
	}
	
}