import java.util.*;
import java.util.ArrayList;

class Property
{
    private String PropertyType;
    private String PropertyId;
    private String area;
    private double rentPerMonth;
    private boolean isAvailable;

    public Property(String PropertyType,String PropertyId,String area,double rentPerMonth)
    {
        this.PropertyType = PropertyType;
        this.area = area;
        this.PropertyId = PropertyId;
        this.rentPerMonth = rentPerMonth;
        this.isAvailable = true;
    }
    public String getType()
    {
        return PropertyType;
    }
    public String getArea()
    {
        return area;
    }

    public String getId()
    {
        return PropertyId;
    }
    
    public double calCulateRent()
    {
        return rentPerMonth;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnProperty() {
        isAvailable = true;
    }
}

class Customer
{
    private String name;
    private String id;
    
    Customer(String name,String id)
    {
        this.name = name;
        this.id = id;
    }

    public String getCustomerName()
    {
        return name;
    }
    public String getCustomerId()
    {
        return id;
    }
}

class Rent
{
    private Property property;
    private Customer customer;
    private int months;

    public Rent(Property property,Customer customer,int months)
    {
       this.property = property;
       this.customer = customer;
       this.months = months;
    }
    
    public Property getproperty()
    {
        return property;
    }
    public Customer getCustomer()
    {
        return customer;
    }
    public int getMonth()
    {
        return months;
    }
}

class RentProperty 
{
    private ArrayList<Property> prop;
    private ArrayList<Customer> cus;
    private ArrayList<Rent> rent;

    public RentProperty()
    {
        prop = new ArrayList<>();
        cus = new ArrayList<>();
        rent = new ArrayList<>();
    }

    public void addProperty(Property p)
    {
        prop.add(p);
    }
    public void addCustomers(Customer c)
    {
        cus.add(c);
    }
    public void rentProp(Property p,Customer c,int months)
    {
        if(p.isAvailable())
        {
            p.rent();
            rent.add(new Rent(p, c, months));
        }
        else
        {
            System.out.println("Property is not available for rent");
        }
    }
    public void returnBack(Property p)
    {
        Rent delete = null;
        for(Rent pr : rent)
        {
            if(pr.getproperty() == p)
            {
                delete = pr;
                break;
            }
        }
        if(delete != null)
        {
            rent.remove(delete);
        }
        else
        {
            System.out.println("Property is not given for rent.");
        }
    }

    public void run()
    {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while(choice!=3)
        {
            System.out.println("Enter your choice from below options : \n1.Rent a property as per your requirement\n2. return a property\n3.exit from system\n");
            choice = sc.nextInt();

            if(choice == 1)
            {
                 System.out.println("\n\n= = = Rent a Property = = =");
                         
                         System.out.println("\nEnter your name please..");
                         String cname = sc.nextLine();

                         System.out.println("Rent any below available properties as per your requirement \n");

                         for(Property p : prop)
                         {
                            System.out.println(p.getId() + " - " + p.getType() + " " + p.getArea());
                         }
                         System.out.print("\nEnter the Property ID you want to rent: ");
                         String Id = sc.nextLine();

                         System.out.print("Enter the number of months for rental: ");
                         int rentalMonths = sc.nextInt();
                         sc.nextLine(); // Consume newline

                         Customer newCustomer = new Customer("CUS" + (cus.size() + 1), cname);
                         addCustomers(newCustomer);
                         Property selected = null;
                for (Property p : prop) {
                    if (p.getId().equals(Id) && p.isAvailable()) {
                        selected = p;
                        break;
                    }
                }
                if (selected != null) {
                    double totalPrice = selected.calCulateRent();
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getCustomerName());
                    System.out.println("Property : " + selected.getType() + " " + "Location : "+selected.getArea() + "Property ID : "+selected.getId());
                    System.out.println("Rental Duration: " + rentalMonths);
                    System.out.printf("Rent per month : ", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentProp(selected, newCustomer, rentalMonths);
                        System.out.println("\nProperty rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid Property selection or this property is not available for rent.");
                }  
            }      
            else if(choice == 2)
            {
            System.out.println("\n== Return a Property ==\n");
            System.out.print("Enter the car ID you want to return: ");
            String PoId = sc.nextLine();

            Property ppToReturn = null;
            for (Property p : prop) {
                if (p.getId().equals(PoId) && !p.isAvailable()) {
                    ppToReturn = p;
                    break;
                }
            }

            if (ppToReturn != null) {
                Customer customer = null;
                for (Rent rental : rent) {
                    if (rental.getproperty() == ppToReturn) {
                        customer = rental.getCustomer();
                        break;
                    }
                }

                if (customer != null) {
                    returnBack(ppToReturn);
                    System.out.println("Car returned successfully by " + customer.getCustomerName());
                }
            }
                else {
                    System.out.println("Car was not rented or rental information is missing.");
                }
            } 
            else if(choice == 3)
            {
                break;
            }
            else{
                System.out.println("Invalid car ID or car is not rented.");
            }
                
            }
            System.out.println("Thankyou for rent your property with us ! ");
        }
    }


public class Solution 
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n ==== Welcome to Property Rental System !! ====");

        RentProperty rp = new RentProperty();
        
        Property f1 = new Property("Flat 4-BHK (1000 sq.ft)", "f1", "Near kalawad road, kotecha chowk,Rajkot", 30000);
        Property f2 = new Property("Flat 2-BHK (650 sq.ft)", "f2", "Near ayodhya chowk,150 feet ring road,Rajkot", 12000);
        Property f3 = new Property("Flat 1-BHK (500 sq.ft)", "f3", "Near Bapasitaram chowk,mavdi main road,Rajkot", 8000);

        Property b1 = new Property("Bunglow 1-BHK (800 sq.fit,Total floor = 1)", "b1", "Near panchayat chowk , University road,Rajkot", 8000);
        Property b2 = new Property("Bunglow 2-BHK (1000 sq.fit,total floor = 2)", "b2", "Near shital park,150 feet ring road,Rajkot", 10000);
        Property b3 = new Property("Bunglow 1-BHK (1200 sq.fit,total floor = 3)", "b3", "Near crystal mall,kalawad road,Rajkot", 15000);

        Property s1 = new Property("Shop/Show-room (300 sq.ft)", "s1", "Near, Madhapar chowk,150 feet ring road,Rajkot", 30000); 
        Property s2 = new Property("Shop/Small shop (200 sq.ft)", "s2", "Near, Gondal chowk,150 feet ring road,Rajkot", 15000); 
        Property s3 = new Property("Shop/large shop(225 sq.ft)", "s3", "Near, West zone office,150 feet ring road,Rajkot", 25000); 

        Property o1 = new Property("Office/Corporate office(900 sq.ft)", "o1", "Near Nana mava circle,150 feet ring road,Rajkot", 50000);
        Property o2 = new Property("Office(500 sq.ft)", "o2", "Raiya telephone exchange,150 feet ring road,Rajkot", 40000);

        rp.addProperty(f1);
        rp.addProperty(f2);
        rp.addProperty(f3);
        rp.addProperty(b1);
        rp.addProperty(b2);
        rp.addProperty(b3);
        rp.addProperty(s1);
        rp.addProperty(s2);
        rp.addProperty(s3);
        rp.addProperty(o1);
        rp.addProperty(o2);

        rp.run();
        
    }
}