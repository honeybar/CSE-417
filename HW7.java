import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
// this program is used to come up with a strategy to maximize profit based on the available
// stock market trend
public class HW7 {
	public static void main (String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		System.out.println("file name:");
		String filename = input.next();
		Scanner file = new Scanner(new File(filename));
		HW7 stockmarket = new HW7();
		stockmarket.predictStock(file);
	}
	public void predictStock(Scanner file) {
		String line = file.nextLine();
		Scanner data = new Scanner(line);
		int numData = data.nextInt();
		line = file.nextLine();
		data = new Scanner(line);
		int k = data.nextInt();
		int[] trends = new int[numData];
		for(int i = 0; i < numData; i++) { // put costs in the array
			line = file.nextLine();
			data = new Scanner(line);
			trends[i] = data.nextInt();
		}
		
		file.close();
		data.close();
		int[][] strategy = new int[k][2];
		int j = 0;
		int sum = 0;
		for(int i = 0; i < numData-1; i++) {
			// find the lowest price just before the the price goes up. 
			while(i < numData-1 && trends[i+1] <= trends[i] ) {
				i++;
			}
			int buy = i;
			int h = buy+1;
			int sell = h;
			h++;
			// find the highest stock price before the price drops again
			while(h < numData && trends[h] > trends[sell]) {
				sell = h;
				h++;
			}
			// if there is a profit...
			if(sell < numData && trends[sell]-trends[buy]>0) {
				if(j < k) { // if the strategy is not full yet...
					strategy[j][0] = buy;
					strategy[j][1] = sell;
					sum+= (trends[sell]-trends[buy]);
					j++;
				} else { // if there is already k strategies...
					int smallest = -1;
					// find the weakest strategy
					for(int x = 0; x < k; x++) {
						if(trends[strategy[x][1]]-trends[strategy[x][0]]< trends[sell]-trends[buy]) {
							if(smallest == -1 || trends[strategy[x][1]]-trends[strategy[x][0]]< trends[strategy[smallest][1]]-trends[strategy[smallest][0]]) {
								smallest = x;
							} 
						}
					}
					if(smallest != -1) { // check if there is a weakest strategy in the current strategy
						sum -= (trends[strategy[smallest][1]]-trends[strategy[smallest][0]]);
						strategy[smallest][0] = buy;
						strategy[smallest][1] = sell;
						sum+= (trends[sell]-trends[buy]);
					} 
				}
			}
			i = sell;
		}
		for(int[] arr: strategy)
			System.out.println(Arrays.toString(arr));
		System.out.println("total sum: " + sum);
	}
	
}
