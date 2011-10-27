package ch.hszt.kfh.ubungen;

public class MdpJunitDemoImpl implements IMdpJunitDemo {

	@Override
	public boolean isEmpty(String s) {
		return s == null || "".equals(s);
	}

	@Override
	public String capitalize(String s) {
		if (isEmpty(s)) {
			return s;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	@Override
	public String reverse(String s) throws NullPointerException {
		String r = "";
		for (int i = s.length() - 1; i >= 0; --i) {
			r += s.charAt(i);
		}
		return r;
	}

	@Override
	public String join(String... strings) {
		String r = "";
		if (strings.length > 0) {
			r += strings[0];
		}
		for (int i = 1; i < strings.length; i++) {
			r += " " + strings[i];
		}
		return r;
	}

}
