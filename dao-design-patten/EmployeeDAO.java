package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/qlbh";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private final List<Employee> Employees = new ArrayList<>();

    public void updateEmployeesFromDB() {
        Employees.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM Employee");
            while (resultSet.next()) {
                var id = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var Employee = new Employee(id, name);
                Employees.add(Employee);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to connect to DB, error: " + e.getMessage());
        }
    }

    EmployeeDAO() {
        updateEmployeesFromDB();
    }

    @Override
    public List<Employee> getAll() {
        return Employees;
    }

    @Override
    public Employee get(int id) {
        for (var Employee : Employees) {
            if (Employee.getId() == id) {
                return Employee;
            }
        }
        return null;
    }

    @Override
    public void save(Employee Employee) {
        Employees.add(Employee);
    }

    @Override
    public void update(Employee employee) {
        // no optional
        var existEmployee = get(employee.getId());
        if (existEmployee != null) {
            existEmployee.setName(employee.getName());
        }
    }

    @Override
    public void delete(Employee Employee) {
//        get(Employee.getId()).ifPresent(Employees::remove); // explain this line : get(Employee.getId()) returns an Optional object, if it is present, then remove it from the list
        var existEmployee = get(Employee.getId());
        if (existEmployee != null) {
            Employees.remove(existEmployee);
        }
    }
}
