package com.github.varenytsiamykhailo.euniversity.students.frame;

import com.github.varenytsiamykhailo.euniversity.students.logic.Group;
import com.github.varenytsiamykhailo.euniversity.students.logic.ManagementSystem;
import com.github.varenytsiamykhailo.euniversity.students.logic.Student;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class StudentsFrame extends JFrame {
    ManagementSystem ms = null;
    private JList allGroupsJList;
    private JList allStudentsJList;
    private JSpinner yearSpinner;

    public StudentsFrame() {
        // Устанавливаем layout (область) по всему окну
        getContentPane().setLayout(new BorderLayout());

        // Верхняя панель (здесь будет поле для ввода года со спинером)
        JPanel topPanel = createTopPanel();
        // Нижняя панель (внутри нее - левая панель (список групп) и правая панель (список студентов))
        JPanel bottomPanel = createBottomPanel();

        // Вставляем верхнюю (topPanel) и нижнюю панели (bottomPanel) в форму
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);

        // Задаем границы формы
        setBounds(400, 100, 1000,600);
    }

    // Верхняя панель (здесь будет поле для ввода года со спинером)
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("Год обучения:"));

        // Спиннер:
        SpinnerModel spinnerModel = new SpinnerNumberModel(2020, 1900, 2100, 1);
        yearSpinner = new JSpinner(spinnerModel);
        topPanel.add(yearSpinner);
        return topPanel;
    }

    // Нижняя панель (внутри нее - левая панель (список групп) и правая панель (список студентов))
    private JPanel createBottomPanel() {
        // Нижняя панель
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout()); // все оставшееся место ниже topPanel
        ms = ManagementSystem.getInstance(); // Коннект к базе происходит внутри ManagementSystem. ManagementSystem сам обработает исключение коннекта


        // Получаем список групп и студентов
        Vector<Group> allGroups = null;
        Vector<Student> allStudents = null;
        try { // Необходимо обработать ошибку при обращении к базе данных
            allGroups = new Vector<Group>(ms.getAllGroups());
            allStudents = new Vector<Student>(ms.getAllStudents());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Левая панель для вывода списка групп
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        leftPanel.add(new JLabel("Группы:"), BorderLayout.NORTH);
        allGroupsJList = new JList(allGroups); // Создаем визуальный список
        leftPanel.add(new JScrollPane(allGroupsJList), BorderLayout.CENTER); // Вставляем список в скроллируемую панель (JScrollPane), которую помещаем в центре leftPanel


        // Правая панель для вывода списка студентов
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        rightPanel.add(new JLabel("Студенты:"), BorderLayout.NORTH);
        allStudentsJList = new JList(allStudents);
        rightPanel.add(new JScrollPane(allStudentsJList), BorderLayout.CENTER); // вставляем список в скроллируемую панель (JScrollPane), которую помещаем в центре leftPanel


        // Вставляем панели со списками групп (leftPanel) и студентов (rightPanel) в нижнюю панель (bottomPanel)
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.CENTER);

        return bottomPanel;
    }
}
