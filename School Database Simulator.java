import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

class FormatException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FormatException() {
        super(">>>>>>>>>>>Sorry incorrect format. (Ids are 7 digits): ");
    }
}

abstract class Person{
	private int UCFID;
	private String name;
	
	public Person(int UCFID, String name) {
		this.UCFID  = UCFID;
		this.name = name;
	}
	
	public int getUCFID() {
		return UCFID;
	}
	public void setUCFID(int UCFID) {
		this.UCFID  = UCFID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UCFID: " + UCFID + ", Name: " + name;
	}

}

class Student extends Person{
	private String underorgrad;
	private List<Course> currentCourses;
	private List<Lab> currentLabs;
	
	public Student(int UCFID, String name, String underorgrad) {
		super(UCFID, name);
		this.underorgrad = underorgrad;
		currentCourses = new ArrayList<>();
		currentLabs = new ArrayList<>();
	}
	
	public Student(int UCFID, String name, String underorgrad, List<Course> currentCourses, List<Lab> currentLabs) {
		super(UCFID, name);
		this.underorgrad = underorgrad;
		this.currentCourses = currentCourses;
		this.currentLabs = currentLabs;
	}

	public void addLab(Lab lab) {
        currentLabs.add(lab);
    }
	public void addCourse(Course course) {
        currentCourses.add(course);
    }
	public void removeCourse(Course course) {
	    currentCourses.remove(course);
	}
	public void removeLab(Lab lab) {
	    currentLabs.remove(lab);
	}
	public String getunderorgrad() {
		return underorgrad;
	}
	public void setunderorgrad(String underorgrad) {
		this.underorgrad = underorgrad;
	}
	public List<Course> getcurrentCourses() {
		return currentCourses;
	}
	public void setcurrentCourses(List<Course> currentCourses) {
		this.currentCourses = currentCourses;
	}
	public List<Lab> getcurrentLabs() {
		return currentLabs;
	}
	public void setcurrentLabs(List<Lab> currentLabs) {
		this.currentLabs = currentLabs;
	}

	@Override
	public String toString() {
		return "Student : " + super.toString() + ", Graduate or Undergraduate: " + underorgrad;
	}
	
}

class Faculty extends Person{
	private String rank;
	private String officeroom;
	private List<Course> lectures;
	
	public Faculty(int UCFID, String name, String rank, String officeroom) {
		super(UCFID, name);
		this.rank = rank;
		this.officeroom = officeroom;
		lectures = new ArrayList<>();
	}
	
	public void addCourse(Course course) {
        lectures.add(course);
    }
	public void removeCourse(Course course) {
	    lectures.remove(course);
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getofficeroom() {
		return officeroom;
	}
	public void setofficeroom(String officeroom) {
		this.officeroom = officeroom;
	}
	public List<Course> getlectures() {
		return lectures;
	}
	public void setlectures(List<Course> lectures) {
		this.lectures = lectures;
	}

	@Override
	public String toString() {
		return "Faculty: " + super.toString() + ", Rank: " + rank + ", officeroom: " + officeroom;
	}
	
}

class TA extends Person{
	private String supervisorName;
	private String seekingDegree;
	private List<Lab> supervisingLabs;
	private List<Course> currentCourses;
	private List<Lab> currentLabs;
	
	public TA(int UCFID, String name, String supervisorName, String seekingDegree) {
		super(UCFID, name);
		this.supervisorName = supervisorName;
		this.seekingDegree = seekingDegree;
		supervisingLabs = new ArrayList<>();
		currentCourses = new ArrayList<>();
		currentLabs = new ArrayList<>(); 	
	}

	public TA(int UCFID, String name, String supervisorName, String seekingDegree, List<Course> currentCourses, List<Lab> currentLabs) {
		super(UCFID, name);
		this.supervisorName = supervisorName;
		this.seekingDegree = seekingDegree;
		this.currentCourses = currentCourses;
		this.currentLabs = currentLabs;
		supervisingLabs = new ArrayList<>();
	}

	public void addLabSupervising(Lab lab) {
        supervisingLabs.add(lab);
    }
	public void addLab(Lab lab) {
        currentLabs.add(lab);
    }
	public void addCourse(Course course) {
        currentCourses.add(course);
    }
	public void removeLabSupervising(Lab lab) {
        supervisingLabs.remove(lab);
    }
	public void removeCourse(Course course) {
	    currentCourses.remove(course);
	}
	public void removeLab(Lab lab) {
	    currentLabs.remove(lab);
	}
	public String getsupervisorName() {
		return supervisorName;
	}
	public void setsupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getseekingDegree() {
		return seekingDegree;
	}
	public void setseekingDegree(String seekingDegree) {
		this.seekingDegree = seekingDegree;
	}
	public List<Lab> getsupervisingLabs() {
		return supervisingLabs;
	}
	public void setsupervisingLabs(List<Lab> supervisingLabs) {
		this.supervisingLabs = supervisingLabs;
	}
	public List<Course> getcurrentCourses() {
		return currentCourses;
	}
	public void setcurrentCourses(List<Course> currentCourses) {
		this.currentCourses = currentCourses;
	}
	public List<Lab> getcurrentLabs() {
		return currentLabs;
	}
	public void setcurrentLabs(List<Lab> currentLabs) {
		this.currentLabs = currentLabs;
	}

	@Override
	public String toString() {
		return "TA: " + super.toString() + ", supervisorName: " + supervisorName + ", seekingDegree: " + seekingDegree;
	}
	
}

abstract class Course{
	private int crn;
	private String prefix, lectureName, gradUngrad, buildingNum, roomNum, online, lab;
	private boolean hasLabs = false;
	
	// Constructor for in-person course
	public Course(int crn, String prefix, String lectureName, String gradUngrad, String buildingNum, String roomNum, String lab, Boolean hasLabs) {
		super();
		this.crn = crn;
		this.prefix = prefix;
		this.lectureName = lectureName;
		this.gradUngrad = gradUngrad;
		this.buildingNum = buildingNum;
		this.roomNum = roomNum;
		this.lab = lab;
		this.hasLabs = hasLabs;
	}
	
	// Constructor for online course
	public Course(int crn, String prefix, String lectureName, String gradUngrad, String online) {
		super();
		this.crn = crn;
		this.prefix = prefix;
		this.lectureName = lectureName;
		this.gradUngrad = gradUngrad;
		this.online = online;
	}

	// Getters and setters
	public int findCrn(int crn) {
		return crn;
	}
	public int getCrn() {
		return crn;
	}
	public void setCrn(int crn) {
		this.crn = crn;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getlectureName() {
		return lectureName;
	}
	public void setlectureName(String lectureName) {
		this.lectureName = lectureName;
	}
	public String getGradUngrad() {
		return gradUngrad;
	}
	public void setGradUngrad(String gradUngrad) {
		this.gradUngrad = gradUngrad;
	}
	public String getbuildingNum() {
		return buildingNum;
	}
	public void setbuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	public String getroomNum() {
		return roomNum;
	}
	public void setroomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public String getOnline() {
		return online;	
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getLab() {
		return lab;
	}
	public void setLab(String lab) {
		this.lab = lab;
	}
	public boolean hasLabs() {
		return hasLabs;
	}
	public void sethasLabs(boolean hasLabs) {
		this.hasLabs = hasLabs;
	}

	@Override
	public String toString() {
		return  "[" + crn + "," + prefix + "," + lectureName + "]";
	}
	
	public String printingString() {
		if (online == null) {
			return  crn + "," + prefix + "," + lectureName + "," + gradUngrad + "," + buildingNum + "," + roomNum + "," + lab ;
		}
		else {
			return  crn + "," + prefix + "," + lectureName + "," + gradUngrad + "," + online ;
		}
	}

}

class OnlineCourse extends Course{

	public OnlineCourse(int crn, String prefix, String lectureName, String gradUngrad, String online) {
		super(crn, prefix, lectureName, gradUngrad, online);
	}
	
}

class InPersonCourse extends Course{
	
	private ArrayList<Lab> labs;

	public InPersonCourse(int crn, String prefix, String lectureName, String gradUngrad, String buildingNum,
			String roomNum, String labString, Boolean hasLabs) {
		super(crn, prefix, lectureName, gradUngrad, buildingNum, roomNum, labString, hasLabs);
		if (this.hasLabs()) {
            labs = new ArrayList<Lab>();
        }
	}
	
	public void addLab(int crn, String roomNum) {
        labs.add(new Lab(crn, roomNum));
    }
	public ArrayList<Lab> getLabs() {
		return labs;
	}
	public void setLabs(ArrayList<Lab> labs) {
		this.labs = labs;
	}
	
}

class Lab {
	private int crn;
	private String roomNum;
	
	// Constructor for lab
	public Lab(int crn, String roomNum) {
		this.crn = crn;
		this.roomNum = roomNum;
		
	}

	public int getCrn() {
		return crn;
	}
	public void setCrn(int crn) {
		this.crn = crn;
	}
	public String getroomNum() {
		return roomNum;
	}
	public void setroomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	@Override
	public String toString() {
		return "[" + crn + "," + roomNum + "]";
	}
	
	public String printintString() {
		return crn + "," + roomNum;
	}
}

class courses {

    private List<Course> courses;
    private boolean alter;

	public courses() {
        courses = new ArrayList<>();
    }

    public List<Course> getcourses() {
        return courses;
    }

    public void setcourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
            courses.add(course);
    }
    
    public Course getCourse(int index) {
        return courses.get(index);
    }
    public Course getCourseThroughCrn(int crn) {
        for (Course course : courses) {
            if (course.getCrn() == crn) {
                return course;
            }
        }
        return null; 
    }
    public int size() {
        return courses.size();
    }
    public boolean isAlter() {
        return alter;
    }
    public void setAlter(boolean alter) {
		this.alter = alter;
	}
    
}

class people{
	List<Person> people;

	public people() {
		people = new ArrayList<>();
	}

	public List<Person> getpeople() {
		return people;
	}
	public void setpeople(List<Person> people) {
		this.people = people;
	}
	
	public void addPerson(Person person) {
        people.add(person);
	}
	public Person getPersonThroughID(int ID) {
        for (Person person : people) {
            if (person.getUCFID() == ID) {
                return person;
            }
        }
        return null; 
    }
	public int size() {
        return people.size();
    }
		
}

public class FinalProject {
	
	static Scanner scanWords = new Scanner(System.in);
	static Scanner numberScan = new Scanner(System.in);

	public static void menu(people people, courses courses) {
		int userInput = 0;
		
		boolean stopLoop = false;
		boolean stopTry = false;
		
		do {
			
			System.out.println("\nChoose one of these options: \n");
			System.out.println("\t1 - Add a new Faculty to the schedule");
			System.out.println("\t2 - Enroll a Student to a Lecture");
			System.out.println("\t3 - Print the schedule of a Faculty");
			System.out.println("\t4 - Print the schedule of an TA");
			System.out.println("\t5 - Print the schedule of a Student");
			System.out.println("\t6 - Delete a Lecture");
			System.out.println("\t7 - Exit");
			System.out.print("\n\t\tEnter your choice: ");
			
			do {
				try {
					userInput = numberScan.nextInt();
					numberScan.nextLine();
					if(userInput > 7 || userInput < 1)
						throw new IllegalArgumentException();
					stopTry = true;
				} catch (InputMismatchException e) {
					stopTry = false;
					System.out.print("\nOops that's not a valid option, Please try again: ");
					numberScan.nextLine();
				} catch (IllegalArgumentException e) {
					stopTry = false;
					System.out.print("\nPlease enter an option that is 1-7:  ");
				}
			} while (stopTry == false);
			
			switch (userInput) {
			case 1: {
				
				stopLoop = false;
				addFaculty(people, courses);
				break;
			} 
			case 2: {
				stopLoop = false;
				enrollStudent(people, courses);
				break;
			}
			case 3: {
				stopLoop = false;
				facultySchedulePrint(people, courses);
				break;
			} 
			case 4: {
				stopLoop = false;
				TAScheduleprint(people, courses);
				break;
			}
			case 5: {
				stopLoop = false;
				studentSchedulePrint(people, courses);
				break;
			}
			case 6: {
				stopLoop = false;
				deleteLecture(people, courses);
				break;
			} 
			case 7: {
				exit(courses);
				stopLoop = true;
				break;
			}
			
			default:
				stopLoop = false;
				break;
			}
			
		}while(stopLoop == false);
		
	}
	
	public static void courses(courses courses)  throws FileNotFoundException{
		String [] courseInformation = null;	
		String buffer, prefix, lectureName, gradUngrad, buildingNum, roomNum, labString, online, fileInput;
		buffer = prefix = lectureName = gradUngrad = buildingNum = roomNum = labString = fileInput = null;
		int crn = 0;
		boolean hasLabs = false;
		boolean stopLoop = false;
		boolean readLabs = false;
				
		Scanner fileScan;
				
		System.out.print("Enter the absolute path of the file: ");
		
		do {
			try {
				fileInput = scanWords.nextLine();
				fileScan = new Scanner(new File(fileInput));
				stopLoop = true;
			} catch (FileNotFoundException e) {
				System.out.println("\nSorry no such file.");
				System.out.print("\nTry again: ");
				stopLoop = false;
			}
		} while (stopLoop == false);
					
		fileScan = new Scanner(new File(fileInput));
		
		while(fileScan.hasNextLine()) {
						
			buffer = fileScan.nextLine();
			courseInformation = buffer.split(",");
			
			if (readLabs) {
                if (courseInformation.length != 2) {
                    readLabs = false;
                } else {
                    // Get Lab CRN and Room Number
                    crn = Integer.parseInt(courseInformation[0].trim());
                    roomNum = courseInformation[1].trim();
                    // Add Lab To Last Lecture
                    Course lastCourse = courses.getCourse(courses.size() - 1);
                    if (lastCourse instanceof InPersonCourse) {
                        ((InPersonCourse) lastCourse).addLab(crn, roomNum);
                    }
                    continue;
                }
            }
			
			crn = Integer.parseInt(courseInformation[0]);
			prefix = courseInformation[1];
			lectureName = courseInformation[2];
			gradUngrad = courseInformation[3];
						
			if(courseInformation[4].trim().equalsIgnoreCase("ONLINE")) {
				online = courseInformation[4];
						
				OnlineCourse course = new OnlineCourse(crn, prefix, lectureName, gradUngrad, online);
				courses.addCourse(course);
							
			} else {
						
				buildingNum = courseInformation[4];
				roomNum = courseInformation[5];
				labString = courseInformation[6];
				hasLabs = false;
							
				if (courseInformation[6].trim().equalsIgnoreCase("YES")){
							
					hasLabs = true;
					readLabs = true;
							
				}
				
				InPersonCourse course = new InPersonCourse(crn, prefix, lectureName, gradUngrad, buildingNum, roomNum, labString, hasLabs);			
				courses.addCourse(course);
							
			} 
						
						
		}
		
		System.out.println("\nFile Found! Let's proceed..." + "\n\n*****************************************");			
	}
	
	public static boolean searchID(people people, int UCFID) {
		for (Person person : people.getpeople()) {
			if (person instanceof Person && person.getUCFID() == UCFID) {
		        return true;
		    }
		}
		return false;
	}
	
	public static boolean searchFacultyID(people people, int UCFID) {
		for (Person person : people.getpeople()) {
			if (person instanceof Faculty && person.getUCFID() == UCFID) {
		        return true;
		    }
		}
		return false;
	}
	
	public static String seekingDegreeWhile(String input) {
		
		while(!input.equalsIgnoreCase("ms") && !input.equalsIgnoreCase("phd")){
				 System.out.printf("\nOops! That's not a valid entry. Please try again: ");
				 input = scanWords.nextLine();	
			 }
		return input;
	}
	
	public static String yesNoWhile(String input) {
		
		while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")
			  && !input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
				 
				 System.out.printf("\nIs that a yes or no? Enter y/Y for Yes or n/N for No: ");
				 input = scanWords.nextLine();	
			 }
		return input;
	}
	
	public static boolean isCourseAlreadyAssigned(people people, Course course) {
	    for (Person person : people.getpeople()) {
	        if (person instanceof Faculty) {
	            List<Course> lectures = ((Faculty) person).getlectures();
	            if (lectures.contains(course)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	public static Person convertTA(people people, Student student, String supervisorName, String seekingDegree, int UCFID) {
	    List<Course> currentCourses = student.getcurrentCourses();
	    List<Lab> currentLabs = student.getcurrentLabs();

	    Person person = new TA(UCFID, student.getName(), supervisorName, seekingDegree, currentCourses, currentLabs);
	    
	    return person;
	}
	
	public static Person convertToStudent(people people, TA ta) {
	    List<Course> currentCourses = ta.getcurrentCourses();
	    List<Lab> currentLabs = ta.getcurrentLabs();

	    Person person = new Student(ta.getUCFID(), ta.getName(), "Graduate" ,currentCourses, currentLabs);
	    
	    return person;
	}
	
	private static boolean hasStudentsOrTA(people people) {
		for (Person person : people.getpeople()) {
		    if (person instanceof Student || person instanceof TA) {
		        return true;
		    }
		}
		return false;
	}
	
	private static boolean hasTAs(people people) {
		for (Person person : people.getpeople()) {
		    if (person instanceof TA) {
		        return true;
		    }
		}
		return false;
	}
	
	private static boolean hasFaculty(people people) {
		for (Person person : people.getpeople()) {
		    if (person instanceof Faculty) {
		        return true;
		    }
		}
		return false;
	}
	
	public static void addTA(people people, courses courses, Lab lab, Course course) {

		String UCFIDString, name, supervisorName, seekingDegree;
		UCFIDString = name = supervisorName = seekingDegree = null;
		int UCFID = 0;
		boolean stopTry = false;
		boolean foundStudent = false;
		
		
		do {
			try {
				UCFIDString = scanWords.nextLine();
				if(UCFIDString.length() != 7){
					throw new FormatException();
				}	
				
				UCFID = Integer.parseInt(UCFIDString);
				
				for (Person person : people.getpeople()) {
			        
			        if (person instanceof Student && person.getUCFID() == UCFID) {
			        	
			        	for (Course studentCourse : ((Student) person).getcurrentCourses()) {
			                if (studentCourse == course) {
			                    throw new IllegalArgumentException("\nA student cannot be a TA for a class they are enrolled in. ");
			                }
			            }
			        	
			        	System.out.println("\nTA found as a student: " + person.getName());
				        name = person.getName();
				        foundStudent = true;
				        break;
				        
			        } else if (person instanceof Faculty && person.getUCFID() == UCFID) {
			        	 throw new IllegalArgumentException("\nFaculty cannot be a TA for a course. ");
			        } else if (person instanceof TA && person.getUCFID() == UCFID) {
			        	foundStudent = false;
			        	break;
			        }
			    }
				
				stopTry = true;
				
			} catch (FormatException e) {
					System.out.print(e.getMessage());
			} catch (NumberFormatException e) {
		        System.out.println("Invalid input. Please enter a 7-digit UCF ID: ");
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage() + "Please enter a 7-digit UCF ID: ");
			}
			
		} while (stopTry == false);
		
		for (Person person : people.getpeople()) {
			if (person instanceof TA && person.getUCFID() == UCFID) {
				((TA) person).addLabSupervising(lab);
		        return;
		    }
		}
		 
		if (!foundStudent) {
	        System.out.print("\nName of TA: ");
	        name = scanWords.nextLine();
	        
	        System.out.print("\nTA’s supervisor’s name: ");
		    supervisorName = scanWords.nextLine();
		    
		    System.out.print("\nDegree Seeking: ");
		    seekingDegree = scanWords.nextLine();
		    seekingDegree = seekingDegreeWhile(seekingDegree);
		    
		    Person person = new TA(UCFID, name, supervisorName, seekingDegree);
		    people.addPerson(person);
		    ((TA) person).addLabSupervising(lab);
		    
	    } else {
	    	System.out.print("\nTA’s supervisor’s name: ");
		    supervisorName = scanWords.nextLine();
		    
		    System.out.print("\nDegree Seeking: ");
		    seekingDegree = scanWords.nextLine();
		    seekingDegree = seekingDegreeWhile(seekingDegree);
		    
		    ListIterator<Person> Iterator = people.getpeople().listIterator();
		    while (Iterator.hasNext()) {
		        Person person = Iterator.next();
		        if (person instanceof Student && person.getUCFID() == UCFID) {
		            Person ta = convertTA(people, (Student) person, supervisorName, seekingDegree, UCFID);
		            ((TA) ta).addLabSupervising(lab);
		            Iterator.remove(); // Remove the current element from the list
		            Iterator.add(ta); // Add the new element to the list
		        }
		    }
	    } 
	}
	
	private static Student addStudent(people people, int UCFID) {
	
		String name, underorgrad;
		name = underorgrad = null;
		
		System.out.print("\nEnter name: ");
		name = scanWords.nextLine();
		
		System.out.print("\nEnter student status (Graduate or Undergraduate): ");
		underorgrad = scanWords.nextLine();
		
		while(!underorgrad.equalsIgnoreCase("graduate") && !underorgrad.equalsIgnoreCase("undergraduate")) {
			 System.out.printf("\nOops! That's not a valid entry. Please try again: ");
			 underorgrad = scanWords.nextLine();	
		}
		
		Person person = new Student(UCFID, name, underorgrad);
		people.addPerson(person);
		
		System.out.println("\nStudent Added!");
		
		
		return (Student) person;
		
	}
	
	public static void addFaculty(people people, courses courses) {
		
	
		int UCFID = 0, numberOfLectures = 0;
		String UCFIDString, name, rank, officeroom, crnBuffer;
		UCFIDString = name = rank = officeroom = crnBuffer = null;
		boolean stopTry = false;
		boolean foundFaculty = false;
		
		System.out.print("\nEnter UCF id: ");
		
		do {
			try {
				UCFIDString = scanWords.nextLine();
				if(UCFIDString.length() != 7){
					throw new FormatException();
				}	
				
				UCFID = Integer.parseInt(UCFIDString);
				
				if(searchFacultyID(people, UCFID)) {
					foundFaculty = true;
				}
				
				stopTry = true;
				
			} catch (FormatException e) {
					System.out.print(e.getMessage());
			} catch (NumberFormatException e) {
		        System.out.print("\nInvalid input. Please enter a 7-digit UCF ID: ");
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
			
		} while (stopTry == false);
		
		
		
		if(!foundFaculty) {
			System.out.print("\nEnter name: ");
			name = scanWords.nextLine();
			
			System.out.print("\nEnter rank: ");
			rank = scanWords.nextLine();
			
			while(!rank.equalsIgnoreCase("professor") && !rank.equalsIgnoreCase("associate professor")
				 && !rank.equalsIgnoreCase("assistant professor") && !rank.equalsIgnoreCase("adjunct professor") 
				 && !rank.equalsIgnoreCase("adjunct")) {
				 System.out.printf("\nOops! That's not a valid entry. Please try again: ");
				 rank = scanWords.nextLine();	
			}
			
			System.out.print("\nEnter office location: ");
			officeroom = scanWords.nextLine();
			
			Person person = new Faculty(UCFID, name, rank, officeroom);
			people.addPerson(person);
			
		} else {
			Person person = people.getPersonThroughID(UCFID);
			System.out.println("\nFound faculty memeber: " + person.getName());
		}
		
		System.out.print("\nEnter how many lectures: ");
		
		stopTry = false;
		do {
			try {
				numberOfLectures = numberScan.nextInt();
				numberScan.nextLine();
				if(numberOfLectures > 50) {
					throw new IllegalArgumentException("\nToo many lectures. Please try again: ");
				}
				stopTry = true;
			} catch (InputMismatchException e) {
				System.out.printf("\nOops! That's not a valid entry. Please try again: ");
				numberScan.nextLine();
			}
			catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
		} while (stopTry == false);

		List<Course> lectures = new ArrayList<>();
		int [] Crns = new int [numberOfLectures];
		
		System.out.print("\nEnter the crns of the lectures: ");
		
		stopTry = false;
		do {
			try {
				
				crnBuffer = scanWords.nextLine();
	
				for(int i = 0; i < numberOfLectures; i++){
					String[] splitCrns = crnBuffer.split(" ");
					Crns[i] = Integer.parseInt(splitCrns[i]);
					
					if(!courses.getcourses().contains(courses.getCourseThroughCrn(Crns[i]))) {
						throw new IllegalArgumentException("\nOops! That crn doesn't exist. Please enter a valid crn: ");
					}
					if(!isCourseAlreadyAssigned(people, (courses.getCourseThroughCrn(Crns[i])))) {
						lectures.add(courses.getCourseThroughCrn(Crns[i]));
					} 
					else throw new IllegalArgumentException("\nSomeone already is already assigned that crn. Please enter another crn: ");
					
				}
				stopTry = true;
			
			} catch (IllegalArgumentException e) {
				System.out.printf(e.getMessage());
				
			}
		}while (stopTry == false);
		
		
		Person person = people.getPersonThroughID(UCFID);
		
		
		for (int i = 0; i < numberOfLectures; i++) {
		    Course course = lectures.get(i);
		    ((Faculty) person).addCourse(course);
		    if (!course.hasLabs()) {
		        System.out.println("\n" +course + " Added!");
		    }
		}
		
		for(int i = 0; i < numberOfLectures; i++) {
		    Course course = lectures.get(i);
		    
		    if(course.hasLabs()) {
		    	System.out.println("\n" + course + " has these labs: ");
		    	InPersonCourse inPerson = (InPersonCourse) course;
		    	
		    	inPerson.getLabs().forEach(lab -> System.out.println("\t" + lab.toString()));
		    	
		    	for (Lab lab : inPerson.getLabs()) {
				    System.out.print("\nEnter the TA’s id for " + lab.getCrn() + ": ");

				    addTA(people, courses, lab, course);
				}
		    	
		    	System.out.println("\n" + course + " Added!");
		    	
		    } 
		   
		}
		
		System.out.println("\n*****************************************");
			
	}
	
	 
	public static void enrollStudent(people people, courses courses) {
		
		String UCFIDString = null, studentName = null;
		int UCFID = 0, lectureID = 0;
		boolean stopTry = false, foundStudent = false;
		
		System.out.print("\nEnter UCF id: ");
		
		do {
			
			try {
				UCFIDString = scanWords.nextLine();
				if(UCFIDString.length() != 7){
					throw new FormatException();
				}	
				
				UCFID = Integer.parseInt(UCFIDString);
				
				
				for (Person person : people.getpeople()) {
			        if (person instanceof Student && person.getUCFID() == UCFID) {
			            foundStudent = true;
			            break;
			        } else if (person instanceof Faculty && person.getUCFID() == UCFID) {
			        	 throw new IllegalArgumentException("\nFaculty cannot be enrolled in a course. ");
			        } else if (person instanceof TA && person.getUCFID() == UCFID) {
			        	foundStudent = true;
			        	break;
			        }
			    }
				
				if (!foundStudent) {
					 System.out.println("\nNo student found with id: " +  UCFIDString );
					 System.out.print("\nWould you like to add a student? (y/n): ");
					 String addStudent = scanWords.nextLine();
					 addStudent = yesNoWhile(addStudent);
					 if(addStudent.equalsIgnoreCase("yes") || addStudent.equalsIgnoreCase("y")){
						 Student student = addStudent(people, UCFID);
						 System.out.print("\nWould you like to contine where you left off? (y/n): ");
						 String userInput = scanWords.nextLine();
						 userInput = yesNoWhile(userInput);
						 if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
							 UCFID = student.getUCFID();
							 break;
						 }
						 else return;
					 }
					 else return;
					 
			    }
				
				stopTry = true;
				
			} catch (FormatException e) {
				System.out.print(e.getMessage());	
			} catch (NumberFormatException e) {
		        System.out.println("Invalid input. Please enter a 7-digit UCF ID: ");
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage() + "\nWould you like to re-enter a student ID ? (y/n): ");
				String userInput = scanWords.nextLine();
				userInput = yesNoWhile(userInput);
				if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")){
					System.out.print("\nPlease enter a student's 7-digit UCF ID: ");
				}
				else return;
			}
			
		} while (stopTry == false);
		
		Person person = people.getPersonThroughID(UCFID);
		if (person instanceof Student && person.getUCFID() == UCFID || person instanceof TA && person.getUCFID() == UCFID) {
	        studentName = person.getName();
	        System.out.println("\nRecord found/Name: " + studentName);
	    }
		
		System.out.print("\nWhich lecture to enroll [" + studentName + "] in? ");
		
		stopTry = false;
		do {
			try {
				lectureID = numberScan.nextInt();
				numberScan.nextLine();
				
				Course course = courses.getCourseThroughCrn(lectureID);
				
				if(course == null || !courses.getcourses().contains(course)) {
					throw new IllegalArgumentException("\nThat crn doesn't exist. ");
				}
				
				if (person instanceof Student && person.getUCFID() == UCFID ) {
			        Student student = (Student) person;
			        if (student.getcurrentCourses().contains(course)) {
			            throw new IllegalArgumentException("\n" + student.getName() + " is already enrolled in " + course.getCrn());
			        }
			    } else if (person instanceof TA && person.getUCFID() == UCFID) {
			        TA ta = (TA) person;
			        if (ta.getcurrentCourses().contains(course)) {
			            throw new IllegalArgumentException("\n" + ta.getName() + " is already enrolled in " + course.getCrn());
			        }
			        if(course.hasLabs()) {
			        	ArrayList<Lab> labs = ((InPersonCourse) course).getLabs();
			        	for (int i = 0; i < labs.size(); i++) {
			        		Lab lab = labs.get(i);
			        		if (ta.getsupervisingLabs().contains(lab)) {
			        			throw new IllegalArgumentException("\n" + ta.getName() + " is already a TA for course [" + course.getCrn() + "]");
			        		}
			        	}
			        }
			        
			    }
				
				stopTry = true;
				
			} catch (InputMismatchException e) {
				System.out.print("\nOops! That's not a valid crn. Please enter a valid crn: ");
				numberScan.nextLine();
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.out.print("\nWould you like to re-enter a crn? (y/n): ");
				String userInput = scanWords.nextLine();
				userInput = yesNoWhile(userInput);
				if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")){
					System.out.print("\nPlease enter a crn: ");
				}
				else {
					return;
				}

			} 
			
		} while(stopTry == false);
		
		
		Course course = courses.getCourseThroughCrn(lectureID);
		    
		if(course.hasLabs()) {
			System.out.println("\n" + course + " has these labs: ");
		    InPersonCourse inPerson = (InPersonCourse) course;
		    inPerson.getLabs().forEach(lab -> System.out.println("\t" + lab.toString()));
		     
		    List<Lab> labs = inPerson.getLabs();
		    int randomIndex = new Random().nextInt(labs.size());
		    Lab randomLab = labs.get(randomIndex);
		    
		    if (person instanceof Student && person.getUCFID() == UCFID) {
		    	((Student) person).addCourse(course);
		    	((Student) person).addLab(randomLab);
			}
		    if (person instanceof TA && person.getUCFID() == UCFID) {
				((TA) person).addCourse(course);
				((TA) person).addLab(randomLab);
			}
		    	      
			System.out.println("\n[" + studentName + "] is added to lab: " + randomLab.getCrn());
		     
		} else {
			
			if (person instanceof Student && person.getUCFID() == UCFID) {
		    	((Student) person).addCourse(course);
			}
		    if (person instanceof TA && person.getUCFID() == UCFID) {
		    	((TA) person).addCourse(course);
			}
		    
			 System.out.println("\n" + course);
		}
		
		System.out.println("\nStudent enrolled!");
		System.out.println("\n*****************************************");

	}
	
	public static void facultySchedulePrint(people people, courses courses) {
		
		String UCFIDString = null;
		int UCFID = 0;
		boolean stopTry = false;
		
		if (people == null || people.size() == 0 || !hasFaculty(people)) {
			System.out.println("\nThere is no Faculty in the list.");
			return;
		}
		
	    System.out.print("\nEnter the UCF id: ");
	    
		do {
			try {
				UCFIDString = scanWords.nextLine();
				if(UCFIDString.length() != 7){
					throw new FormatException();
				}	
				
				UCFID = Integer.parseInt(UCFIDString);
				
				if(!searchID(people, UCFID)) {
					throw new IllegalArgumentException("\nSorry no Faculty found. ");
				}
				Person person = people.getPersonThroughID(UCFID);
				
			    if (person instanceof Student && person.getUCFID() == UCFID ) {
			    	System.out.println("\nStudent schudule cannot be printed here. ");
			    	System.out.print("\nWould you like to be redirected (y/n): ");
			    	String userInput = scanWords.nextLine();
			    	userInput = yesNoWhile(userInput);
			    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
			    		studentSchedulePrint(people, courses);
			    		return;
					} else {
						throw new IllegalArgumentException();
					}
			    } 
			    else if (person instanceof TA && person.getUCFID() == UCFID ) {
			    	System.out.println("\nTA schudule cannot be printed here. ");
			    	System.out.print("\nWould you like to be redirected (y/n): ");
			    	String userInput = scanWords.nextLine();
			    	userInput = yesNoWhile(userInput);
			    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
			    		TAScheduleprint(people, courses);
			    		return;
					} else {
						throw new IllegalArgumentException();
					}
			    } 
			    
				stopTry = true;
				
			} catch (FormatException e) {
					System.out.print(e.getMessage());
			} catch (NumberFormatException e) {
		        System.out.print("\nInvalid input. Please enter a 7-digit UCF ID: ");
			} catch (IllegalArgumentException e) {
				if(e.getMessage() != null) System.out.println(e.getMessage());
				System.out.print("\nWould you like to enter another ID (y/n): ");
		    	String userInput = scanWords.nextLine();
		    	userInput = yesNoWhile(userInput);
		    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
		    		System.out.print("\nPlease enter a Faculty's 7-digit UCF ID: ");
		    	} 
		    	else {
					return;
				}
				
			}
			
		} while (stopTry == false);
	    
		Person person = people.getPersonThroughID(UCFID);
	    Faculty faculty = (Faculty) person;
	    
	    List<Course> lectures = faculty.getlectures();
	    if (lectures.size() == 0) {
	        System.out.println("\n\tNo lectures found for " + person.getName() + ".");
	    } else {
	    	System.out.println("\n" + person.getName() + " is teaching the following lectures:");
	        for (Course course : lectures) {
	            System.out.print("\n\t" + course);
	            if(course.hasLabs()) {
	            	ArrayList<Lab> labs = ((InPersonCourse) course).getLabs();
                    System.out.print(" with Labs:\n");
                    for (int i = 0; i < labs.size(); i++) {
                        Lab lab = labs.get(i);
                        System.out.println("\t" + lab);
                    }
	            } else if(course.getOnline() != null) {
	            	System.out.print("[" + course.getOnline() + "]\n");
	            }
	        }
	        
	    }
	    
	    System.out.println("\n*****************************************");
	    
		
	}
	
	public static void TAScheduleprint(people people, courses courses) {
		
		String UCFIDString = null;
		int UCFID = 0;
		boolean stopTry = false;
		
		if (people == null || people.size() == 0 || !hasTAs(people)) {
			System.out.println("\nThere are no TA's in the list.");
			return;
		}
		
	    System.out.print("\nEnter the UCF id: ");
	    
		do {
			try {
				UCFIDString = scanWords.nextLine();
				if(UCFIDString.length() != 7){
					throw new FormatException();
				}	
				
				UCFID = Integer.parseInt(UCFIDString);
				
				if(!searchID(people, UCFID)) {
					throw new IllegalArgumentException("\nSorry no TA found. ");
				}
				Person person = people.getPersonThroughID(UCFID);
				
			    if (person instanceof Student && person.getUCFID() == UCFID ) {
			    	System.out.println("\nStudent schudule cannot be printed here. ");
			    	System.out.print("\nWould you like to be redirected (y/n): ");
			    	String userInput = scanWords.nextLine();
			    	userInput = yesNoWhile(userInput);
			    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
			    		studentSchedulePrint(people, courses);
			    		return;
					} else {
						throw new IllegalArgumentException();
					}
			    } 
			    else if (person instanceof Faculty && person.getUCFID() == UCFID) {
			    	System.out.println("\nFaculty schudule cannot be printed here. ");
			    	System.out.print("\nWould you like to be redirected (y/n): ");
			    	String userInput = scanWords.nextLine();
			    	userInput = yesNoWhile(userInput);
			    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
			    		facultySchedulePrint(people, courses);
			    		return;
					} else {
						throw new IllegalArgumentException();
					}
			    }
			    
				stopTry = true;
				
			} catch (FormatException e) {
					System.out.print(e.getMessage());
			} catch (NumberFormatException e) {
		        System.out.print("\nInvalid input. Please enter a 7-digit UCF ID: ");
			} catch (IllegalArgumentException e) {
				if(e.getMessage() != null) System.out.println(e.getMessage());
				System.out.print("\nWould you like to enter another ID (y/n): ");
		    	String userInput = scanWords.nextLine();
		    	userInput = yesNoWhile(userInput);
		    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
		    		System.out.print("\nPlease enter a TA's 7-digit UCF ID: ");
		    	} 
		    	else {
					return;
				}
				
			}
			
		} while (stopTry == false);
	    
		Person person = people.getPersonThroughID(UCFID);
	        
	    System.out.println("\nRecord Found:");
	    System.out.println("\n\t" + person.getName());
	    
	    TA ta = (TA) person;
	    
	    List<Course> currentCourses = ta.getcurrentCourses();
	    List<Lab> currentLabs = ta.getcurrentLabs();
	    List<Lab> supervisingLabs = ta.getsupervisingLabs();
	    if (currentCourses.size() == 0 && currentLabs.size() == 0) {
	        System.out.println("\n\tNo enrolled courses or labs found.");
	    } else {
	    	System.out.println("\n\tEnrolled in the following lectures:\n");
	        for (Course course : currentCourses) {
	            System.out.print("\t" + course);
	            if(course.hasLabs()) {
	            	for (Lab lab : currentLabs) {
		            System.out.print("/" + lab + "\n");
	            	}
	            } else if(course.getOnline().equalsIgnoreCase("online")) {
	            	System.out.print("[" + course.getOnline() + "]\n");
	            }
	        }
	        
	    }
	    
	    if(supervisingLabs.size() == 0) {
	    	System.out.println("\n\tNo labs supervised found.");
	    } else {
	    	System.out.println("\n\tSupervising the following labs:");
	        for(Lab lab : supervisingLabs) {
	        	System.out.println("\t" + lab);
	        }
	    }
	    
	    System.out.println("\n*****************************************");
	    
	}
	
	public static void studentSchedulePrint(people people, courses courses) {
		
		String UCFIDString = null;
		int UCFID = 0;
		boolean stopTry = false;
		boolean isTA = false;
		
		if (people == null || people.size() == 0 || !hasStudentsOrTA(people)) {
			System.out.println("\nThere are no students in the list.");
			return;
		}
		
	    System.out.print("\nEnter the UCF id: ");
	    
		do {
			try {
				UCFIDString = scanWords.nextLine();
				if(UCFIDString.length() != 7){
					throw new FormatException();
				}	
				
				UCFID = Integer.parseInt(UCFIDString);
				
				if(!searchID(people, UCFID)) {
					throw new IllegalArgumentException("\nSorry no Student found. ");
				}
				Person person = people.getPersonThroughID(UCFID);
				
			    if (person instanceof TA && person.getUCFID() == UCFID ) {
			    	isTA = true;
			    } 
			    if (person instanceof Faculty && person.getUCFID() == UCFID) {
			    	System.out.println("\nFaculty schudule cannot be printed here. ");
			    	System.out.print("\nWould you like to be redirected (y/n): ");
			    	String userInput = scanWords.nextLine();
			    	userInput = yesNoWhile(userInput);
			    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
			    		facultySchedulePrint(people, courses);
			    		return;
					} else {
						throw new IllegalArgumentException();
					}
			    }
			    
				stopTry = true;
				
			} catch (FormatException e) {
					System.out.print(e.getMessage());
			} catch (NumberFormatException e) {
		        System.out.print("\nInvalid input. Please enter a 7-digit UCF ID: ");
			} catch (IllegalArgumentException e) {
				if(e.getMessage() != null) System.out.println(e.getMessage());
				System.out.print("\nWould you like to enter another ID (y/n): ");
		    	String userInput = scanWords.nextLine();
		    	userInput = yesNoWhile(userInput);
		    	if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
		    		System.out.print("\nPlease enter a student's 7-digit UCF ID: ");
		    	} 
		    	else {
					return;
				}
				
			}
			
		} while (stopTry == false);
	    
		Person person = people.getPersonThroughID(UCFID);
	        
	    System.out.println("\nRecord Found:");
	    System.out.println("\n\t" + person.getName());
	    
	    if(!isTA) {
		    Student student = (Student) person;
		    
		    List<Course> currentCourses = student.getcurrentCourses();
		    List<Lab> currentLabs = student.getcurrentLabs();
		    if (currentCourses.size() == 0 && currentLabs.size() == 0) {
		    	System.out.println("\nNo enrolled courses or labs found.");
		    } else {
		    	System.out.println("\n\tEnrolled in the following lectures:\n");
		        for (Course course : currentCourses) {
		            System.out.print("\t" + course);
		            if(course.hasLabs()) {
		            	for (Lab lab : currentLabs) {
			            System.out.print("/" + lab);
		            	}
		            } else if(course.getOnline().equalsIgnoreCase("online")) {
		            	System.out.print("[" + course.getOnline() + "]");
		            } 
		        }
		    }
	    } else {
	    	TA ta = (TA) person;
		    
		    List<Course> currentCourses = ta.getcurrentCourses();
		    List<Lab> currentLabs = ta.getcurrentLabs();
		    if (currentCourses.size() == 0 && currentLabs.size() == 0) {
		    	System.out.println("\nNo enrolled courses or labs found.");
		    } else {
		    	System.out.println("\n\tEnrolled in the following lectures:\n");
		        for (Course course : currentCourses) {
		            System.out.print("\t" + course);
		            if(course.hasLabs()) {
		            	for (Lab lab : currentLabs) {
			            System.out.print("/" + lab);
		            	}
		            } else if(course.getOnline().equalsIgnoreCase("online")) {
		            	System.out.print("[" + course.getOnline() + "]");
		            }
		            
		        }
		        
		    }
	    }
	    
	    System.out.println("\n\n*****************************************");
	   
	            
	} 
	
	public static void deleteLecture(people people, courses courses) {
		
		boolean stopTry = false;
		int lectureID = 0;
		
		System.out.print("\nEnter the crn of the lecture to delete: ");
		
		do {
			try {
				lectureID = numberScan.nextInt();
				numberScan.nextLine();
				Course course = courses.getCourseThroughCrn(lectureID);
				
				if (!(course instanceof Course)) {
	                throw new IllegalArgumentException("\nOops! That crn doesn't belong to a lecture. Please enter a valid lecture crn: ");
	            }
				
				if(!courses.getcourses().contains(course)) {
					throw new IllegalArgumentException("\nOops! That crn doesn't exist. Please enter a valid crn: ");
				}
				stopTry = true;
				
			} catch (InputMismatchException e) {
				System.out.print("\nOops! That's not a valid crn. Please enter a valid crn: ");
				numberScan.nextLine();
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}
			
		} while(stopTry == false);
		
		Course course = courses.getCourseThroughCrn(lectureID);
		
		for(Person person : people.getpeople()) {
			if(person instanceof Student) {
				Student student = (Student) person;
				
				if(course.hasLabs()) {
	            	ArrayList<Lab> labs = ((InPersonCourse) course).getLabs();
                    for (int i = 0; i < labs.size(); i++) {
                        Lab lab = labs.get(i);
                        if(student.getcurrentLabs().contains(lab)){
                        	student.removeLab(lab);
                        }
                    }
	            }
				
				if(student.getcurrentCourses().contains(course)) {
					student.removeCourse(course);
				}
				
			}
			if(person instanceof Faculty) {
				Faculty faculty = (Faculty) person;
				if(faculty.getlectures().contains(course)) {
					faculty.removeCourse(course);
				}
			}
			if(person instanceof TA) {
				TA ta = (TA) person;
				if(course.hasLabs()) {
	            	ArrayList<Lab> labs = ((InPersonCourse) course).getLabs();
                    for (int i = 0; i < labs.size(); i++) {
                        Lab lab = labs.get(i);
                        if(ta.getcurrentLabs().contains(lab)) {
                        	ta.removeLab(lab);
                        }
                        if(ta.getsupervisingLabs().contains(lab)) {
                        	ta.removeLabSupervising(lab);
                        }
                    }
	            }
				
				if(ta.getcurrentCourses().contains(course)) {
					ta.removeCourse(course);
				}
			}
		}
		
		ListIterator<Person> peopleIterator = people.getpeople().listIterator();
	    while (peopleIterator.hasNext()) {
	    	Person person;
	        person = peopleIterator.next();
	        
	        if(person instanceof Faculty && ((Faculty) person).getlectures().size() == 0) {
	        	peopleIterator.remove();
	        }
	        if(person instanceof Student && ((Student) person).getcurrentCourses().size() == 0) {
	        	peopleIterator.remove();
	        }
	        if(person instanceof TA && ((TA) person).getcurrentCourses().size() == 0 
	           && ((TA) person).getsupervisingLabs().size() == 0) {
	        	peopleIterator.remove();
	        }
	        if (person instanceof TA &&((TA) person).getcurrentCourses().size() != 0 
	        	&& ((TA) person).getsupervisingLabs().size() == 0) {
	        	Person ta = convertToStudent(people, (TA) person);
	        	peopleIterator.remove(); // Remove the current element from the list
	        	peopleIterator.add(ta); // Add the new element to the list
	        }
	    }
	    
	    
		ListIterator<Course> courseIterator = courses.getcourses().listIterator();
	    while (courseIterator.hasNext()) {
	        course = courseIterator.next();
	        if (course instanceof Course && course.getCrn() == lectureID) {
	        	if(course.getOnline() == null) {
	        		System.out.println("\n" + course + " Deleted");
	        		courseIterator.remove(); 
	            } else {
	            	System.out.print("\n" + course + "[" + course.getOnline() + "]" + " Deleted");
	            	courseIterator.remove();
	            }
	        	
	        }
	    }
	    
	    courses.setAlter(true);
	    
	    
	    System.out.println("\n\n*****************************************");
	}
	
	public static void exit(courses courses) {
		
		PrintWriter out = null;
		try {
			out = new PrintWriter("lec_copy.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		if (courses.isAlter()) {
	        System.out.println("\nYou have made a deletion of at least one lecture. Would you like to print the copy of lec.txt?");
	        System.out.print("Enter y/Y for Yes or n/N for No: ");
	        String userInput = scanWords.nextLine();
	        userInput = yesNoWhile(userInput);
	        if(userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
	        	for (Course course : courses.getcourses()) {
	        		if(course.hasLabs()) {
	        			course = (InPersonCourse) course;
                        out.print(course.printingString() + "\n");
                        ArrayList<Lab> labs = ((InPersonCourse) course).getLabs();
                        for (int i = 0; i < labs.size(); i++) {
                            Lab lab = labs.get(i);
                            out.print(lab.printintString()+ "\n");
                        }
	        		}
	        		else {
	        		out.print(course.printingString()+ "\n");
	        		}
	        	}
				 
			}
	    } 
		out.close();
		System.out.println("\nBye!");
		
	}

	public static void main(String[] args) throws FileNotFoundException{
		courses courses = new courses();
		people people = new people();
		
		courses(courses);
		menu(people, courses);
	}	
}
