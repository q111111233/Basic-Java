class BoolVal extends Val
{
	boolean val;

	BoolVal(boolean i)
	{
		val = i;
	}

	public String toString()
	{
		return val+"";
	}

	Val cloneVal()
	{
		return new BoolVal(val);
	}

	@Override
	float floatVal() 
	{	
		return 0;
	}

	@Override
	boolean isZero() {
		// TODO Auto-generated method stub
		return false;
	}


}