package department.view;

import department.data.model.Department;
import department.data.model.Employee;
import department.di.annotation.Inject;
import department.di.annotation.Injectable;
import department.di.factory.BeanFactory;
import department.service.CompanyService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Injectable
public class ConsoleView implements View {

    @Inject
    private final CompanyService service = BeanFactory.getInstance().getBean(CompanyService.class);
//    @Inject
//    private  CompanyService service ;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMenu() throws SQLException {
        while (true) {
            System.out.println("что вы хотите сделать? выберите цифру:");
            System.out.println("1. добавить сотрудника");
            System.out.println("2. редактировать сотрудника");
            System.out.println("3. удалить сотрудника");
            System.out.println("4. добавить отдел");
            System.out.println("5. редактировать отдел");
            System.out.println("6. удалить отдел");
            System.out.println("7. посмотреть всех сотрудников");
            System.out.println("8. посмотреть все отделы");
            System.out.println("9. выход");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    editEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    addDepartment();
                    break;
                case 5:
                    editDepartment();
                    break;
                case 6:
                    deleteDepartment();
                    break;
                case 7:
                    viewEmployees();
                    break;
                case 8:
                    viewDepartments();
                    break;
                case 9:
                    exit();
                default:
                    System.out.println("неправильный ввод");
            }
        }
    }

    @Override
    public void addEmployee() throws SQLException {
        System.out.println("Новый сотрудник.");
        System.out.print("имя: ");
        String name = scanner.next();
        System.out.print("возраст: ");
        int age = scanner.nextInt();
        System.out.print("зарплата: ");
        double salary = scanner.nextDouble();
        System.out.print("Название отедела: ");
        String depName = scanner.next();

        service.addEmployeeToDepartment(depName, name, age, salary);
    }

    public void editEmployee() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите id редактируемого сотрудника");
        int id = scanner.nextInt();
        Employee employee = service.getEmployee(id);
        System.out.println("Текущие данные сотрудника: ");
        System.out.println("Имя: " + employee.getName() +
                "; Возраст: " + employee.getAge() +
                "; Зарплата: " + employee.getSalary() +
                "; Отдел: " + employee.getDepartmentId());
        System.out.println("Введите новые данные сотрудника: ");
        System.out.print("имя: ");
        String name = scanner.next();
        System.out.print("возраст: ");
        int age = scanner.nextInt();
        System.out.print("зарплата: ");
        double salary = scanner.nextDouble();
        System.out.print("Id отедела: ");
        int depId = scanner.nextInt();

        service.editEmployee(new Employee(id, name, age, salary, depId));
    }

    @Override
    public void deleteEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите id удаляемого сотрудника");
        int name = scanner.nextInt();
        service.removeEmployee(name);
    }

    @Override
    public void addDepartment() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите название отедела:");
        String departmentName = scanner.nextLine();
        service.addDepartment(departmentName);

    }

    public void editDepartment() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите id редактируемого отдела");
        int id = scanner.nextInt();
        Department department = service.getDepartment(id);
        System.out.println("Текущие данные отдела: ");
        System.out.println("Имя: " + department.getName());
        System.out.println("Введите новые данные отдела: ");
        System.out.print("имя: ");
        String name = scanner.next();

        service.editDepartment(new Department(id, name));
    }

    @Override
    public void deleteDepartment() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите индекс удаляемого отедела:");
        int departmentInd = scanner.nextInt();
        service.deleteDepartment(departmentInd);
    }

    @Override
    public void viewDepartments() throws SQLException {
        List<Department> departments = service.getAllDepartments();

        for (Department department : departments) {
            String departmentName = department.getName();
            List<Employee> employees = service.getEmployeesFromDepartment(department.getId());
            System.out.println("Id: " + department.getId() + "; Название: " + departmentName + ", число сотрудников: " + employees.size());
            System.out.println("    Сотрудники: ");
            for (Employee e : employees){
                System.out.println("        Id: " +  e.getId() + " Имя: " + e.getName() + "; Возраст: " + e.getAge() + "; Зарплата: " + e.getSalary());
            }
            System.out.println();
        }
    }

    private void viewEmployees() throws SQLException {
        List<Employee> employeeList = service.getAllEmployees();

        for (Employee employee : employeeList) {
            int employeeId = employee.getId();
            String employeeName = employee.getName();
            int employeeAge = employee.getAge();
            double employeeSalary = employee.getSalary();
            int employeeDepId = employee.getDepartmentId();


            System.out.println("Id: " +  employeeId +"; Имя: " + employeeName + "; Возраст: " +employeeAge
                    + "; Зарплата: " + employeeSalary + "; Id отдела: " + employeeDepId);
            System.out.println();
        }
    }

    private void exit(){
        System.out.println("выход");
        service.exit();
        System.exit(0);
    }
}