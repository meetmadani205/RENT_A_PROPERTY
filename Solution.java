import java.util.*;
import java.sql.*;

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
    
    public String calCulateRent()
    {
        return rentPerMonth+"";
    }
    public boolean isAvailable() 
    {
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
    Connection con = null;
    public RentProperty()
    {
        try
        {
           String url = "jdbc:mysql://localhost:3306/project";
           String username = "root";
           String password = "************";
           con = DriverManager.getConnection(url, username, password);
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addProperty(Property p) throws SQLException
    {
        String in = "insert into property(id,type,area,rentpermonth,isavailable) values(?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(in);

        ps.setString(1, p.getId());
        ps.setString(2, p.getType());
        ps.setString(3, p.getArea());
        ps.setString(4, p.calCulateRent());
        ps.setString(5, "True");
       
        ps.executeUpdate();
    }
    public void addCustomers(Customer c) throws SQLException
    {
        String q = "select count(*) as cnt from customer where id = "+c.getCustomerId();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(q);
        int a = 0;
        if(rs.next())
        {
           a = rs.getInt(1);
        }
        if(a == 1)
        {
            System.out.println("This customer has already exist! +id = "+c.getCustomerId());
            return;
        }
        String in = "insert into customer(id,name) values(?,?)";
        PreparedStatement ps = con.prepareStatement(in);

        ps.setString(1, c.getCustomerId());
        ps.setString(2, c.getCustomerName());
       
        ps.executeUpdate();
    }
    public void rentProp(Property p,Customer c,String months) throws SQLException
    {
        
        if(p.isAvailable())
        {
            p.rent();
            String ex = "update property set isavailable = 'False' where id = "+p.getId();
            PreparedStatement psz = con.prepareStatement(ex);
            psz.executeUpdate();

            String in = "insert into rent(pid,cid,rentpermonth) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(in);

            ps.setString(1, p.getId());
            ps.setString(2, c.getCustomerId());
            ps.setString(3, p.calCulateRent());
       
            ps.executeUpdate();
            System.out.println();
            System.out.println("                -:Confirmation details :- ");
            System.out.println("Customer Name : "+c.getCustomerName()+" id = "+c.getCustomerId());
            System.out.println("Proprty type : "+p.getType()+" id = "+p.getId());
            System.out.println("Address : "+p.getArea());
            System.out.println("Rent per month = "+p.calCulateRent());
            System.out.println();
            System.out.println("Property rented successfully!");
        }
        else
        {
            System.out.println("Property is not available for rent");
        }
    }
    public void returnBack(Property p) throws SQLException
    {
        String q = "select count(*) as cnt from rent where pid = "+p.getId();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(q);
        int a = 0;

        if(rs.next())
        {
            a = rs.getInt(1);
        }
        
        if(a == 1)
        {
            String qu = "DELETE FROM rent WHERE pid = ?";
            PreparedStatement pstmt = con.prepareStatement(qu);
            pstmt.setString(1, p.getId());

            pstmt.executeUpdate();

            String ex = "update property set isavailable = 'True' where id = "+p.getId();
            PreparedStatement psz = con.prepareStatement(ex);
            psz.executeUpdate();
            System.out.println("Property returned successfully!");
        }
        else
        {
            System.out.println("Property is not given for rent.");
        }
    }
    public void run() throws SQLException
    {
        Property arr[] = Solution.call();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        System.out.println();
        while(choice != 3)
        {
            System.out.println("Enter your choice :\n1.Rent a property\n2.Return a property\n3.exit from system");
            choice = sc.nextInt();
            if(choice == 1)
            {
                String q = "select id,type,area from property where isavailable = 'True'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(q);
                System.out.println("id                   "+"Addres                  ");
                while(rs.next())
                {
                    System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
                }
                System.out.println();
                System.out.println("Enter property id you want to rent");
                String in = sc.next();

                System.out.println("Enter your name : ");
                String name = sc.next();

                System.out.println("For How many months you need to rent?");
                String mon = sc.next();

                String qe = "select count(*) from customer";
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(qe);
                int b = 1;
                if(rs1.next())
                {
                    b = rs1.getInt(1);
                }
                Customer nc = new Customer(name, (b+1)+"");
                addCustomers(nc);

                String qee = "select count(*) from property where id = "+in;
                Statement st11 = con.createStatement();
                ResultSet rs11 = st1.executeQuery(qee);
                int b1 = 0;
                if(rs11.next())
                {
                    b1 = rs11.getInt(1);
                }
                if(b1 == 1)
                {
                    String qeee = "select isavailable from property where id = "+in;
                    Statement st111 = con.createStatement();
                    ResultSet rs111 = st111.executeQuery(qeee);

                    if(rs111.next() && rs111.getString(1).equalsIgnoreCase("True"))
                    {
                        int c = Integer.parseInt(in);
                        rentProp(arr[c], nc, mon);
                    }
                    else
                    {
                        System.out.println("Rental canceled!");
                    }
                }
                else
                {
                    System.out.println("Rental canceled!!");
                }
            }
            else if(choice == 2)
            {
                System.out.println("Enter property id you want to return");
                String in = sc.next();
                int c = Integer.parseInt(in);
                returnBack(arr[c]);
            }
        }
    }
}
public class Solution 
{
      static Property arr[] = new Property[11];
      public static void main(String args[]) throws SQLException
      {
        RentProperty rp = new RentProperty();
        
        
        arr[0] = new Property("Flat 4-BHK (1000 sq.ft)", "0", "Near kalawad road, kotecha chowk,Rajkot", 30000);
        arr[1] = new Property("Flat 2-BHK (650 sq.ft)", "1", "Near ayodhya chowk,150 feet ring road,Rajkot", 12000);
        arr[2] = new Property("Flat 1-BHK (500 sq.ft)", "2", "Near Bapasitaram chowk,mavdi main road,Rajkot", 8000);
        arr[3] = new Property("Bunglow 1-BHK (800 sq.fit,Total floor = 1)", "3", "Near panchayat chowk , University road,Rajkot", 8000);
        arr[4] = new Property("Bunglow 2-BHK (1000 sq.fit,total floor = 2)", "4", "Near shital park,150 feet ring road,Rajkot", 10000);
        arr[5] = new Property("Bunglow 1-BHK (1200 sq.fit,total floor = 3)", "5", "Near crystal mall,kalawad road,Rajkot", 15000);
        arr[6] = new Property("Shop/Show-room (300 sq.ft)", "6", "Near, Madhapar chowk,150 feet ring road,Rajkot", 30000); 
        arr[7] = new Property("Shop/Small shop (200 sq.ft)", "7", "Near, Gondal chowk,150 feet ring road,Rajkot", 15000); 
        arr[8] = new Property("Shop/large shop(225 sq.ft)", "8", "Near, West zone office,150 feet ring road,Rajkot", 25000); 
        arr[9] = new Property("Office/Corporate office(900 sq.ft)", "9", "Near Nana mava circle,150 feet ring road,Rajkot", 50000);
        arr[10] = new Property("Office(500 sq.ft)", "10", "Raiya telephone exchange,150 feet ring road,Rajkot", 40000);

        for(Property p : arr)
        {
            rp.addProperty(p);
        }
        rp.run();
      }
      public static Property[] call()
        {
            return arr;
        }
}
