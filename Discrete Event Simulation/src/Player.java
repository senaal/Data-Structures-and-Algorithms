//plsyer class to create players with specific id, skill level and status.
public class Player {
	private int ID;
	private int skill;
	private boolean status = false;
	private double trainingTime;
	private double massageTime;
	private double therapyTime;
	private double waitTrainQ;
	private double waitTherQ;
	private double waitMassQ;
	private int massage;
	public Player(int iD, int skill) {
		ID = iD;
		this.skill = skill;
	}
	public void increaseThWT(double waitingTime) {
		this.waitTherQ += waitingTime;
	}
	public void increaseMWT(double waitingTime) {
		this.waitMassQ += waitingTime;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getSkill() {
		return skill;
	}
	public void setSkill(int skill) {
		this.skill = skill;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public double getMassageTime() {
		return massageTime;
	}
	public void setMassageTime(double massageTime) {
		this.massageTime = massageTime;
	}
	public double getTherapyTime() {
		return therapyTime;
	}
	public void setTherapyTime(double therapyTime) {
		this.therapyTime = therapyTime;
	}
	public double getTrainingTime() {
		return trainingTime;
	}
	public void setTrainingTime(double trainingTime) {
		this.trainingTime = trainingTime;
	}
	public int getMassage() {
		return massage;
	}
	public void setMassage(int massage) {
		this.massage = massage;
	}
	public double getWaitTrainQ() {
		return waitTrainQ;
	}
	public void setWaitTrainQ(double waitTrainQ) {
		this.waitTrainQ = waitTrainQ;
	}
	public double getWaitTherQ() {
		return waitTherQ;
	}
	public void setWaitTherQ(double waitTherQ) {
		this.waitTherQ = waitTherQ;
	}
	public double getWaitMassQ() {
		return waitMassQ;
	}
	public void setWaitMassQ(double waitMassQ) {
		this.waitMassQ = waitMassQ;
	}
}
