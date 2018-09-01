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
