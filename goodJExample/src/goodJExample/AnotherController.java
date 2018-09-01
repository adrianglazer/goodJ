package goodJExample;

import goodJ.Abstract.AbstractController;
import goodJ.Annotation.Action;
import goodJ.Annotation.Controller;

@Controller(name="anotherController")
public class AnotherController extends AbstractController
{
    @Action(name="saveAction")
    public void saveAction()
    {
        MainView view = (MainView) this.getApplication().getBuiltViews("mainView").get(0);
        ExampleService service = (ExampleService) this.getApplication().getService("exampleService");
        
        service.saveTextToFile(view.getTextFromTextArea());
    }
    
    @Action(name="cancelAction")
    public void cancelAction()
    {
        this.getApplication().close();
    }
}
