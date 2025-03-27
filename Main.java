import java.util.Scanner;

public class Main
{
	public static final String[] STATEMENTS = {
		"English is my native language.",
		"My parents graduated college.",
		"I have never wondered where my next meal will come from.",
		"I have no disabilities.",
		"My work and school holidays coincide with the religious holidays I celebrate.",
		"I studied the culture and history of my ancestors in elementary school.",
		"I have never been bullied or been made fun of based on something I could not change (ie. race, ethnicity, sexual orientation, disabilities.)",
		"I have never been stopped by law enforcement due to mere suspicion as opposed to legitimate wrongdoing.",
		"I or my parents have inherited money or property.",
		"I am a US citizen.",
		"I feel safe going for a walk alone.",
		"I go by the same name I was given at birth.",
		"I am comfortable presenting my ID because it properly identifies me.",
		"My ancestors were not forced to come to the United States against their will to be enslaved.",
		"I have family or friends that can give me employment if I need it.",
		"I have never been told my natural hair looks dirty or unprofessional.",
		"I have gone to private school.",
		"I can easily find souvenirs with my name on them.",
		"I am under 40 years of age.",
		"I have at least a bachelor's degree."
	};

	public static final int PTS_PER_ANSWER = 10, TOTAL_PTS_POSSIBLE = PTS_PER_ANSWER * STATEMENTS.length,
		MAX = Person.DEFAULT_PRIVILEGE + TOTAL_PTS_POSSIBLE,
		MIN = Person.DEFAULT_PRIVILEGE - TOTAL_PTS_POSSIBLE,
		LEFT_WIDTH = Math.abs(MIN)/PTS_PER_ANSWER,
		RIGHT_WIDTH = MAX/PTS_PER_ANSWER,
		NAME_WIDTH = 20;

	private static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args)
	{
		// Person.Identity test;
		
		// DECLARATION + INITIALIZATION
		Person p1 = new Person("Amira", "she/her/hers", "I am a Syrian refugee.", 40);
		Person p2 = new Person("D'Andra", "she/her/hers", "I am an African-American trans woman.", -20);
		Person p3 = new Person("Jennifer", "ze, hir, hirs", "I am a New Yorker", 140);
		Person p4 = new Person("Pete", "he/him/his", "I am a guy from Pennsylvania", 200);
		Person self = new Person();
		Person[] people = { p1, p2, p3, p4, self };
		boolean done = false;
		int input;

		// WELCOME + INTRO
		System.out.println("Welcome to the Privilege Calculator.\n\n"
				+ "This is a small exercise that gives us a glimpse at how "
				+ "fortunate we have been in life.\n");

		Main.fillInfo(self);

		// INPUT + CALCULATION + OUTPUT
		do {
			System.out.println("\n~~~Main Menu~~~\n");
			System.out.println("1. Take questionnaire to calculate privilege estimate.");
			System.out.println("2. Check my estimate. (Defaults to " + Person.DEFAULT_PRIVILEGE
					+ " if questionnaire has not been taken.)");
			System.out.println("3. Compare my estimate with others'.");
			System.out.println("4. Exit program.");
			System.out.print("What would you like to do?\nEnter choice: ");

			input = keyboard.nextInt();
			System.out.println();

			switch (input) {
				case 1:
					// int selfPrivilege = Main.doPrivilegeQuestionnaire();
					// self.setPrivilege(selfPrivilege);
					self.setPrivilege(Main.doPrivilegeQuestionnaire());
					System.out.println("Your privilege estimate is: " + self.getPrivilege());
					System.out.println("\nReturning to main menu...\n");
					break;
				case 2:
					System.out.println(self);
					break;
				case 3:
					Main.comparePeople(people);
					System.out.println("\nReturning to main menu.\n");
					break;
				case 4:
					System.out.println("Exiting Program...\n");
					keyboard.close(); //housekeeping
					done = true;
					break;
				default:
					System.out.println("Invalid input, please enter a valid choice."
							+ "\nReturning to main menu...\n");
					break;
			}
		} while (!done);

		System.out.println("Thank you for exploring your privilege, it can be uncomfortable but it's a crucial step" +
				"in our own growth and self-reflection. We appreciate you taking that journey with us! :D");
	}
	
	public static void comparePeople(Person[] group) {
		Person user = group[group.length - 1];
		int compareResult;

		// Print privilege walk visual
		Main.printRuler();

		// for-each loop: for each person p in group (array of persons)
		// Equivalent to: for(int i = 0; i < group.length; i++) { Person p = group[i]: ... }
		for (Person p : group) {
			Main.printPrivilegeBar(p);
		}

		// Print Summary
		System.out.println("\nSummary of results above:");
		for (int i = 0; i < group.length - 1; i++) {
			compareResult = user.compareTo(group[i]);

			if (compareResult > 0) {
				System.out.println(
						"More privilege than " + group[i].getName() + " (with a difference of " + compareResult + ")" );
			} else if (compareResult == 0) {
				System.out.println("Same privilege as " + group[i].getName());
			} else {
				System.out.println(
						"Less privilege than " + group[i].getName() + " (with a difference of " + compareResult + ")" );
			}
		}
	}

	public static void printRuler() {
		int total = LEFT_WIDTH + 1 + RIGHT_WIDTH;

		// Print Key
		System.out.printf("%-" + NAME_WIDTH + "s%-" + LEFT_WIDTH + "d0%" + RIGHT_WIDTH + "d%n", "", MIN, MAX);

		// Print Ruler
		System.out.printf("%-" + NAME_WIDTH + "s", ""); // Prints blank to left of ruler
		for (int i = 0; i < total; i++) {
			System.out.print("-");
		}
		System.out.println();

		// Print Legend
		System.out.printf("%-" + NAME_WIDTH + "s%-" + LEFT_WIDTH + "s%1s%" + RIGHT_WIDTH + "s%n", "NAME(ESTIMATE):", "|", "|", "|");
	}


	public static void printPrivilegeBar(Person p) {
		// Was: System.out.printf("%s (%d) %s%n", p.getName(), p.getPrivilege(), p.getName().charAt(0));
		int privilegeEstimate = p.getPrivilege(), position = Math.abs(privilegeEstimate) / 10;
		char marker = p.getName().charAt(0);
		String label = p.getName() + " (" + privilegeEstimate + ")";

		System.out.printf("%-" + NAME_WIDTH + "s", label);

		// Left Side: Representing MIN up to 0
		if (privilegeEstimate < 0) {
			if ((LEFT_WIDTH - position) == 0) {
				System.out.printf("%-" + LEFT_WIDTH + "s", marker);
			} else {
				System.out.printf("%" + (LEFT_WIDTH - position) + "s%-" + position + "s", "", marker);
			}
		} else {
			System.out.printf("%" + LEFT_WIDTH + "s", "");
		}

		// Middle: Representing 0
		if (privilegeEstimate == 0) {
			System.out.print(marker);
		} else {
			System.out.print(" ");
		}

		// Right Side: Representing > 0 to MAX
		if (privilegeEstimate > 0) {
			if ((RIGHT_WIDTH - position) == 0) {
				System.out.printf("%" + RIGHT_WIDTH + "s", marker);
			} else {
				System.out.printf("%" + position + "s%" + (RIGHT_WIDTH - position) + "s", marker, "");
			}
		} else {
			System.out.printf("%" + RIGHT_WIDTH + "s", "");
		}

		System.out.println();
	}

	
	public static void fillInfo(Person person){
		//sets default privilege prior to questionnaire to 100
		String name, pronouns, background;
		
		System.out.print("What is your name? ");
		name = keyboard.nextLine();

		System.out.println("\nHello " + name + "!");
		System.out.println("Here is a list of common examples of preferred pronouns:");
		// from https://miracosta.edu/student-services/student-equity/lgbtqia/_docs/GenderPronounsMCCBrandedBW.pdf
		System.out.println("Gender Neutral/Nonbinary: they/them/their(s) or ze/hir/hirs");
		System.out.println("Feminine: she/her/hers");
		System.out.println("Masculine: he/him/his");
		System.out.print("What are your preferred pronouns? ");
		pronouns = keyboard.nextLine();

		System.out.println("\nPlease share a small self-identifying statement about yourself "
				+ "and your background and identity, this can be anything you like!\n"
				+ "For example: I'm a [nationality / place of origin / ethnicity / sexuality / gender expression / etc.]...");
		System.out.print("Tell us about yourself: ");
		background = keyboard.nextLine();
		
		person.setName(name);
		person.setPronouns(pronouns);
		person.setBackground(background);
	}

	public static int doPrivilegeQuestionnaire() {
		boolean isValid;
		int choice, privilegeEstimate = Person.DEFAULT_PRIVILEGE;
	
		System.out.println("Please indicate whether the following statements are true or false.\n"
				+ "Input 1 or 2 accordingly.\n");
		
		for(int i = 0; i < STATEMENTS.length; i++) 
		{
			isValid = false;
			do{
				System.out.println(STATEMENTS[i]);
				System.out.print("1. True. \n2. False.\nEnter the appropriate answer: ");
				choice = keyboard.nextInt();
				System.out.println();

				switch (choice)
				{
					case 1:
						privilegeEstimate += PTS_PER_ANSWER;
						isValid = true;
						break;
					case 2:
						privilegeEstimate -= PTS_PER_ANSWER;
						isValid = true;
						break;
					default:
						System.out.println("Invalid choice, please make sure to enter 1 or 2.");
						break;			
				}
			}while(!isValid);
		}
		
		return privilegeEstimate;
	}
}