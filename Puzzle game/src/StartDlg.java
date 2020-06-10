import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;


class StartDlg extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 설정 선택 창의 그룹
	@SuppressWarnings("unused")
	private ButtonGroup groupSetting;
	// Default의 창의 그룹
	private ButtonGroup groupDefault;
	
	// 설정 선택창의 라디오 버튼
	private JRadioButton jDelfault;
	@SuppressWarnings("unused")
	private JRadioButton jCostomer;
	
	// Default의 창에 라디오 버튼
	private JRadioButton jEasy;
	private JRadioButton jNomar;
	private JRadioButton jHard;
	
	
	// Ok와 Cancel버튼
	private JButton jBtnOk;
	private JButton jBtnCancel;
	
	// 폰트 사용
	private Font myFont;
	

	// 값 받기
	private int row_value;
	private int col_value;

	// 체크된 라디오 박스
	@SuppressWarnings("unused")
	private boolean check;

	// 확인 버튼 클릭
	@SuppressWarnings("unused")
	private boolean click;

	private FindGame owner;

	@SuppressWarnings("unused")
	private Font myFont(int font){
		this.myFont = new Font("Dialog", Font.BOLD, font);
		return myFont;
	}

	public StartDlg(JFrame owner){
		super(owner,"", false);
		this.owner = (FindGame)owner;
		this.init();
		this.setLayout();
		this.addListener();
		this.showJDlg();

	}
	private void init(){

		// 그룹
		this.groupSetting = new ButtonGroup();
		this.groupDefault = new ButtonGroup();
		
		// 선택 창
		this.jDelfault = new JRadioButton("Default");
		// 버튼을 누른 상태로 만듬.
		this.jDelfault.doClick();
		this.jCostomer = new JRadioButton("Costomer");
		
		// Default를 눌렸을경우 선택하는거
		this.jEasy = new JRadioButton("4x4");
		this.jNomar = new JRadioButton("4x5");
		// 생성자를 이용하여 버튼을 누른 상태로 만듬.
		this.jHard = new JRadioButton("4x6", true);
		// 버튼들
		this.jBtnOk = new JButton("설정");
		this.jBtnCancel = new JButton("취소");
	
	}
	private void setLayout(){
		//새로 시작의 다이얼로그 래이아웃 배치도이다. 
		JPanel big = new JPanel(new GridLayout(0, 1));
	
		JPanel pnlDefault = new JPanel(new GridLayout(0, 1));
		pnlDefault.setBorder(new TitledBorder("퍼즐 크기 선택 \n"));
		groupDefault.add(jEasy);
		groupDefault.add(jNomar);
		groupDefault.add(jHard);
		pnlDefault.add(jEasy);
		pnlDefault.add(jNomar);
		pnlDefault.add(jHard);
		big.add(pnlDefault);
		
		JPanel pnlBtn = new JPanel(); 
		pnlBtn.add(jBtnOk);
		pnlBtn.add(jBtnCancel);
		
		this.add(pnlBtn, BorderLayout.SOUTH);
		this.add(big,BorderLayout.CENTER);
		
	}
	//각 라디오 버튼과 ok, cancle에 대한 버튼들의 등록을 한다 
	private void addListener(){
	
		this.addWindowListener(new WindowAdapter(){
			@SuppressWarnings("unused")
			public void WindowClosing(WindowEvent we){
				dispose();
		
			}
		});

		// 선택 이벤트 처리
		this.jDelfault.addActionListener(this);


//		 Ok이벤트 처리(숫자, 자리, 홀수, 짝수)
		this.jBtnOk.addActionListener(this);
		this.jBtnCancel.addActionListener(this);
		
		// 나중에 쓸 이벤트 정의
		this.jEasy.addActionListener(this);
		this.jNomar.addActionListener(this);
		this.jHard.addActionListener(this);
	}

	private void showJDlg(){
		this.pack();
		this.setLocation(200, 100);
		this.setVisible(true);
	}

	//각 버튼들이 눌렸을경우 getsource를 통해서 버튼의 객체의 id값을 가져온다 
	public void actionPerformed(ActionEvent ae){
		Object o = ae.getSource();
				
		if(this.jDelfault.isSelected()) {

			this.jEasy.setEnabled(true);
			this.jNomar.setEnabled(true);
			this.jHard.setEnabled(true);

			//난이도에 따른 세팅 정보 이다. 
			if(this.jEasy.isSelected()) {
				this.row_value = 4;
				this.col_value = 4;
			} else if(this.jNomar.isSelected()) {
				this.row_value = 4;
				this.col_value = 5;
			} else if(this.jHard.isSelected()) {
				this.row_value = 4;
				this.col_value = 6;
			}

			if(o == this.jBtnOk) { //Ok 버튼을 눌렀을 떄. 오류가 있으면 다이얼 로그창 띄워야.
				this.owner.setBtns(this.row_value, this.col_value, true);
				new FindGame(this, this.row_value, this.col_value);
				this.owner.close();
				this.owner.dispose();
				this.dispose();
			}
		}else if(o == this.jBtnCancel){
			this.dispose();
		}

	}
}