package department.service;


import department.data.dao.DepartmentDAO;
import department.data.dao.EmployeeDAO;
import department.data.model.Department;
import department.data.model.Employee;
import department.data.repository.DepartmentRepository;
import department.data.repository.EmployeeRepository;
import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.di.factory.BeanFactory;

import java.sql.SQLException;
import java.util.List;

@Injectable
public class CompanyService {
//    @Inject
//    private final DepartmentDAO departmentRepository = BeanFactory.getInstance().getBean(DepartmentDAO.class);
//    @Inject
//    private final EmployeeDAO employeeRepository= BeanFactory.getInstance().getBean(EmployeeDAO.class);

    private final DepartmentDAO departmentRepository = new DepartmentDAO();
    private final EmployeeDAO employeeRepository= new EmployeeDAO();

//    private final DepartmentRepository departmentRepository = new DepartmentRepository();
//    private final EmployeeRepository employeeRepository= new EmployeeRepository();


    public CompanyService() throws SQLException {
    }

    public void addDepartment(String name) throws SQLException {
        departmentRepository.create(name);
    }

    public void deleteDepartment(int departmentName) throws SQLException {
        departmentRepository.delete(departmentName);
    }

    public void editDepartment(Department department) throws SQLException{
        departmentRepository.update(department);
    }

    public void addEmployeeToDepartment(String departmentName, String name, int age, double salary) throws SQLException {
        Department department = departmentRepository.getByName(departmentName);
        int depId;
        if (department == null) {
            departmentRepository.create(departmentName);
            department = departmentRepository.getByName(departmentName);
        }
        depId = department.getId();

        employeeRepository.create(depId, name, age, salary);
    }

    public void removeEmployee(int empId) throws SQLException {
        employeeRepository.delete(empId);
    }

    public void editEmployee(Employee employee) throws SQLException {
        employeeRepository.update(employee);
    }

    public List<Department> getAllDepartments() throws SQLException {
        return departmentRepository.getAll();
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeRepository.getAll();
    }

    public List<Employee> getEmployeesFromDepartment(int depId) throws SQLException {
        return departmentRepository.getEmployeesFromDepartment(depId);
    }

    public Employee getEmployee(int id) throws SQLException {
        return employeeRepository.getById(id);
    }

    public Department getDepartment(int id) throws SQLException {
        return departmentRepository.getById(id);
    }

    public double getTotalSalaryInDepartment(String departmentName) {
        return 0;
    }


    public void exit(){
        employeeRepository.close();
        departmentRepository.close();
    }
}

