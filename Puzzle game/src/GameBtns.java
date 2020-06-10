

class GameBtns {
	
	//defalut 생성자 
	public GameBtns() {}

	public String[] RanArray(int size) {
		//이미지를 생성할 크기를 정한다 총 1개의 이미지당 2개의 같은 카드를 생성한다 
		String RanFix[] = { "./Image/1.png", "./Image/2.png", "./Image/3.png",
				"./Image/4.png", "./Image/5.png", "./Image/6.png",
				"./Image/7.png", "./Image/8.png", "./Image/9.png",
				"./Image/10.png", "./Image/11.png", "./Image/12.png",
				"./Image/13.png", "./Image/14.png", "./Image/15.png",
				"./Image/16.png" };

		//random으로 생성된 이미지들의 path를 담을 변수 이다. 
		
		String RanOutput[];
		//size/2 갯수의 배열을 가지고 와서, 쌍으로 만들어서 배열에 넣는다 
		int num = size / 2;
		int tempCount = 0;
		int temp = 0;
		//같은값을 뽑았는지 아닌지를 결정할을 flag변수이다 
		boolean flag = false;
		//size갯수 만큼 String 배열을 생성하여 초기화 해준다 
		RanOutput = new String[size];

		//for문을 통해 random으로 String의 객체를 생성하여 이미지 아이콘을 생성할 준비를 한다 
		for (int i = 0; i < size; i++) {
			do {
				tempCount = 0;
				flag = false;
				temp = (int) (Math.random() * num); // 0~num-1 까지 중의 숫자를 하나 뽑아서,
				RanOutput[i] = new String(RanFix[temp]);

				for (int j = 0; j < i; j++) { // 중복 검사. 두개까지 허용한다 
					if (RanOutput[i].equals(RanOutput[j])) { // 이전까지 배열중 동일한거 갯수
																// 세서,
						tempCount++;
						if (tempCount > 1) { // 전과 같은 것이 있다면
							flag = true; // 다시 뽑는다. flag 설정하여 다시 뽑아야되는것을 알려준다
							break;
						}
					}
				}
			} while (flag);
		}
		//이미지의 random path의 size만큼 생성하여 그 String 배열을 return 한다 
		return RanOutput;
	}
}

