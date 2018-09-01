# goodJ
Java Framework for window applications

### Installation
Just include goodJ.jar in your class path.

### Use case scenario

1. You have to create a main class where you will run new Application. Application name has to be a package name of your app (see example)

```java
package goodJExample;

import goodJ.Application;

public class MainApp
{
    public static void main(String[] args)
    {
        new Application("goodJExample", "mainController", "mainAction");
    }
}
```

2. Your main class will invoke first controller named "mainController" and action which is an annotated method inside it.

```java
package goodJExample;

import goodJ.Abstract.AbstractController;
import goodJ.Annotation.Action;
import goodJ.Annotation.Controller;

@Controller(name="mainController")
public class MainController extends AbstractController
{
    @Action(name="mainAction")
    public void mainAction()
    {
        this.getApplication().buildView("mainView");
    }
}
```

3. Method "mainAction" will create our first view. View annotation has an additional "type" parameter which can be SINGLE_VIEW or MULTIPLE_VIEW. First one simply prevents from opening many windows. For example when button click should open a settings window then another button click will only focus on already opened window.

In below example notice also the Bind annotation above JButton. This automatically binds button to some action in any controller.

```java
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
```

4. When you click "Save" button
