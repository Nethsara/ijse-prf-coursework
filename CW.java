import java.util.Scanner;

class Student {
	// Creating the object
	String sid;
	String name;
	int prfMarks;
	int dbmsMarks;
	int total;
	double average;
	int rank;

	// Constructions overloading
	Student(String sid, String name) { // creating student withou marks
		this.sid = sid;
		this.name = name;
		this.prfMarks = -1;
		this.dbmsMarks = -1;
		this.total = prfMarks + dbmsMarks;
		this.average = this.total / 2;
		this.rank = 0;
	}

	Student(String sid, String name, int prfMarks, int dbmsMarks) { // Creating a student with marks
		this.sid = sid;
		this.name = name;
		this.prfMarks = prfMarks;
		this.dbmsMarks = dbmsMarks;
		this.total = prfMarks + dbmsMarks;
		this.average = this.total / 2;
		this.rank = 0;
	}

	// Equals function -> Return boolean if available
	public boolean equals(String sid) {
		return this.sid == sid;
	}

	// Setting data
	public void setData() {
		this.total = prfMarks + dbmsMarks;
		this.average = total / 2;
	}

}

public class CourseWorkObj {
	static Scanner in = new Scanner(System.in); // Importing the scanner

	/*
	 * Starting of the dependecies
	 */
	/* Clear console method */
	public final static void clearConsole() {
		try {
			final String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}

		} catch (final Exception e) {
			e.printStackTrace();
			// Handle any exceptions.
		}
	}

	/* Find the index */
	public static int findTheIndex(Student data[], String sid) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].sid.equals(sid)) {
				return i;
			}
		}
		return 0;
	}

	/* Checking whether the marks is available */
	public static boolean haveMarks(Student data[], String sid) {
		return data[findTheIndex(data, sid)].prfMarks > -1;
	}

	/* Checking whether the SID is available */
	public static boolean checkSID(Student data[], String sid) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].sid.equals(sid)) {
				return true;
			}
		}
		return false;
	}

	/* Incrementing the data */
	public static Student[] increment(Student data[]) {
		Student temp[] = new Student[data.length + 1];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		return temp;
	}

	/* Validating the marks */
	public static boolean markValidate(int marks) {
		return marks >= 0 && marks <= 100;
	}

	/* Getting Rank */
	public static void getRank(Student data[]) {
		for (int i = 0; i < data.length; i++) {
			int count = 0;
			for (int j = 0; j < data.length; j++) {
				if (data[j].total > data[i].total) {
					count++;
				}
			}
			data[i].rank = count + 1;
		}
	}
	/* End of dependencies */

	/*
	 * Starting of the functions
	 */

	/* Adding students without marks */
	public static void addNewStudent(Student data[]) {
		clearConsole();

		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("|                                  ADD NEW STUDENT                                      |");
		System.out.println("-----------------------------------------------------------------------------------------");

		boolean isRunning = true;
		do {
			System.out.print("Enter Student ID : (-1 To Terminate) > ");
			String id = in.next();
			if (id.equals("-1"))
				break;
			if (checkSID(data, id)) {
				System.out.println("Student already exists. Please try another. ");
				continue;
			}

			in.nextLine();

			System.out.print("Enter Student Name : ");
			String name = in.next();
			in.nextLine();

			data = increment(data);
			data[data.length - 1] = new Student(id, name);
			System.out.print("Do you want to continue? Y/N");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);

		menu(data);
	}

	/* Adding students with marks */
	public static void addNewStudentWithMarks(Student data[]) {
		clearConsole();

		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("|                            ADD NEW STUDENT WITH MARKS                                 |");
		System.out.println("-----------------------------------------------------------------------------------------");

		boolean isRunning = true;
		do {
			System.out.print("Enter the SID : ");
			String sid = in.next();
			if (sid.equals("-1"))
				break;
			if (checkSID(data, sid)) {
				System.out.println("Student already exists. Please try another");
				continue;
			}

			data = increment(data);

			System.out.print("Enter the name : ");
			String name = in.next();

			System.out.print("Enter PRF Marks : ");
			int prf = in.nextInt();
			while (true) {
				if (markValidate(prf)) {
					System.out.println("PRF Marks Added Successfully ");
					break;
				} else {
					System.out.print("Invalid Marks. Try again : >");
					prf = in.nextInt();
				}
			}

			System.out.print("Enter DBMS Marks : ");
			int dbm = in.nextInt();
			while (true) {
				if (markValidate(dbm)) {
					System.out.println("DBMS Marks Added Successfully ");
					break;
				} else {
					System.out.print("Invalid Marks. Try again : > ");
					dbm = in.nextInt();
				}
			}

			data[data.length - 1] = new Student(sid, name, prf, dbm);
			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);
		menu(data);
	}

	/* Adding marks to existing students */
	public static void addMarks(Student data[]) {
		clearConsole();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("|                                       ADD MARKS                                       |");
		System.out.println("-----------------------------------------------------------------------------------------");
		boolean isRunning = true;
		do {
			System.out.print("Enter the SID > ");
			String sid = in.next();

			if (!checkSID(data, sid)) {
				System.out.println("Can't find the ID. Please try again. ");
				continue;
			}
			if (haveMarks(data, sid)) {
				System.out.println("Marks already added. Please use update to update marks");
				continue;
			}

			L3: while (checkSID(data, sid)) {

				System.out.print("Enter PRF Marks : ");
				int prf = in.nextInt();
				while (true) {
					if (markValidate(prf)) {
						data[findTheIndex(data, sid)].prfMarks = prf;
						System.out.println("PRF Marks Added Successfully ");
						break;
					} else {
						System.out.print("Invalid Marks(Range > 1 - 100). Try again > ");
						prf = in.nextInt();
					}
				}

				System.out.print("Enter DBMS Marks : ");
				int dbm = in.nextInt();
				while (true) {
					if (markValidate(dbm)) {
						data[findTheIndex(data, sid)].dbmsMarks = dbm;
						data[findTheIndex(data, sid)].setData();
						System.out.println("DBMS Marks Added Successfully ");
						break L3;

					} else {
						System.out.print("Invalid Marks(Range > 1 - 100). Try again > ");
						dbm = in.nextInt();
						continue;
					}
				}
			}

			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);
		menu(data);

	}

	/* Update details */
	public static void updateDetails(Student data[]) {
		clearConsole();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out
				.println("|                                       Update DATA                                      |");
		System.out.println("-----------------------------------------------------------------------------------------");
		boolean isRunning = true;
		do {
			System.out.println("Enter student ID > ");
			String sid = in.next();

			if (!checkSID(data, sid)) {
				System.out.println("Can't find the ID. Please try again. ");
				continue;
			}

			System.out.println("Enter the new name > ");
			String name = in.next();

			data[findTheIndex(data, sid)].name = name;
			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);
		menu(data);

	}

	/* Update marks */
	public static void updateMarks(Student data[]) {
		clearConsole();
		System.out
				.println("+-----------------------------------------------------------------------------------------+");
		System.out
				.println("|                                       UDPATE MARKS                                      |");
		System.out
				.println("+-----------------------------------------------------------------------------------------+");
		boolean isRunning = true;
		do {

			System.out.print("Enter the SID > ");
			String sid = in.next();

			if (!checkSID(data, sid)) {
				System.out.println("Can't find the ID. Please try again. ");
				continue;
			}

			if (!haveMarks(data, sid)) {
				System.out.println("Marks not added");
				continue;
			}

			int index = findTheIndex(data, sid);
			String name = data[index].name;

			System.out.print("Enter the prf");
			int prf = in.nextInt();

			System.out.print("Enter the prf");
			int dbms = in.nextInt();

			data[index] = new Student(sid, name, prf, dbms);
			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);
		menu(data);
	}

	/* Deleting data */
	public static void deleteData(Student data[]) {
		clearConsole();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out
				.println("|                                       DELETE MARKS                                     |");
		System.out.println("-----------------------------------------------------------------------------------------");
		boolean isRunning = true;
		do {
			System.out.print("Enter the sid > ");
			String sid = in.next();

			if (!checkSID(data, sid)) {
				System.out.println("Can't find the ID. Please try again. ");
				continue;
			}

			Student tempAr[] = new Student[data.length - 1];
			int index = findTheIndex(data, sid);
			for (int i = index; i < data.length - 1; i++) {
				data[i] = data[i + 1];
			}

			for (int i = 0; i < tempAr.length; i++) {
				tempAr[i] = data[i];

			}
			data = tempAr;
			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);

		menu(data);

	}

	/* Print Data of the student */
	public static void printDataOfStudent(Student data[]) {
		clearConsole();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out
				.println("|                                       PRINT DATA                                       |");
		System.out.println("-----------------------------------------------------------------------------------------");
		boolean isRunning = true;
		do {
			getRank(data);
			System.out.print("Enter the SID > ");
			String sid = in.next();

			if (!checkSID(data, sid)) {
				System.out.println("Can't find the ID. Please try again. ");
				continue;
			}

			int index = findTheIndex(data, sid);

			String placeInLetters = data[index].rank == 1 ? "First"
					: data[index].rank == 2 ? "Second"
							: data[index].rank == 3 ? "Third" : data[index].rank == data.length ? "Last" : " ";
			System.out.println("+---------------------------------------+-----------------------+");

			System.out.println("|Programming Fundamental Marks\t\t|" + data[index].prfMarks + "\t\t\t|");
			System.out.println("|Dbms Marks \t\t\t\t|" + data[index].dbmsMarks + "\t\t\t|");
			System.out.println("|Total marks\t\t\t\t|" + data[index].total + "\t\t\t|");
			System.out.println("|avg.marks  \t\t\t\t|" + data[index].average
					+ "\t\t\t|");
			System.out.println("|Rank       \t\t\t\t|" + data[index].rank + " " + placeInLetters + "\t\t|");
			System.out.println("+---------------------------------------+-----------------------+");

			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;
		} while (isRunning);

		menu(data);
	}

	/* Printing all data */
	public static void printAllStudents(Student data[]) {
		getRank(data); // Generating the ranks

		/* Sorting acording to the rank */
		for (int j = 0; j < data.length; j++) {
			for (int i = 0; i < data.length - 1; i++) {
				if (data[i].rank > data[i + 1].rank) {
					Student tempID = data[i];
					data[i] = data[i + 1];
					data[i + 1] = tempID;
				}
			}
		}
		clearConsole();
		System.out.println("---------------------------------------------------------------------");
		System.out.println("|                             Printing Ranks                         |");
		System.out.println("---------------------------------------------------------------------");
		boolean isRunning = true;
		do {
			System.out.println("+-------+-------+---------------+---------------+---------------+");
			System.out.println("|Rank\t|sID    |" + "Name\t\t|" + "Total Marks\t|" + "AVG Marks \t|");
			for (int i = 0; i < data.length; i++) {
				System.out.println(
						"|" + data[i].rank + "\t" + "|" + data[i].sid + "\t" + "|" + data[i].name + "\t\t" + "|"
								+ data[i].total + "\t\t" + "|" + data[i].average + "\t\t|");
			}
			System.out.println("+-------+-------+---------------+---------------+---------------+");

			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);
		menu(data);

	}

	/* Priniting best ins PRF */
	public static void bestInPRF(Student data[]) {
		clearConsole();
		/* Sorting acording to the PRF Marks */
		for (int j = 0; j < data.length; j++) {
			for (int i = 0; i < data.length - 1; i++) {
				if (data[i].prfMarks > data[i + 1].prfMarks) {
					Student tempID = data[i];
					data[i] = data[i + 1];
					data[i + 1] = tempID;
				}
			}
		}
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("|                                       BEST IN PRF                                     |");
		System.out.println("-----------------------------------------------------------------------------------------");

		boolean isRunning = true;
		do {
			System.out.println("+-------+-----------------------+---------------+---------------+");
			System.out.println("|sID    |" + "Name\t\t\t|" + "PRF Marks\t|" + "DBMS Marks \t|");
			for (int i = data.length - 1; i >= 0; i--) {
				if (data[i].dbmsMarks == -1)
					continue;
				System.out.println(
						"|" + data[i].sid + "\t" + "|" + data[i].name + "\t\t\t" + "|" + data[i].prfMarks + "\t\t"
								+ "|" + data[i].dbmsMarks + "\t\t|");
			}
			System.out.println("+-------------------------------+---------------+---------------+");

			System.out.print("Do you want to continue? Y/N ");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);
		menu(data);

	}

	/* Printing best in DBMS */
	public static void bestInDBMS(Student data[]) {
		clearConsole();
		/* Sorting acording to the DBMS Marks */
		for (int j = 0; j < data.length; j++) {
			for (int i = 0; i < data.length - 1; i++) {
				if (data[i].dbmsMarks > data[i + 1].dbmsMarks) {
					Student tempID = data[i];
					data[i] = data[i + 1];
					data[i + 1] = tempID;
				}
			}
		}
		clearConsole();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out
				.println("|                                       BEST IN DBMS                                     |");
		System.out.println("-----------------------------------------------------------------------------------------");
		boolean isRunning = true;
		do {
			System.out.println("+-------+-----------------------+---------------+---------------+");
			System.out.println("|sID    |" + "Name\t\t\t|" + "DBMS Marks\t|" + "PRF Marks \t|");
			for (int i = data.length - 1; i >= 0; i--) {
				if (data[i].dbmsMarks == -1)
					continue;
				System.out.println(
						"|" + data[i].sid + "\t" + "|" + data[i].name + "\t\t\t" + "|" + data[i].dbmsMarks + "\t\t"
								+ "|" + data[i].prfMarks + "\t\t|");
			}
			System.out.println("+-------------------------------+---------------+---------------+");

			System.out.print("Do you want to continue? Y/N");
			isRunning = in.next().equalsIgnoreCase("y") ? true : false;

		} while (isRunning);
		menu(data);

	}

	public static void main(String args[]) {
		Student data[] = new Student[0];
		menu(data);
	}

	public static void menu(Student data[]) {
		clearConsole();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("|                     WELCOME TO GDSE MARKS MANAGEMENT SYSTEM                           |");
		System.out.println("-----------------------------------------------------------------------------------------");

		System.out.print("[1] Add New Student\t\t\t");
		System.out.println("[2] Add New Student with Marks");
		System.out.print("[3] Add Marks\t\t\t\t");
		System.out.println("[4] Update Student Details");
		System.out.print("[5] Update Marks\t\t\t");
		System.out.println("[6] Delete Student");
		System.out.print("[7] Print Student Details\t\t");
		System.out.println("[8] Print Student Ranks");
		System.out.print("[9] Best in PRF\t\t\t\t");
		System.out.println("[10] Best in DBMS\n");

		do {
			System.out.print("Enter an option to continue > ");
			int select = in.nextInt();

			switch (select) {
				case 1:
					addNewStudent(data);
					break;
				case 2:
					addNewStudentWithMarks(data);
					break;
				case 3:
					addMarks(data);
					break;
				case 4:
					updateDetails(data);
					break;
				case 5:
					updateMarks(data);
					break;
				case 6:
					deleteData(data);
					break;
				case 7:
					printDataOfStudent(data);
					break;
				case 8:
					printAllStudents(data);
					break;
				case 9:
					bestInPRF(data);
					break;
				case 10:
					bestInDBMS(data);
					break;
				default:
					System.out.println(" Please Enter A Valid Number");
			}

		} while (true);
	}
}
