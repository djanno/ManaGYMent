package controller.panels.members;

public interface IAbstractTableMemberController {
    
    void createTable();
    
    abstract void addMemberCmd();
    
    abstract void editMemberCmd(final int index);
    
    void deleteMemberCmd(final int index);
    
}
