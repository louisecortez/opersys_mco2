package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static final int STATION_COUNT = 8;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Simulation s = new Simulation();
		
		Passenger pTemp;
		
		int trainCapacity;
		int originTemp = 0;
		int destinationTemp = 1;
		int passengerCnt = 0;
		String addPassenger = "0";
		
		do {
			System.out.println("*** Adding passenger " + "#" + (passengerCnt + 1) + "***");
			
			// gui for error checking if origin/destination are Stations
			System.out.println("Enter origin: ");
			originTemp = sc.nextInt();
			System.out.println("Enter destination: ");
			destinationTemp = sc.nextInt();
			sc.nextLine();
			
			pTemp = new Passenger(passengerCnt, s);
			
			s.passengerEnterSim(pTemp, originTemp, destinationTemp);
			passengerCnt++;
			
			System.out.println("Add new passenger? (1) - yes, otherwise - no");
			addPassenger = sc.nextLine();
		} while(addPassenger.compareToIgnoreCase("1") == 0);
		
		System.out.println("Enter train capacity: ");
		trainCapacity = sc.nextInt();
		System.out.println("Number of trains: " + s.setTrainCnt(trainCapacity));
		s.setTrains(s.setTrainCnt(trainCapacity), trainCapacity);
		
		s.startSimulation();
		
		System.out.println("complete simulation initialization");
		
		// Passenger test
		
//		Scanner sc = new Scanner(System.in);
//		Simulation simulation = new Simulation();
//		
//		System.out.println("Created new passengers.");
//		Passenger p = new Passenger(1, new Station(0), new Station(1), simulation);
//		Passenger p1 = new Passenger(2, new Station(0), new Station(1), simulation);
//		
//		Thread t1 = new Thread(p);
//		Thread t2 = new Thread(p1);
//		
//		t1.start();
//		t2.start();
//		
//		
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("\nPress the enter key to board train.");
//		sc.nextLine();
//		
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				p.boardTrain(new Train(10, simulation)); // added parameters in constructor
//				p1.boardTrain(new Train(10, simulation)); // added parameters in constructor
//			}
//		}).start();
//		
//		
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		
//		System.out.println("\nPress the enter key to leave train.");
//		sc.nextLine();
//
//		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				p.leaveTrain();
//				p1.leaveTrain();
//			}
//		}).start();
//		
	}
}

//package model;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//	public static final int STATION_COUNT = 8;
//	
//	public static void main(String[] args) {
//		Simulation s = new Simulation();
//		s.initComponents();
//		
//		System.out.println("complete simulation initialization");
//		
//		// Passenger test
//		
////		Scanner sc = new Scanner(System.in);
////		Simulation simulation = new Simulation();
////		
////		System.out.println("Created new passengers.");
////		Passenger p = new Passenger(1, new Station(0), new Station(1), simulation);
////		Passenger p1 = new Passenger(2, new Station(0), new Station(1), simulation);
////		
////		Thread t1 = new Thread(p);
////		Thread t2 = new Thread(p1);
////		
////		t1.start();
////		t2.start();
////		
////		
////		try {
////			Thread.sleep(500);
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
////		
////		System.out.println("\nPress the enter key to board train.");
////		sc.nextLine();
////		
////		
////		new Thread(new Runnable() {
////			@Override
////			public void run() {
////				p.boardTrain(new Train(10, simulation)); // added parameters in constructor
////				p1.boardTrain(new Train(10, simulation)); // added parameters in constructor
////			}
////		}).start();
////		
////		
////		try {
////			Thread.sleep(500);
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
////		
////		
////		System.out.println("\nPress the enter key to leave train.");
////		sc.nextLine();
////
////		
////		new Thread(new Runnable() {
////			@Override
////			public void run() {
////				p.leaveTrain();
////				p1.leaveTrain();
////			}
////		}).start();
////		
//	}
//}
