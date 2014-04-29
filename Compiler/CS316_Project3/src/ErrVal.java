
public class ErrVal extends Val
{
	String msg;
	
	public ErrVal (String msg)
	{
		this.msg = msg;
	}
	
	Val cloneVal() 
	{
		return new ErrVal(msg);
	}

	@Override
	float floatVal() 
	{
		return 0;
	}

	@Override
	boolean isZero() 
	{
	return false;
	}

	@Override
	public String toString() 
	{
		return msg;
	}

}
