package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static final int STATION_COUNT = 8;
	
	public static void main(String[] args) {
		// Passenger test
		
		Scanner sc = new Scanner(System.in);
		Simulation simulation = new Simulation();
		
		System.out.println("Created new passengers.");
		Passenger p = new Passenger(1, new Station(0), new Station(1), simulation);
		Passenger p1 = new Passenger(2, new Station(0), new Station(1), simulation);
		
		Thread t1 = new Thread(p);
		Thread t2 = new Thread(p1);
		
		t1.start();
		t2.start();
		
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nPress the enter key to board train.");
		sc.nextLine();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				p.boardTrain(new Train(10, simulation)); // added parameters in constructor
				p1.boardTrain(new Train(10, simulation)); // added parameters in constructor
			}
		}).start();
		
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("\nPress the enter key to leave train.");
		sc.nextLine();

		
		new Thread(new Runnable() {
			@Override
			public void run() {
				p.leaveTrain();
				p1.leaveTrain();
			}
		}).start();
		
	}
}
