package iskconbangalore.org.kaoperations;

public class Users {

    private String Name;
    private String Number;
    private String MenuType;
    private String Residency;
    private int Points;

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    private int admin;
    public int getPoints() {
        return Points;
    }

    public void setPoints(int Points) {
        this.Points = Points;
    }



    public Users()
    {
        this.Name="";
        this.Number="";
        this.MenuType="";
        this.Residency="";
        this.Points= 0;
        this.admin = 0;
    }
    public Users(String Name,String Number,String MenuType,String Residency,int points,int admin)
    {
        this.Name = Name;
        this.Number = Number;
        this.MenuType = MenuType;
        this.Residency = Residency;
        this.Points = points;
        this.admin = admin;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getMenuType() {
        return MenuType;
    }

    public void setMenuType(String menuType) {
        MenuType = menuType;
    }

    public String getResidency() {
        return Residency;
    }

    public void setResidency(String residency) {
        Residency = residency;
    }



}
