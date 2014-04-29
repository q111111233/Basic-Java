abstract class Val
{
	abstract Val cloneVal();
	abstract float floatVal(); // conversion of integer value to float value
	abstract boolean isZero();
	public abstract String toString ();
}

//Create Error Val to print message.