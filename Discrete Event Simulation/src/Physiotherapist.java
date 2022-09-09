// physiotherapy class to create physiotherapists with specific id, duration and status.
public class Physiotherapist implements Comparable<Physiotherapist> {
	private int ID;
	private double duration;
	private boolean status;
	public Physiotherapist(int iD, double duration) {
		ID = iD;
		this.duration = duration;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public int compareTo(Physiotherapist o) {
		return 0;
	}
}
