package department.di.config;

import department.data.dao.DepartmentDAO;
import department.data.dao.EmployeeDAO;


import java.util.Map;

public class JavaConfiguration implements Configuration{
    @Override
    public String getPackageToScan() {
        return "department";
    }

    @Override
    public Map<Class, Class> getInterfaceToImplementations() {
        return Map.of(DepartmentDAO.class, EmployeeDAO.class);
    }
}