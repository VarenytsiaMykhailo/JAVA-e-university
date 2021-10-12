package com.github.varenytsiamykhailo.euniversity.desktop.frame;

import com.github.varenytsiamykhailo.euniversity.desktop.ManagementSystemDesktopDAO;
import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


/*
 * ActionListener - для меню и кнопок
 * ListSelectionListener - для списка
 * ChangeListener - для спиннера
 */

public class StudentsFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

    // Имена константы для строк (надписи меню, кнопок и т.д.)
    private static final String MENU_REPORTS = "Отчеты";
    private static final String MENU_ITEM_ALL_STUDENTS = "Все студенты";
    private static final String TOP_PANEL_LABEL = "Год обучения:";
    private static final String BOTTOM_PANEL_INNER_LEFT_PANEL_LABEL = "Группы:";
    private static final String BOTTOM_PANEL_INNER_RIGHT_PANEL_LABEL = "Студенты:";
    private static final String MOVE_GROUP_BUTTON = "Переместить";
    private static final String CLEAR_GROUP_BUTTON = "Очистить";
    private static final String INSERT_STUDENT_BUTTON = "Добавить";
    private static final String UPDATE_STUDENT_BUTTON = "Исправить";
    private static final String DELETE_STUDENT_BUTTON = "Удалить";

    // Имена константы для кнопок (btn.setName() / btn.getName())
    private static final String MOVE_GROUP_BUTTON_NAME = "moveGroup";
    private static final String CLEAR_GROUP_BUTTON_NAME = "clearGroup";
    private static final String INSERT_STUDENT_BUTTON_NAME = "insertStudent";
    private static final String UPDATE_STUDENT_BUTTON_NAME = "updateStudent";
    private static final String DELETE_STUDENT_BUTTON_NAME = "deleteStudent";
    private static final String ALL_STUDENTS_MENU_ITEM_NAME = "allStudents";

    ManagementSystemDesktopDAO ms = null;

    private JList allGroupsJList;

    private JList allStudentsJList;

    private JTable studentsJTable;

    private JSpinner yearJSpinner;

    public StudentsFrame() {
        // Устанавливаем layout (область) по всему окну
        getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = createMenuBar();
        // Устанавливаем меню для формы
        setJMenuBar(menuBar);

        // Верхняя панель (здесь будет поле для ввода года со спинером)
        JPanel topPanel = createTopPanel();
        // Нижняя панель (внутри нее - левая панель (список групп) и правая панель (список студентов))
        JPanel bottomPanel = createBottomPanel();

        // Вставляем верхнюю (topPanel) и нижнюю панели (bottomPanel) в форму
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);

        // Задаем границы формы
        setBounds(400, 100, 1000, 600);
    }

    /**
     * Строка меню menuBar
     */
    private JMenuBar createMenuBar() {
        // Строка меню
        JMenuBar menuBar = new JMenuBar();
        // Выпадающее меню
        JMenu menu = new JMenu(MENU_REPORTS);
        // Создаем пункт в выпадающем меню
        JMenuItem menuItem = new JMenuItem(MENU_ITEM_ALL_STUDENTS);
        menuItem.setName(ALL_STUDENTS_MENU_ITEM_NAME);
        // Добавляем листенер
        menuItem.addActionListener(this);
        // Вставляем пункт меню в выпадающее меню
        menu.add(menuItem);
        // Вставляем выпадающее меню в строку меню
        menuBar.add(menu);
        return menuBar;
    }

    /**
     * Верхняя панель (здесь будет поле для ввода года со спинером)
     */
    private JPanel createTopPanel() {
        // Спиннер:
        SpinnerModel spinnerModel = new SpinnerNumberModel(2020, 1900, 2100, 1);
        yearJSpinner = new JSpinner(spinnerModel);
        // Добавляем листенер
        yearJSpinner.addChangeListener(this);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel(TOP_PANEL_LABEL));
        topPanel.add(yearJSpinner);

        return topPanel;
    }

    /**
     * Нижняя панель (внутри нее - левая панель (список групп) и правая панель (список студентов))
     */
    private JPanel createBottomPanel() {
        ms = ManagementSystemDesktopDAO.getInstance(); // Коннект к базе происходит внутри ManagementSystem. ManagementSystem сам обработает исключение коннекта

        // Получаем список групп и студентов
        Vector<Group> allGroups = null;
        Vector<Student> allStudents = null;
        try { // Необходимо обработать ошибку при обращении к базе данных
            allGroups = new Vector<Group>(ms.getAllGroups());
            allStudents = new Vector<Student>(ms.getAllStudents());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allGroupsJList = new JList(allGroups);
        // выставляем режим выделения одного пункта из листа (выделение через контрл не будет работать)
        allGroupsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Добавляем листенер
        allGroupsJList.addListSelectionListener(this);
        allStudentsJList = new JList(allStudents);

        // Нижняя панель
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout()); // все оставшееся место ниже topPanel
        // Вставляем панели со списками групп (leftPanel) и студентов (rightPanel) в нижнюю панель (bottomPanel)
        JPanel leftPanel = createLeftPanelForBottomPanel();
        JPanel rightPanel = createRightPanelForBottomPanel();
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.CENTER);

        return bottomPanel;
    }

    /**
     * Левая панель, предназначенная для нижней панели
     */
    private JPanel createLeftPanelForBottomPanel() {
        // Создаем кнопки для раздела группы и помещаем их вниз leftPanel
        JButton moveGroupButton = new JButton(MOVE_GROUP_BUTTON);
        moveGroupButton.setName(MOVE_GROUP_BUTTON_NAME);
        moveGroupButton.addActionListener(this);
        JButton clearGroupButton = new JButton(CLEAR_GROUP_BUTTON);
        clearGroupButton.setName(CLEAR_GROUP_BUTTON_NAME);
        clearGroupButton.addActionListener(this);

        // Создаем панель, на которую положим наши кнопки и поместим ее вниз leftPanel
        JPanel groupsButtonsPanel = new JPanel();
        groupsButtonsPanel.setLayout(new GridLayout(1, 2));
        groupsButtonsPanel.add(moveGroupButton);
        groupsButtonsPanel.add(clearGroupButton);

        // Левая панель для вывода списка групп
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        leftPanel.add(new JLabel(BOTTOM_PANEL_INNER_LEFT_PANEL_LABEL), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(allGroupsJList), BorderLayout.CENTER); // Вставляем список в скроллируемую панель (JScrollPane), которую помещаем в центре leftPanel
        leftPanel.add(groupsButtonsPanel, BorderLayout.SOUTH);

        return leftPanel;
    }

    /**
     * Правая панель, предназначенная для нижней панели
     */
    private JPanel createRightPanelForBottomPanel() {
        // Создаем таблицу и вставляем ее в скроллируемую панель. Эту скроллируемую панель кладем на панель rightPanel
        studentsJTable = new JTable(0, StudentsTableModel.NUM_COLUMNS_FOR_STUDENTS_TABLE);
        studentsJTable.setModel(new StudentsTableModel(new Vector<Student>())); // Устанавливаем модель для таблицы с пустыми студентами

        // Создаем кнопки для студентов
        JButton insertStudentButton = new JButton(INSERT_STUDENT_BUTTON);
        insertStudentButton.setName(INSERT_STUDENT_BUTTON_NAME);
        insertStudentButton.addActionListener(this);
        JButton updateStudentButton = new JButton(UPDATE_STUDENT_BUTTON);
        updateStudentButton.setName(UPDATE_STUDENT_BUTTON_NAME);
        updateStudentButton.addActionListener(this);
        JButton deleteStudentButton = new JButton(DELETE_STUDENT_BUTTON);
        deleteStudentButton.setName(DELETE_STUDENT_BUTTON_NAME);
        deleteStudentButton.addActionListener(this);

        // Создаем панель, на которую положим наши кнопки и поместим ее вниз rightPanel
        JPanel studentsButtonsPanel = new JPanel();
        studentsButtonsPanel.setLayout(new GridLayout(1, 3));
        studentsButtonsPanel.add(insertStudentButton);
        studentsButtonsPanel.add(updateStudentButton);
        studentsButtonsPanel.add(deleteStudentButton);

        // Правая панель для вывода списка студентов
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        rightPanel.add(new JLabel(BOTTOM_PANEL_INNER_RIGHT_PANEL_LABEL), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(studentsJTable), BorderLayout.CENTER);
        rightPanel.add(studentsButtonsPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    /**
     * Реализация интерфейса ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(ALL_STUDENTS_MENU_ITEM_NAME))
                showAllStudents();
            if (c.getName().equals(MOVE_GROUP_BUTTON_NAME))
                moveGroup();
            if (c.getName().equals(CLEAR_GROUP_BUTTON_NAME))
                clearGroup();
            if (c.getName().equals(INSERT_STUDENT_BUTTON_NAME))
                insertStudent();
            if (c.getName().equals(UPDATE_STUDENT_BUTTON_NAME))
                updateStudent();
            if (c.getName().equals(DELETE_STUDENT_BUTTON_NAME))
                deleteStudent();
        }
    }

    /**
     * Показ всех студентов
     */
    private void showAllStudents() {
        JOptionPane.showMessageDialog(this, "showAllStudents");
    }

    /**
     * Перенос группы
     */
    private void moveGroup() {
        JOptionPane.showMessageDialog(this, "moveGroup");
    }

    /**
     * Очистка группы
     */
    private void clearGroup() {
        JOptionPane.showMessageDialog(this, "clearGroup");
    }

    /**
     * Добавление студента
     */
    private void insertStudent() {
        JOptionPane.showMessageDialog(this, "insertStudent");
    }

    /**
     * Редактирование студента
     */
    private void updateStudent() {
        JOptionPane.showMessageDialog(this, "updateStudent");
    }

    /**
     * Удаление студента
     */
    private void deleteStudent() {
        JOptionPane.showMessageDialog(this, "deleteStudent");
    }

    /**
     * Реализация интерфейса ListSelectionListener
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) { // Если выбрали какую-либо группу (например, мышкой) в списке групп
            reloadStudents();
        }
    }

    /**
     * Реализация интерфейса ChangeListener
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        reloadStudents();
    }

    /**
     * Обновление списка студентов для определенной группы. Тело метода выполняется в другом потоке
     */
    private void reloadStudents() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (studentsJTable != null) {
                    Group g = (Group) allGroupsJList.getSelectedValue(); // Получаем выделенную группу
                    SpinnerNumberModel spinnerNumberModel = (SpinnerNumberModel) yearJSpinner.getModel();
                    int spinnerNumber = spinnerNumberModel.getNumber().intValue();
                    try {
                        ArrayList<Student> students = ms.getStudentsFromGroup(g);
                        studentsJTable.setModel(new StudentsTableModel(new Vector<Student>(students))); // Устанавливаем модель для таблицы с новыми данными
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                    }

                }
            }
        };

        thread.start();
    }
}

// Переопределенная панель
class GroupPanel extends JPanel {
    public Dimension getPreferredSize() {
        return new Dimension(250, 0);
    }
}