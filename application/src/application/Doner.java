package application;

public class Doner {
	 private String name;
	    private int age;
	    private String bloodGroup;
	    private String contact;

	    public Doner(String name, int age, String bloodGroup, String contact) {
	        this.name = name;
	        this.age = age;
	        this.bloodGroup = bloodGroup;
	        this.contact = contact;
	    }

	    public String getName() { return name; }
	    public int getAge() { return age; }
	    public String getBloodGroup() { return bloodGroup; }
	    public String getContact() { return contact; }

	    @Override
	    public String toString() {
	        return name + "," + age + "," + bloodGroup + "," + contact;
	    }
}
