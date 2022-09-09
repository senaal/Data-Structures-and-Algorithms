public class City implements Comparable<City> {
    private String name;
    private int distance;
    private City oldCity;

    public City(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    @Override
    public int compareTo(City o) {
        if(this.distance < o.distance){
            return -1;
        }
        else if(this.distance > o.distance){
            return 1;
        }
        return o.getName().compareTo(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public City getOldCity() {
        return oldCity;
    }

    public void setOldCity(City oldCity) {
        this.oldCity = oldCity;
    }
}
