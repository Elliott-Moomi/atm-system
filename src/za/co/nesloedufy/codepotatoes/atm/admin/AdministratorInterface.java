package za.co.nesloedufy.codepotatoes.atm.admin;

import za.co.nesloedufy.codepotatoes.atm.pojos.Employee;

public interface AdministratorInterface {
    void addEmployee(Employee emp);
    void editEmployee(int id);
    void removeEmployee(int id);
    void viewEmployeeInfo(int id);
    void updateATMStatus();
    void viewAuditTrail(long atmId);
    void employeeList();
    //void freezeCard(long cardNumber);
    void fieldSelectionMenu();
    void storeToDatabase();
    void readFromDatabase();

}
