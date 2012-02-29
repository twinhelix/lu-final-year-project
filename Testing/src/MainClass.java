
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Iface f = new Face2();
		f.setName("hey");
		Iface ff = (Iface) f.clone();
		f.setName("g-unit");
		//ff.setName("man");
		System.out.println(f.getName() + ff.getName());
			
	}

}
