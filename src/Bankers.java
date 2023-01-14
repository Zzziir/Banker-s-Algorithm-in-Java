import java.util.Scanner;

public class Bankers {
	static int safeSequence[];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter total number of resources: ");
		int resSize = sc.nextInt();
		sc.nextLine();
		int[] resources = new int[resSize];
		int[] cur_avail = new int[resSize];
		
		for(int i=0; i<resSize; i++) {
			System.out.print("Enter total number of instances for resource "+(i+1)+": ");
			resources[i] = sc.nextInt();
			sc.nextLine();
			cur_avail[i] = resources[i];
		}
		
		System.out.print("Enter number of processes: ");
		int procSize = sc.nextInt();
		sc.nextLine();
		
		safeSequence = new int[procSize];
		int[][] max = new int[resSize][procSize];
		int[][] allocation = new int[resSize][procSize];
		
		for(int i=0; i<procSize; i++) {
			System.out.print("Enter the Maximum string for Process "+(i+1)+": ");
			String ip = sc.nextLine();
			for(int j=0; j<resSize; j++) {
				max[j][i] = Integer.parseInt(String.valueOf(ip.charAt(j)));
			}
		}
		
		for(int i=0; i<procSize; i++) {
			System.out.print("Enter the Allocation string for Process "+(i+1)+": ");
			String ip = sc.nextLine();
			for(int j=0; j<resSize; j++) {
				allocation[j][i] = Integer.parseInt(String.valueOf(ip.charAt(j)));
				cur_avail[j] = cur_avail[j] - allocation[j][i];
			}
		}
		
		int[][] need = new int[resSize][procSize];
		  
		for(int i=0; i<procSize; i++) {
			for(int j = 0; j < resSize; j++) {
				need[j][i] = max[j][i] - allocation[j][i];	
			}
        }
        
		boolean safe = checkState(need, allocation, cur_avail, resSize, procSize);
        System.out.println();
        
        if(safe)
        {
         System.out.println("The system is in a Safe State.");
         System.out.print("The Safe Sequence is: ");
         for(int i = 0; i < procSize; i++)
          System.out.print("P" + (safeSequence[i] + 1) + " ");
         System.out.println();
        }
        else
         System.out.println("The system is not in a Safe State.");
	}
	
	static boolean checkState(int need[][], int allocation[][], int cur_avail[], int resSize, int procSize) {
		boolean[] marked = new boolean[procSize];
		int safePos = 0;
		boolean safe = true;
		int[] avail = new int[resSize];
		for(int i=0; i<resSize; i++) {
			avail[i] = cur_avail[i];
		}
		while(safePos < procSize && safe) {
			for(int i=0; i<procSize; i++) {
				int c = 0;
				//checking for candidate
				for(int j=0; j<resSize; j++) {
					if(need[j][i] <= avail[j]) {
						c++;
					}
				}
				//
				if((c == resSize) && (marked[i] == false)) {
					for(int j=0;j<resSize;j++) {
						avail[j] += allocation[j][i];
					}
					marked[i] = true;
					safeSequence[safePos] = i;
					safePos++;
					break;
				}
				//finds a deadlock
				if(i == procSize - 1 && c < resSize) {
					safe = false;
				}
			}
		}
		return safe;
	}
	
	}
