package com.github.varenytsiamykhailo.euniversity.desktop.frame;

import com.github.varenytsiamykhailo.euniversity.logic.Student;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class StudentsTableModel extends AbstractTableModel {

    // Константы
    /**
     * Количество столбцов в таблице и их названия.
     * При изменении параметра - внести изменения в метод getValueAt классе StudentsTableModel
     */
    protected static final String[] studentsTableColumnNames = new String[]{"Фамилия", "Имя", "Отчество", "Дата рождения", "Пол", "Год обучения"};
    protected static final int  NUM_COLUMNS_FOR_STUDENTS_TABLE = studentsTableColumnNames.length;

    private Vector<Student> students; // Хранилище для списка студентов

    public StudentsTableModel(Vector students) {
        this.students = students;
    }

    /**
     * Возвращает количество строк
     */
    @Override
    public int getRowCount() {
        if (students != null)
            return students.size(); // Количество строк в таблице равно числу записей в коллекции
        return 0;
    }

    /**
     * Возвращает количество столбцов
     */
    @Override
    public int getColumnCount() {
        return NUM_COLUMNS_FOR_STUDENTS_TABLE; // Количество столбцов - 7: Фамилия, Имя, Отчество, Дата рождения, Пол, Группа, Год обучения
    }

    /**
     * Возвращает имя колонки
     */
    @Override
    public String getColumnName(int columnIndex) {
        return  studentsTableColumnNames[columnIndex];
    }

    /**
     * Возвращает данные для определенной строки и столбца
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (students != null) {
            Student student = getStudent(rowIndex);

            switch (columnIndex) {
                case 0:
                    return student.getLastName();
                case 1:
                    return student.getFirstName();
                case 2:
                    return student.getPatronymic();
                case 3:
                    return student.getDateOfBirth();
                case 4:
                    return student.getSex();
                case 5:
                    return student.getEducationYear();
            }
        }
        return null;
    }

    /**
     * Возвращает студента (объект типа Student) по номеру строки
     */
    private Student getStudent(int rowIndex) {
        if (students != null) {
            if (rowIndex < students.size() && rowIndex >= 0) {
                return (Student) students.get(rowIndex);
            }
        }
        return null;
    }
}
