import java.io.FileNotFoundException;
import java.util.*;
import java.io.PrintStream;
import java.io.File;

public class project3main{
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));

        Map<String, HashMap<City,Integer>> cities = new HashMap<>();
        ArrayList<City> citiesList = new ArrayList<>();
        TreeSet<City> firstCities = new TreeSet<>();
        PriorityQueue<City> secondCities = new PriorityQueue<>();
        
        Set<City> visitedCity = new HashSet<>();
        Set<City> honeymoonC = new HashSet<>();
        Stack<City> path = new Stack<>();

        //input reading
        int timeLimit = in.nextInt();
        int numOfCities = in.nextInt();
        int honeymoon = 0;

        City Mecnuns = new City(in.next(), 0);
        firstCities.add(Mecnuns);
        citiesList.add(Mecnuns);
        String Leylas = in.next();
        int numberOfC = Integer.parseInt(Leylas.substring(1));

        //creating cities
        for(int i = 1; i < numOfCities; i++){
            City cit = new City(" ", 0);
            citiesList.add(cit);
        }
        
        for(int i = 0; i < numOfCities; i++){
            String mainCity = in.next();
            if (i > 0 && i < numberOfC){
                City cit = citiesList.get(i);
                cit.setName(mainCity);
                cit.setDistance(Integer.MAX_VALUE);
                firstCities.add(cit);
            }
            else{
                if(i > 0){
                    City cit = citiesList.get(i);
                    cit.setName(mainCity);
                    cit.setDistance(Integer.MAX_VALUE);
                    secondCities.add(cit);
                }
            }
            //uses hashmap in hashmap to create city map.
            HashMap<City, Integer> neighbours = new HashMap<>();
            
            if(in.hasNext()) {
                String city = in.nextLine();
                String[] splitNe = city.split(" ");
                int sizeOfArray = splitNe.length;
                for (int j = 0; j < sizeOfArray; j++) {
                    if (j % 2 == 1) {
                        String[] name = splitNe[j].split("");
                        if (name[0].equals("c")) {
                            int id = Integer.parseInt(splitNe[j].substring(1)) - 1;
                            neighbours.put(citiesList.get(id), Integer.parseInt(splitNe[j + 1]));
                        }
                        else if (name[0].equals("d")){
                            int id = firstCities.size()+Integer.parseInt(splitNe[j].substring(1)) - 1;
                            neighbours.put(citiesList.get(id), Integer.parseInt(splitNe[j + 1]));
                        }
                    }
                }
            }
            cities.put(mainCity , neighbours);
        }

        //DIJKSTRA ALGORITHM Checks all cities in first side and calculate shortest path.
        while (!firstCities.isEmpty()) {
            City current = firstCities.pollFirst();
            int distt = current.getDistance();
            for( City temp: cities.get(current.getName()).keySet()){
                int dist = cities.get(current.getName()).get(temp);
                if(temp.getDistance() > dist+distt && !visitedCity.contains(temp)) {
                    firstCities.remove(temp);
                    temp.setDistance(dist+distt);
                    temp.setOldCity(current);
                    firstCities.add(temp);
                }
            }
            visitedCity.add(current);
        }
        //Checks Mecnun can arrive to Leyla's city and writes the path. Also, calculates arrival time.
        int arrivalTime = 0;
        boolean checkLeyla = false;
        for (City city : citiesList) {
            if (city.getName().equals(Leylas)){
                arrivalTime = city.getDistance();
                if(city.getDistance() == Integer.MAX_VALUE || city.getDistance() < 0){
                    checkLeyla = true;
                }
                city.setDistance(0);
                secondCities.add(city);
                while(city.getOldCity() != null){
                    path.push(city);
                    city = city.getOldCity();
                }
            }
        }
        //Path writing to output file.
        path.push(Mecnuns);
        if(arrivalTime > 0 && !checkLeyla){
            while(!path.isEmpty()) {
                out.print(path.pop().getName() + " ");
            }
            out.println();
        }
        else if(checkLeyla){
            out.println(-1);
        }

        //PRIM ALGORITHM Checks all cities in second side and calculates lowest cost for travel.
        //Makes all paths between two sides are undirected.
        for (City city : secondCities) {
            for (City cit : cities.get(city.getName()).keySet()){
                int distance = cities.get(city.getName()).get(cit);
                if (cities.get(cit.getName()).get(city) != null && distance < cities.get(cit.getName()).get(city)){
                    cities.get(cit.getName()).put(city, distance);
                }
                else if (cities.get(cit.getName()).get(city) == null){
                    cities.get(cit.getName()).put(city, distance);
                }
            }
        }
        boolean check = true;
        while (!secondCities.isEmpty()) {
            City current = secondCities.poll();
            if (current.getDistance() == Integer.MAX_VALUE){
                check = false;
            }
            for( City temp: cities.get(current.getName()).keySet()){
            	 if (temp.getName().charAt(0) == 'c') {
                     continue;
                 }
                int dist = cities.get(current.getName()).get(temp);
                if(!temp.equals(current)&&temp.getDistance() > dist && !honeymoonC.contains(temp)) {
                    secondCities.remove(temp);
                    temp.setDistance(dist);
                    temp.setOldCity(current);
                    secondCities.add(temp);
                }
            }

            honeymoonC.add(current);
        }
        //Honeymoon payment calculator.
        for (City city : honeymoonC){
            honeymoon += city.getDistance();

        }
        honeymoon = honeymoon*2;
        
        if(arrivalTime > timeLimit){
            honeymoon = -1;
        }
        if(arrivalTime < 0){
            honeymoon = -1;
        }
        else if (!check){
            honeymoon = -2;
        }
        //Writing honeymoon tax.
        out.println(honeymoon);
    }
}
