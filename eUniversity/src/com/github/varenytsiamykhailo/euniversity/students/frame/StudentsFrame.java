package com.github.varenytsiamykhailo.euniversity.students.frame;

import com.github.varenytsiamykhailo.euniversity.students.logic.Group;
import com.github.varenytsiamykhailo.euniversity.students.logic.ManagementSystem;
import com.github.varenytsiamykhailo.euniversity.students.logic.Student;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Vector;

public class StudentsFrame extends JFrame {
    ManagementSystem ms = ManagementSystem.getInstance();
    private JList allGroupsList;
    private JList allStudentsList;
    private JSpinner yearSpinner;

    public StudentsFrame() {
        // Устанавливаем layout (область) по всему окну
        getContentPane().setLayout(new BorderLayout());

        // Верхняя панель (здесь будет поле для ввода года)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("Год обучения:"));

        // Спиннер:
        SpinnerModel spinnerModel = new SpinnerNumberModel(2020, 1900, 2100, 1);
        yearSpinner = new JSpinner(spinnerModel);
        topPanel.add(yearSpinner);

        // Нижняя панель
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout()); // все оставшееся место ниже topPanel


        // Левая панель для вывода списка групп
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

        // Получаем список групп
        Vector<Group> groups = new Vector<Group>(ms.getAllGroups());

        leftPanel.add(new JLabel("Группы:"), BorderLayout.NORTH);

        allGroupsList = new JList(groups); //создаем визуальный список
        leftPanel.add(new JScrollPane(allGroupsList), BorderLayout.CENTER); // вставляем список в скроллируемую панель (JScrollPane), которую помещаем в центре leftPanel


        // Правая панель для вывода списка студентов
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

        // Получаем список студентов
        Vector<Student> students = new Vector<Student>(ms.getAllStudents());

        rightPanel.add(new JLabel("Студенты:"), BorderLayout.NORTH);

        allStudentsList = new JList(students);
        rightPanel.add(new JScrollPane(allStudentsList), BorderLayout.CENTER); // вставляем список в скроллируемую панель (JScrollPane), которую помещаем в центре leftPanel


        // Вставляем панели со списками групп (leftPanel) и студентов (rightPanel) в нижнюю панель (bottomPanel)
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.CENTER);

        // Вставляем верхнюю (topPanel) и нижнюю панели (bottomPanel) в форму
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);

        // Задаем границы формы
        setBounds(400, 100, 800,600);
    }
}
