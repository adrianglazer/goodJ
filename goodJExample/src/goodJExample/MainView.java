package goodJExample;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import goodJ.Abstract.AbstractView;
import goodJ.Annotation.Bind;
import goodJ.Annotation.View;
import goodJ.Enum.ViewType;

@View(name = "mainView", type = ViewType.SINGLE_VIEW)
public class MainView extends AbstractView
{
    private JFrame frame;
    private JTextArea textArea = new JTextArea();
    
    @Bind(name = "anotherController.saveAction")
    public JButton saveButton = new JButton("Save");
    
    @Bind(name = "anotherController.cancelAction")
    public JButton cancelButton = new JButton("Cancel");

    @Override
    public void buildView(Object... arg0)
    {
        this.frame = new JFrame();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocation(dim.width / 2 - 800 / 2, dim.height / 2 - 600 / 2);
        frame.setTitle("Main view");
        frame.setVisible(false);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(this.saveButton);
        bottomPanel.add(this.cancelButton);
        this.frame.add(this.textArea, BorderLayout.CENTER);
        this.frame.add(bottomPanel, BorderLayout.SOUTH);
        this.frame.setVisible(true);
    }

    @Override
    public void close()
    {
        this.frame.dispose();
    }

    @Override
    public void focus()
    {
    }

    @Override
    public boolean isVisible()
    {
        return this.frame.isVisible();
    }
    
    public String getTextFromTextArea()
    {
        return this.textArea.getText();
    }
}
