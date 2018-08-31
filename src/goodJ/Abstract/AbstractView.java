package goodJ.Abstract;

import goodJ.Interface.ViewInterface;

public abstract class AbstractView implements ViewInterface
{
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AbstractView clone() throws CloneNotSupportedException
	{
		return (AbstractView) super.clone();
	}
}
