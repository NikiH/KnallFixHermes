package ch.hszt.kfh.ubungen;

public class Bisect {
	
	private double a;
	private double b;
	private double tol;
	
	private IFunction function;
	
	public double getA() {
		return a;
	}
	public double getB() {
		return b;
	}
	public double getTol() {
		return tol;
	}
	public IFunction getFunction() {
		return function;
	}
	
	public void setA(double a) {
		this.a = a;
	}
	public void setB(double b) {
		this.b = b;
	}
	public void setTol(double tol) {
		this.tol = tol;
	}
	public void setFunction(IFunction function) {
		this.function = function;
	}
	
	public double iterate() throws Exception {
		if (function.f(a) * function.f(b) >= 0) {
			throw new Exception("No root in interval.");
		}
		double ai = a;
		double bi = b;
		double diff = Math.abs(ai - bi) / 2;
		while (diff > tol) {
			double x1 = Math.abs(ai + bi) / 2;
			if (function.f(x1) < 0) {
				ai = x1;
			} else {
				bi = x1;
			}
			diff = Math.abs(ai - bi) / 2;
		}
		return Math.abs(ai + bi) / 2;
	}

}
