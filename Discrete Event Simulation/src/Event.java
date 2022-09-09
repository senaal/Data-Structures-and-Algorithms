// event class to create events with specific type, player, physiotherapist, time and duration.
public class Event {
	private String type;
	private Player player;
	Physiotherapist physiotherapist;
	private double time;
	private double duration;
	private boolean start = true; //eger startsa true, finishse false 
	double epsilon = 0.0000000001;
	//constructor for massage and training events.
	public Event(String type, Player player, double time, double duration) {
		this.type = type;
		this.player = player;
		this.time = time;
		this.duration = duration;
	}
	//constructor for therapy events.
	public Event(String type, Player player, double time, double duration, Physiotherapist physiotherapist) {
		this.type = type;
		this.player = player;
		this.time = time;
		this.duration = duration;
		this.physiotherapist = physiotherapist;
	}

	public int timeComp(Event o) {
		if(Math.abs(this.time-o.getTime()) < epsilon) {
			return this.player.getID()-o.getPlayer().getID();
		}
		return Double.compare(this.time, o.getTime());
		
	}
	public int skillLevComp(Event o) {
		if(this.player.getSkill() > o.getPlayer().getSkill()) {
			return -1;
		}
		else if(this.player.getSkill() < o.getPlayer().getSkill()) {
			return 1;
		}
		else {
			if(this.getTime() < o.getTime()) {
				return -1;
			}
			else if (this.getTime() > o.getTime()) {
				return 1;
			}
			else {
				if(this.player.getID() < o.getPlayer().getID()) {
					return -1;
				}
				else if(this.player.getID() > o.getPlayer().getID()) {
					return 1;
				}
				else {
					return 0;
				}
			}
			
		}
	}
	public int trTimeComp(Event o) {
		if(this.player.getTrainingTime() < o.getPlayer().getTrainingTime()) {
			return 1;
		}
		else if(this.player.getTrainingTime() > o.getPlayer().getTrainingTime()) {
			return -1;
		}
		else {
			if(this.getTime() < o.getTime()) {
				return -1;
			}
			else if (this.getTime() > o.getTime()) {
				return 1;
			}
			else {
				if(this.player.getID() < o.getPlayer().getID()) {
					return -1;
				}
				else if(this.player.getID() > o.getPlayer().getID()) {
					return 1;
				}
				else {
					return 0;
				}
			}
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public Physiotherapist getPhysiotherapist() {
		return physiotherapist;
	}
	public void setPhysiotherapist(Physiotherapist physiotherapist) {
		this.physiotherapist = physiotherapist;
	}
}
