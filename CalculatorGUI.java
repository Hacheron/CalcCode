import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField num1Field, num2Field, resultField;
    private JButton addButton, subtractButton, multiplyButton, divideButton;
    private JButton loadButton, saveButton;
    private File inputFile, outputFile;

    public CalculatorGUI() {
        // Set up the frame
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create the input fields
        num1Field = new JTextField(10);
        num2Field = new JTextField(10);
        resultField = new JTextField(10);
        resultField.setEditable(false);

        // Create the buttons
        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");
        loadButton = new JButton("Load XML");
        saveButton = new JButton("Process XML");

        // Add action listeners to the buttons
        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);
        loadButton.addActionListener(this);
        saveButton.addActionListener(this);

        // Add the components to the content pane
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new GridLayout(10, 2, 5, 5));
        contentPane.add(new JLabel("First number:"));
        contentPane.add(num1Field);
        contentPane.add(new JLabel("Second number:"));
        contentPane.add(num2Field);
        contentPane.add(new JLabel("Operation:"));
        contentPane.add(addButton);
        contentPane.add(new JLabel(""));
        //contentPane.add(subtractButton);
        //contentPane.add(new JLabel(""));
        //contentPane.add(multiplyButton);
        //contentPane.add(new JLabel(""));
        //contentPane.add(divideButton);
        contentPane.add(new JLabel("Result:"));
        contentPane.add(resultField);
        contentPane.add(loadButton);
        contentPane.add(saveButton);
        
        

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton || e.getSource() == subtractButton ||
                e.getSource() == multiplyButton || e.getSource() == divideButton) {
            // Get the numbers from the input fields
            double num1 = Double.parseDouble(num1Field.getText());
            double num2 = Double.parseDouble(num2Field.getText());

            // Perform the appropriate operation based on the button clicked
            double result = 0.0;
            if (e.getSource() == addButton) {
                result = num1 + num2;
            } else if (e.getSource() == subtractButton) {
                result = num1 - num2;
            } else if (e.getSource() == multiplyButton) {
                result = num1 * num2;
            } else if (e.getSource() == divideButton) {
                result = num1 / num2;
            }

            // Set the result in the result field
            resultField.setText(Double.toString(result));
        } else if (e.getSource() == loadButton) {
            // Show a file chooser dialog to select the input XML file
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                inputFile = fileChooser.getSelectedFile();
                try {
                    // Parse the input XML file and perform the calculations
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(inputFile);
                    NodeList problemList = doc.getElementsByTagName("problem");
                    for (int i = 0; i < problemList.getLength(); i++) {
                        Element problem = (Element) problemList.item(i);
                        String operation = problem.getAttribute("operation");
                        double num1 = Double.parseDouble(problem.getElementsByTagName("num1").item(0).getTextContent());
                        double num2 = Double.parseDouble(problem.getElementsByTagName("num2").item(0).getTextContent());
                        double result1 = 0.0;
                        if (operation.equals("add")) {
                            result1 = num1 + num2;
                        } else if (operation.equals("subtract")) {
                            result1 = num1 - num2;
                        } else if (operation.equals("multiply")) {
                            result1 = num1 * num2;
                        } else if (operation.equals("divide")) {
                            result1 = num1 / num2;
                        }
                        problem.getElementsByTagName("result").item(0).setTextContent(Double.toString(result1));
                    }
                    // Write the results to the output XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    outputFile = new File(inputFile.getParentFile(), "output.xml");
                    StreamResult resultStream = new StreamResult(outputFile);
                    transformer.transform(source, resultStream);
                    JOptionPane.showMessageDialog(this, "Calculations completed and saved to output.xml");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == saveButton) {
            // Show a file chooser dialog to select the output XML file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("output.xml"));
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                outputFile = fileChooser.getSelectedFile();
                try {
                    // Parse the input XML file and perform the calculations
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(inputFile);
                    NodeList problemList = doc.getElementsByTagName("problem");
                    for (int i = 0; i < problemList.getLength(); i++) {
                        Element problem = (Element) problemList.item(i);
                        String operation = problem.getAttribute("operation");
                        double num1 = Double.parseDouble(problem.getElementsByTagName("num1").item(0).getTextContent());
                        double num2 = Double.parseDouble(problem.getElementsByTagName("num2").item(0).getTextContent());
                        double result1 = 0.0;
                        if (operation.equals("add")) {
                            result1 = num1 + num2;
                        } else if (operation.equals("subtract")) {
                            result1 = num1 - num2;
                        } else if (operation.equals("multiply")) {
                            result1 = num1 * num2;
                        } else if (operation.equals("divide")) {
                            result1 = num1 / num2;
                        }
                        problem.getElementsByTagName("result").item(0).setTextContent(Double.toString(result1));
                    }
                    // Write the results to the output XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    outputFile = new File(inputFile.getParentFile(), "output.xml");
                    StreamResult resultStream = new StreamResult(outputFile);
                    transformer.transform(source, resultStream);
                    JOptionPane.showMessageDialog(this, "Calculations completed and saved to output.xml");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
    public static void main(String[] args) {
        CalculatorGUI calculator = new CalculatorGUI();
        calculator.setVisible(true);
    }
}
