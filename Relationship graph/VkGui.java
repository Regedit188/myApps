import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VkGui extends JFrame {
    private final JButton buildButton = new JButton("Build Graph");
    private final JButton infoButton = new JButton("HELP");
    private final JButton addButton = new JButton("Add user");
    private final JTextArea input = new JTextArea("97024294 159298559 135927919 136837918 139048410");
    private final JLabel label1 = new JLabel("Input all users'");
    private final JLabel label2 = new JLabel(" information:");
    private final JCheckBox checkBoxSpanningTree = new JCheckBox("Spanning Tree",false);
    private final JCheckBox checkBoxNonFriends = new JCheckBox("Consider Non Friends",false);
    private final JRadioButton radioButton1 = new JRadioButton("From file");
    private final JFileChooser choosenFile = new JFileChooser();
    private final JRadioButton radioButton2 = new JRadioButton("From keyboard");
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final GraphPanel graphPanel = new GraphPanel();
    private final JTextField newUser = new JTextField();
    private final JLabel newUserLabel = new JLabel("Input User ID");

        private String inputString;



    private File fileDialog(){
        int returnVal = choosenFile.showDialog(this, "Открыть файл");
        if (returnVal == JFileChooser.APPROVE_OPTION){
            return choosenFile.getSelectedFile();
        }
        return null;
    }

    class BuildButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            inputString = radioButton1.isSelected() ? readFile() : input.getText();
            graphPanel.build(VkGui.this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected());
        }
        private String readFile(){
            StringBuilder text = new StringBuilder();
                try(FileReader reader = new FileReader(fileDialog()))
                {
                    int c;
                    while((c = reader.read()) != -1){
                        text.append ((char) c);
                    }
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }

            return  text.toString();
        }
    }
    class AddButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                String id = newUser.getText();
                if (id.length() == 0){
                    new WarningDialog(VkGui.this, "Ошибка", true, "Введите ID");
                    return;
                }
                graphPanel.addUser(id);
                SwingUtilities.updateComponentTreeUI(VkGui.this);
                //graphPanel.build(VkGui.this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected());
            }
        }

        public VkGui(){
            super("VK visualisation");
            this.setBounds(100, 100, 1430, 850);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setAllFormsBounds();
            setAllButtonsAndCheckBoxes();
            addAllForms();
            new HelpPanel();
        }
        private void setAllButtonsAndCheckBoxes(){
            buildButton.addActionListener(new BuildButtonActionListener());
            addButton.addActionListener(new AddButtonActionListener());
            infoButton.addActionListener(e -> new HelpPanel());
            checkBoxSpanningTree.addActionListener(e -> graphPanel.build(this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected()));
            checkBoxNonFriends.addActionListener(e -> graphPanel.build(this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected()));
            radioButton1.addActionListener(e -> {
                input.setEnabled(false);
                input.setBackground(Color.LIGHT_GRAY);
            });
            radioButton2.addActionListener(e -> {
                input.setEnabled(true);
                input.setBackground(Color.WHITE);
            });
            ButtonGroup group = new ButtonGroup();
            group.add(radioButton1);
            group.add(radioButton2);
            radioButton2.setSelected(true);
            checkBoxSpanningTree.setSelected(true);
            checkBoxNonFriends.setSelected(true);
        }
        private void setAllFormsBounds(){
            infoButton.setBounds(1300, 5, 100, 50);
            label1.setBounds(25,45,80,10);
            label2.setBounds(25,57,80,10);
            input.setBounds(110,30,450,50);
            buildButton.setBounds(580,30,100,50);
            checkBoxNonFriends.setBounds(120,5,130,20);
            checkBoxSpanningTree.setBounds(250,5,100,20);
            radioButton1.setBounds(350,5,80,20);
            radioButton2.setBounds(440,5,100,20);
            newUser.setBounds(1170, 60, 100, 20);
            newUserLabel.setBounds(1170, 45, 100, 12);
            addButton.setBounds(1300, 60, 100, 20);
            layeredPane.setBounds(1,95, 1400, 700);
            input.setFont(new Font("Dialog", Font.PLAIN, 14));
        }
        private void addAllForms(){
            Container container = this.getContentPane();
            container.setLayout(null);
            container.add(radioButton1);
            container.add(radioButton2);
            container.add(label1);
            container.add(label2);
            container.add(input);
            container.add(buildButton);
            container.add(checkBoxSpanningTree);
            container.add(checkBoxNonFriends);
            container.add(newUser);
            container.add(newUserLabel);
            container.add(addButton);
            container.add(infoButton);
            container.add(layeredPane);
        }
        @Override
        public JLayeredPane getLayeredPane(){
            return layeredPane;
        }
    }
