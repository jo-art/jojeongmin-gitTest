class TimeThread extends Thread {
//	private int time;
	private int timer;

	private FindGame clock;
	private Runnable timeoverRunnable;

	//시간 설정 정보이다. 
	TimeThread(FindGame clock, Runnable timeoverRunnable) {
		this.clock = clock;
		//this.time = 0;
		this.timer = 0;
		this.timeoverRunnable = timeoverRunnable;
	}

	public void run() {
		while (this.timer <= 99) {
			this.clock.setTime(timer);
			try {
				Thread.sleep(1000); //1초씩 카운트..
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.timer++;
		}
		timeoverRunnable.run();
	}

	public void inCorrect() {
		timer += 5;
	}
}