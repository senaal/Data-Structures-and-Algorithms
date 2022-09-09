import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author FATMA SENA ALÇI
 */
public class project4main {
    //I used to calculate max flow push-relabel algorithm.
    //push method visits every neighbour of active node, if edge between neighbour and active node has capacity and active node's height is bigger than neighbour's, gifts flow.
    public static boolean push(Node n, Node sink, ArrayDeque<Node> activeNodes){
        for(Edge edge : n.getEdges()){
            if(n.getHeight() > edge.getEnd().getHeight() && edge.getCapacity() > 0){
                int flow = Math.min(n.getExcessFlow(), edge.getCapacity());
                n.setExcessFlow(n.getExcessFlow() - flow);
                edge.getEnd().setExcessFlow(edge.getEnd().getExcessFlow() + flow);
                edge.setCapacity(edge.getCapacity() - flow);
                if(!edge.getEnd().equals(sink)) {
                    edge.getReverseEdge().setCapacity(edge.getReverseEdge().getCapacity() + flow);
                }
                activeNodes.addFirst(edge.getEnd());
                return true;
            }
        }
        if (n.getExcessFlow()>0) activeNodes.addFirst(n);
        return false;
    }
    //relabel method works when push cannot possible. It increases active node's height.
    public static void relabel(Node n){
        int minHeight = 0;
        for(Edge edge : n.getEdges()){
            minHeight = edge.getStart().getHeight();
            if(edge.getCapacity() > 0){
                minHeight = Math.min(edge.getEnd().getHeight(),minHeight);
            }
        }
        n.setHeight(minHeight + 1);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Node> trains = new ArrayList<>();
        ArrayList<Node> reindeers = new ArrayList<>();
        ArrayList<Node> vehicles = new ArrayList<>();
        ArrayList<Node> bags = new ArrayList<>();
        ArrayDeque<Node> activeNodes = new ArrayDeque<>();

        Node source = new Node(0, 0);
        Node sink = new Node(0, 0);
        nodes.add(source);
        nodes.add(sink);

        //creates bags
        Node b = new Node("b", 2,0);
        Node c = new Node("c",2,0);
        Node d = new Node("d",2,0);
        Node e = new Node("e",2,0);
        Node bd = new Node("bd",2,0);
        Node be = new Node("be",2,0);
        Node cd = new Node("cd",2,0);
        Node ce = new Node("ce",2,0);

        bags.add(b);
        bags.add(c);
        bags.add(d);
        bags.add(e);
        bags.add(bd);
        bags.add(be);
        bags.add(cd);
        bags.add(ce);

        //creates vehicles and vehicles edges between vehicle and sink.
        int green = in.nextInt();
        for(int i = 0; i < green; i++){
            Node train = new Node("g", 1, in.nextInt());
            nodes.add(train);
            trains.add(train);
            vehicles.add(train);
            Edge edge = new Edge(train, sink, train.getCapacity());
            train.getEdges().add(edge);
        }
        int red = in.nextInt();
        for(int i = 0; i < red; i++){
            Node train = new Node("r", 1, in.nextInt());
            nodes.add(train);
            trains.add(train);
            vehicles.add(train);
            Edge edge = new Edge(train, sink, train.getCapacity());
            train.getEdges().add(edge);
        }
        int greenR = in.nextInt();
        for(int i = 0; i < greenR; i++){
            Node reindeer = new Node("g", 1, in.nextInt());
            nodes.add(reindeer);
            reindeers.add(reindeer);
            vehicles.add(reindeer);
            Edge edge = new Edge(reindeer, sink, reindeer.getCapacity());
            reindeer.getEdges().add(edge);
        }
        int redR = in.nextInt();
        for(int i = 0; i < redR; i++){
            Node reindeer = new Node("r", 1, in.nextInt());
            nodes.add(reindeer);
            reindeers.add(reindeer);
            vehicles.add(reindeer);
            Edge edge = new Edge(reindeer, sink, reindeer.getCapacity());
            reindeer.getEdges().add(edge);
        }
        int numberOfBags = in.nextInt();
        String empty =  in.nextLine();
        int giftCounter = 0;

        //adds gifts to bags.
        if(numberOfBags != 0) {
            String bagsLine = in.nextLine();
            String[] bagsL = bagsLine.split(" ");
            //input reading and adding same type of bags
            for (int i = 0; i < bagsL.length; i += 2) {
                String type = bagsL[i];
                int gifts = Integer.parseInt(bagsL[i + 1]);
                giftCounter += gifts;
                if (type.contains("a")) {
                    Node bag = new Node(type, 2, gifts);
                    nodes.add(bag);
                    bags.add(bag);
                } else if (type.equals("b")) {
                    b.setCapacity(b.getCapacity() + gifts);
                    nodes.add(b);
                } else if (type.equals("c")) {
                    c.setCapacity(c.getCapacity() + gifts);
                    nodes.add(c);
                } else if (type.equals("d")) {
                    d.setCapacity(d.getCapacity() + gifts);
                    nodes.add(d);
                } else if (type.equals("e")) {
                    e.setCapacity(e.getCapacity() + gifts);
                    nodes.add(e);
                } else if (type.equals("bd")) {
                    bd.setCapacity(bd.getCapacity() + gifts);
                    nodes.add(bd);
                } else if (type.equals("be")) {
                    be.setCapacity(be.getCapacity() + gifts);
                    nodes.add(be);
                } else if (type.equals("cd")) {
                    cd.setCapacity(cd.getCapacity() + gifts);
                    nodes.add(cd);
                } else if (type.equals("ce")) {
                    ce.setCapacity(ce.getCapacity() + gifts);
                    nodes.add(ce);
                }
            }
        }
        //creates edges between bags and vehicles according to conditions.
        for (Node bag : bags){
            Edge sourceEdge = new Edge(source, bag, 0);
            Edge revEdge = new Edge(bag, source, bag.getCapacity());
            source.getEdges().add(sourceEdge);
            bag.getEdges().add(revEdge);
            sourceEdge.setReverseEdge(revEdge);
            revEdge.setReverseEdge(sourceEdge);
            bag.setExcessFlow(bag.getCapacity());
            activeNodes.add(bag);
            switch (bag.getType()) {
                case "a":
                    for (Node vehicle : vehicles) {
                        Edge edge = new Edge(bag, vehicle, 1);
                        Edge revEd = new Edge(vehicle, bag, 0);
                        bag.getEdges().add(edge);
                        vehicle.getEdges().add(revEd);
                        edge.setReverseEdge(revEd);
                        revEd.setReverseEdge(edge);
                    }
                    break;
                case "b":
                    for (Node vehicle : vehicles) {
                        if (vehicle.getType().equals("g")) {
                            Edge edge = new Edge(bag, vehicle, vehicle.getCapacity());
                            Edge revEd = new Edge(vehicle, bag, 0);
                            bag.getEdges().add(edge);
                            vehicle.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "c":
                    for (Node vehicle : vehicles) {
                        if (vehicle.getType().equals("r")) {
                            Edge edge = new Edge(bag, vehicle,vehicle.getCapacity());
                            Edge revEd = new Edge(vehicle, bag, 0);
                            bag.getEdges().add(edge);
                            vehicle.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "d":
                    for (Node train : trains) {
                        Edge edge = new Edge(bag, train, train.getCapacity());
                        Edge revEd = new Edge(train, bag, 0);
                        bag.getEdges().add(edge);
                        train.getEdges().add(revEd);
                        edge.setReverseEdge(revEd);
                        revEd.setReverseEdge(edge);
                    }
                    break;
                case "e":
                    for (Node reindeer : reindeers) {
                        Edge edge = new Edge(bag, reindeer, reindeer.getCapacity());
                        Edge revEd = new Edge(reindeer, bag, 0);
                        bag.getEdges().add(edge);
                        reindeer.getEdges().add(revEd);
                        edge.setReverseEdge(revEd);
                        revEd.setReverseEdge(edge);
                    }
                    break;
                case "ab":
                    for (Node vehicle : vehicles) {
                        if (vehicle.getType().equals("g")) {
                            Edge edge = new Edge(bag, vehicle, 1);
                            Edge revEd = new Edge(vehicle, bag, 0);
                            bag.getEdges().add(edge);
                            vehicle.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "ac":
                    for (Node vehicle : vehicles) {
                        if (vehicle.getType().equals("r")) {
                            Edge edge = new Edge(bag, vehicle, 1);
                            Edge revEd = new Edge(vehicle, bag, 0);
                            bag.getEdges().add(edge);
                            vehicle.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "ad":
                    for (Node train : trains) {
                        Edge edge = new Edge(bag, train, 1);
                        Edge revEd = new Edge(train, bag, 0);
                        bag.getEdges().add(edge);
                        train.getEdges().add(revEd);
                        edge.setReverseEdge(revEd);
                        revEd.setReverseEdge(edge);
                    }
                    break;
                case "ae":
                    for (Node reindeer : reindeers) {
                        Edge edge = new Edge(bag, reindeer, 1);
                        Edge revEd = new Edge(reindeer, bag, 0);
                        bag.getEdges().add(edge);
                        reindeer.getEdges().add(revEd);
                        edge.setReverseEdge(revEd);
                        revEd.setReverseEdge(edge);
                    }
                    break;
                case "bd":
                    for (Node train : trains) {
                        if (train.getType().equals("g")) {
                            Edge edge = new Edge(bag, train, train.getCapacity());
                            Edge revEd = new Edge(train, bag, 0);
                            bag.getEdges().add(edge);
                            train.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "be":
                    for (Node reindeer : reindeers) {
                        if (reindeer.getType().equals("g")) {
                            Edge edge = new Edge(bag, reindeer, reindeer.getCapacity());
                            Edge revEd = new Edge(reindeer, bag, 0);
                            bag.getEdges().add(edge);
                            reindeer.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "cd":
                    for (Node train : trains) {
                        if (train.getType().equals("r")) {
                            Edge edge = new Edge(bag, train, train.getCapacity());
                            Edge revEd = new Edge(train, bag, 0);
                            bag.getEdges().add(edge);
                            train.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "ce":
                    for (Node reindeer : reindeers) {
                        if (reindeer.getType().equals("r")) {
                            Edge edge = new Edge(bag, reindeer, reindeer.getCapacity());
                            Edge revEd = new Edge(reindeer, bag, 0);
                            bag.getEdges().add(edge);
                            reindeer.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "abd":
                    for (Node train : trains) {
                        if (train.getType().equals("g")) {
                            Edge edge = new Edge(bag, train,1);
                            Edge revEd = new Edge(train, bag, 0);
                            bag.getEdges().add(edge);
                            train.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "abe":
                    for (Node reindeer : reindeers) {
                        if (reindeer.getType().equals("g")) {
                            Edge edge = new Edge(bag, reindeer, 1);
                            Edge revEd = new Edge(reindeer, bag, 0);
                            bag.getEdges().add(edge);
                            reindeer.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "acd":
                    for (Node train : trains) {
                        if (train.getType().equals("r")) {
                            Edge edge = new Edge(bag, train, 1);
                            Edge revEd = new Edge(train, bag, 0);
                            bag.getEdges().add(edge);
                            train.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
                case "ace":
                    for (Node reindeer : reindeers) {
                        if (reindeer.getType().equals("r")) {
                            Edge edge = new Edge(bag, reindeer, 1);
                            Edge revEd = new Edge(reindeer, bag, 0);
                            bag.getEdges().add(edge);
                            reindeer.getEdges().add(revEd);
                            edge.setReverseEdge(revEd);
                            revEd.setReverseEdge(edge);
                        }
                    }
                    break;
            }
        }
        //initial conditions of source
        source.setHeight(nodes.size());
        source.setCapacity(giftCounter);

        //set to avoid relabeling the same vehicle.
        HashSet<Node> relabelledVehicles = new HashSet<>();

        //while loop until excess flow is zero except source and sink.
        while(!activeNodes.isEmpty()){
            Node current = activeNodes.poll();
            if(current.equals(source) || current.equals(sink)){
                continue;
            }
            if(current.getExcessFlow() > 0){
                boolean check = push(current, sink, activeNodes);
                if(!check){
                    if (current.getExcessFlow()>0) {
                        relabel(current);
                        if(current.getType().equals("g") || current.getType().equals("r")){
                            activeNodes.addLast(current);
                            relabelledVehicles.add(current);
                            if(relabelledVehicles.size() == vehicles.size()){
                                break;
                            }
                        }
                        else{
                            activeNodes.addFirst(current);
                        }
                    }
                }
                if (current.getExcessFlow()>0){
                    activeNodes.addFirst(current);
                }
            }
        }
        out.println(giftCounter - sink.getExcessFlow());
    }
}
