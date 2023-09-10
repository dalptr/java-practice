package dao;

public class TestDAO {

    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        var allEmployees = employeeDAO.getAll();
        System.out.println("All employees: ");
        allEmployees.forEach(e -> System.out.println(e.getId() + " " + e.getName()));
    }
}
