import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;


class imageThread extends Thread {
	
	//이미지를 가린다.
	//처음 보여준이미지 위에 별 이미지로 하여 이미지를 덮는다. 
	JButton change[];
	String chagepath;
	imageThread(JButton [] J, String name) {
		change = J;
		chagepath = name;
	}


	public void run() {
		try {
			Thread.sleep(5000); //5초 뒤에 이미지들이 모두 별로 바뀐다.
			for (int i = 0; i < change.length; i++) {
				change[i].setIcon(new ImageIcon(chagepath));
				change[i].setBackground(Color.black); //Start부분 배경
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}