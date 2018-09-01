package goodJExample;

import javax.swing.JOptionPane;

import goodJ.Annotation.Service;

@Service(name="exampleService")
public class ExampleService
{
    public void saveTextToFile(String text)
    {
        JOptionPane.showMessageDialog(null, text, " Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
