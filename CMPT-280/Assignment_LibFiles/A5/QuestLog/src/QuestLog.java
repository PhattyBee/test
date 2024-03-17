import com.opencsv.CSVReader;
import lib280.base.Pair280;
import lib280.hashtable.KeyedChainedHashTable280;
import lib280.tree.OrderedSimpleTree280;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

// This project uses a JAR called opencsv which is a library for reading and
// writing CSV (comma-separated value) files.
// 
// You don't need to do this for this project, because it's already done, but
// if you want to use opencsv in other projects on your own, here's the process:
//
// 1. Download opencsv-3.1.jar from http://sourceforge.net/projects/opencsv/
// 2. Drag opencsv-3.1.jar into your project.
// 3. Right-click on the project in the package explorer, select "Properties" (at bottom of popup menu)
// 4. Choose the "Libraries" tab
// 5. Click "Add JARs"
// 6. Select the opencsv-3.1.jar from within your project from the list.
// 7. At the top of your .java file add the following imports:
//        import java.io.FileReader;
//        import com.opencsv.CSVReader;
//
// Reference documentation for opencsv is here:  
// http://opencsv.sourceforge.net/apidocs/overview-summary.html


public class QuestLog extends KeyedChainedHashTable280<String, QuestLogEntry> {

	public QuestLog() {
		super();
	}

	/**
	 * Obtain an array of the keys (quest names) from the quest log.  There is
	 * no expectation of any particular ordering of the keys.
	 *
	 * @return The array of keys (quest names) from the quest log.
	 */
	public String[] keys() {

		// TODO Implement this method.

		//First, try to get the size of the hash table, so we can create our string array of keys.
		int size = 0;
		for(int i = 0; i < this.capacity(); i++) {


			if(this.hashArray[i] != null) {
				this.hashArray[i].goFirst();
				while(this.hashArray[i].itemExists()) {
					size++;
					this.hashArray[i].goForth();
				}
			}
		}

		String[] keys = new String[size];

		// Iterate again to fill the string array.
		//Separate the iterator with hash table and the string list, because the hash array are random order.
		//Could appear null in the middle.
		int index = 0; // Keep track of the current index in the array.
		for(int i = 0; i < this.capacity(); i++) {
			if(this.hashArray[i] != null) {
				//move the cursor to the front of the chain.
				this.hashArray[i].goFirst();
				while(this.hashArray[i].itemExists()) {
					keys[index] = this.hashArray[i].item().key();
					index++;
					this.hashArray[i].goForth();
				}

			}


		}

		return keys;
	}

	/**
	 * Format the quest log as a string which displays the quests in the log in
	 * alphabetical order by name.
	 *
	 * @return A nicely formatted quest log.
	 */
	public String toString() {
		// TODO Implement this method.
		// Use keys() method to get all the names.
		String[] keys = this.keys();
		Arrays.sort(keys);

		StringBuilder result = new StringBuilder();

		//Iterate each key in the hash, and get each of them, then append them into the string builder.
		for(String key : keys) {

				QuestLogEntry entry = this.obtain(key);
				result.append(entry.toString());
				result.append("\n");

		}

		return result.toString().trim();

	}
	
	/**
	 * Obtain the quest with name k, while simultaneously returning the number of
	 * items examined while searching for the quest.
	 * @param k Name of the quest to obtain.
	 * @return A pair in which the first item is the QuestLogEntry for the quest named k, and the
	 *         second item is the number of items examined during the search for the quest named k.
	 *         Note: if no quest named k is found, then the first item of the pair should be null.
	 */
	public Pair280<QuestLogEntry, Integer> obtainWithCount(String k) {
		// TODO Implement this method.
		
		// Write a method that returns a Pair280 which contains the quest log entry with name k, 
		// and the number QuestLogEntry objects that w	ere examined in the process.  You need to write
		// this method from scratch without using any of the superclass methods (mostly because
		// the superclass methods won't be terribly useful unless you can modify them, which you
		// aren't allowed to do!).  Except for hashPos().  You're allowed to use hashPos()!


		int hashIndex = this.hashPos(k);
		int count = 0;

		//The entry that we are looking for.
		QuestLogEntry foundEntry = null;



		if(!this.hashArray[hashIndex].isEmpty()&&  this.hashArray[hashIndex] != null) {
			//reset the cursor at the beginning of the array.
			this.hashArray[hashIndex].goFirst();

			// Iterate over the linked list and count the number of examined entries.
			while(this.hashArray[hashIndex].itemExists()) {

				// Increment the count for each examined entry.
				count++;
				if(this.hashArray[hashIndex].item().key().equals(k)) {

					foundEntry = this.hashArray[hashIndex].item();

					break;
				}
				//move to the next entry if the current one is not matching the given word.
				this.hashArray[hashIndex].goForth();
			}
		}

		return new Pair280<>(foundEntry, count);
	}
	
	
	public static void main(String args[])  {
		// Make a new Quest Log
		QuestLog hashQuestLog = new QuestLog();
		
		// Make a new ordered binary lib280.tree.
		OrderedSimpleTree280<QuestLogEntry> treeQuestLog =
				new OrderedSimpleTree280<QuestLogEntry>();
		
		
		// Read the quest data from a CSV (comma-separated value) file.
		// To change the file read in, edit the argument to the FileReader constructor.
		CSVReader inFile;
		try {
			//input filename on the next line - path must be relative to the working directory reported above.
			inFile = new CSVReader(new FileReader("/Users/charlieliu/Desktop/CMPT-280/Assignment_LibFiles/A5/QuestLog/src/quests100000.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			return;
		}
		
		String[] nextQuest;
		try {
			// Read a row of data from the CSV file
			while ((nextQuest = inFile.readNext()) != null) {
				// If the read succeeded, nextQuest is an array of strings containing the data from
				// each field in a row of the CSV file.  The first field is the quest name,
				// the second field is the quest region, and the next two are the recommended
				// minimum and maximum level, which we convert to integers before passing them to the
				// constructor of a QuestLogEntry object.
				QuestLogEntry newEntry = new QuestLogEntry(nextQuest[0], nextQuest[1], 
						Integer.parseInt(nextQuest[2]), Integer.parseInt(nextQuest[3]));
				// Insert the new quest log entry into the quest log.
				hashQuestLog.insert(newEntry);
				treeQuestLog.insert(newEntry);
			}
		} catch (IOException e) {
			System.out.println("Something bad happened while reading the quest information.");
			e.printStackTrace();
		}
		
		// Print out the hashed quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
		System.out.println(hashQuestLog);
		
		// Print out the lib280.tree quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
	    System.out.println(treeQuestLog.toStringInorder());
		

		// TODO Determine the average number of elements examined during access for hashed quest log.
	    // (call hashQuestLog.obtainWithCount() for each quest in the log and find average # of access)

		String[] questNames = hashQuestLog.keys();

		int numExamination = 0;
		// Iterate over the quest names and get the average number of elements examined during access for hashed quest log.
		for(String questName : questNames) {
			Pair280<QuestLogEntry, Integer> result = hashQuestLog.obtainWithCount(questName);
			numExamination += result.secondItem();
		}
		// Divide the sum by the number of quest names to get the average.
		double averageHash = (double) numExamination / questNames.length;

		System.out.println("The average is" + averageHash);




		
		
		// TODO Determine the average number of elements examined during access for lib280.tree quest log.
	    // (call treeQuestLog.searchCount() for each quest in the log and find average # of access)
		double treeAvg = 0;

		// Iterate over the quest names and get the average number of elements examined during access for lib280.tree quest log.
		for(int i = 0; i < questNames.length; i++) {
			treeAvg = treeAvg + treeQuestLog.searchCount(hashQuestLog.obtain(questNames[i]));
		}
		// Divide the sum by the number of quest names to get the average.
		treeAvg /= questNames.length;
		// Print the average number of elements examined during access for lib280.tree quest log.
		System.out.println("average number of elements examined during access for lib280.tree quest log is " + hashQuestLog.count() + " entries: " + treeAvg);

		
	}
	
	
}
