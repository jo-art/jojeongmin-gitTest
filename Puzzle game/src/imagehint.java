import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.JButton;


class imagehint extends Thread {
	
	//이미지의 힌트를 하는것으로 J버튼들과 이미지들의 경로를 받아서 
	//각 버튼들에 이미지를 다시 한번 setting해준다 
	JButton change[];
	String chagepath[];
	imagehint(JButton [] J, String name[]) {
		change = J;
		chagepath = name;
	}


	public void run() {
		for (int i = 0; i < change.length; i++) {
			change[i].setIcon(new ImageIcon(chagepath[i]));
			change[i].setBackground(Color.pink); //힌트 back ground
		}
	}
}
