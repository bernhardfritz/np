package exercise3.b;

class ExpensiveObject {
	static int count = 0;
	
	ExpensiveObject() {
		count++;
		for(int i = 0; i < 1000; i++) {
			Math.random();
		}
	}
}